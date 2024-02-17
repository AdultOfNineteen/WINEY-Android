package com.teamwiney.map

import android.annotation.SuppressLint
import android.content.Context
import android.os.Looper
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.*
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.compose.CameraPositionState
import com.teamwiney.core.common.base.BaseViewModel
import com.teamwiney.data.network.adapter.ApiResult
import com.teamwiney.data.network.model.request.MapPosition
import com.teamwiney.data.network.model.response.WineShop
import com.teamwiney.data.repository.map.MapRepository
import com.teamwiney.map.model.MovingCameraWrapper
import com.teamwiney.map.model.ShopCategory
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val mapRepository: MapRepository,
) : BaseViewModel<MapContract.State, MapContract.Event, MapContract.Effect>(
    initialState = MapContract.State()
), LifecycleObserver {


    private val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    private val locationRequest: LocationRequest =
        LocationRequest.Builder(3000) // 초기 1회만 가져오고 Long.MAX_VALUE 만큼 기다림
            .setPriority(Priority.PRIORITY_LOW_POWER)
            .build()
    private val locationCallback: CustomLocationCallback = CustomLocationCallback()

    private val onFailToLoadWineShops: (String) -> Unit = {
        postEffect(MapContract.Effect.ShowSnackBar(it))
        updateState(currentState.copy(wineShops = emptyList()))
    }

    override fun reduceState(event: MapContract.Event) {
    }

    fun postBookmark(shopId: WineShop) = viewModelScope.launch {
        mapRepository.postBookmark(shopId.shopId).collectLatest {
            when (it) {
                is ApiResult.Success -> {
                    updateState(
                        currentState.copy(
                            wineShops = currentState.wineShops.map { wineShop ->
                                if (wineShop.shopId == shopId.shopId) {
                                    wineShop.copy(like = it.data.result.like)
                                } else {
                                    wineShop
                                }
                            },
                            selectedMarkar = if (currentState.selectedMarkar?.shopId == shopId.shopId) {
                                currentState.selectedMarkar?.copy(like = it.data.result.like)
                            } else currentState.selectedMarkar
                        )
                    )
                }

                else -> {
                    // Do nothing
                }
            }
        }
    }

    fun getWineShops(
        shopFilter: String,
        cameraPositionState: CameraPositionState
    ) = viewModelScope.launch {

        if (cameraPositionState.contentBounds == null) {
            onFailToLoadWineShops("와인샵 정보를 불러오는데 실패하였습니다.")
            return@launch
        }

        mapRepository.getWineShops(
            shopFilter = shopFilter,
            mapPosition = MapPosition(
                latitude = cameraPositionState.position.target.latitude,
                longitude = cameraPositionState.position.target.longitude,
                leftTopLatitude = cameraPositionState.contentBounds!!.northWest.latitude,
                leftTopLongitude = cameraPositionState.contentBounds!!.northWest.longitude,
                rightBottomLatitude = cameraPositionState.contentBounds!!.southEast.latitude,
                rightBottomLongitude = cameraPositionState.contentBounds!!.southEast.longitude,
            )
        ).collectLatest {
            when (it) {
                is ApiResult.Success -> {
                    if (it.data.result.isEmpty()) {
                        postEffect(MapContract.Effect.ShowSnackBar("주변에 가게가 없습니다."))
                    } else {
                        postEffect(MapContract.Effect.ShowSnackBar("와인샵 정보를 ${it.data.result.size}개 불러왔습니다."))
                    }
                    updateState(currentState.copy(wineShops = it.data.result))
                }

                else -> {
                    postEffect(MapContract.Effect.ShowSnackBar("와인샵 정보를 불러오는데 실패하였습니다."))
                }
            }
        }
    }

    inner class CustomLocationCallback : LocationCallback() {

        override fun onLocationResult(locationResult: LocationResult) {
            super.onLocationResult(locationResult)
            locationResult.lastLocation?.let {
                // 초기 1회 진입할 때 마커 불러오기
                if (initialMarkerLoadFlag) {
                    initialMarkerLoadFlag = false
                    updateState(
                        currentState.copy(
                            isLoading = false,
                            movingCameraPosition = MovingCameraWrapper.MOVING(it)
                        )
                    )
                }
                updateState(currentState.copy(userPosition = LatLng(it.latitude, it.longitude)))
            }
        }
    }

    fun updateMovingCameraPosition(movingCameraPosition: MovingCameraWrapper) {
        updateState(currentState.copy(movingCameraPosition = movingCameraPosition))
    }

    fun updateMovingCameraPositionToDefault() {
        updateState(currentState.copy(movingCameraPosition = MovingCameraWrapper.DEFAULT))
    }

    fun updateSelectMarker(
        wineShop: WineShop?,
        isSelected: Boolean
    ) {
        updateState(
            currentState.copy(
                selectedMarkar = wineShop?.copy(isSelected = isSelected),
                wineShops = currentState.wineShops.map {
                    if (wineShop == null) {
                        it.copy(isSelected = false)
                    } else {
                        if (it.shopId == wineShop.shopId) {
                            it.copy(isSelected = isSelected)
                        } else {
                            it.copy(isSelected = false)
                        }
                    }
                }
            )
        )
    }

    fun updatePreviousCategory(shopCategory: ShopCategory?) {
        updateState(currentState.copy(previousCateogy = shopCategory))
    }

    fun updateSelectedShopCategory(shopCategory: ShopCategory) {
        updateState(currentState.copy(selectedShopCategory = shopCategory))
    }

    @SuppressLint("MissingPermission")
    fun addLocationListener() {
        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper(),
        )
    }

    fun removeLocationListener() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    companion object {
        var initialMarkerLoadFlag = true
        val DEFAULT_LATLNG = LatLng(37.5437, 127.0659)
    }
}