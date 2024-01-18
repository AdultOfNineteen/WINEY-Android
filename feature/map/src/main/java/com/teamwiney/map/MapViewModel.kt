package com.teamwiney.map

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.naver.maps.map.compose.CameraPositionState
import com.teamwiney.core.common.base.BaseViewModel
import com.teamwiney.data.network.adapter.ApiResult
import com.teamwiney.data.network.model.request.MapPosition
import com.teamwiney.data.repository.map.MapRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val mapRepository: MapRepository,
) : BaseViewModel<MapContract.State, MapContract.Event, MapContract.Effect>(
    initialState = MapContract.State()
) {

    override fun reduceState(event: MapContract.Event) {
        viewModelScope.launch {
            when (event) {
                else -> {
                }
            }
        }
    }

    fun getWineShops(
        shopFilter: String,
        cameraPositionState: CameraPositionState
    ) = viewModelScope.launch {

        mapRepository.getWineShops(
            shopFilter = shopFilter,
            mapPosition = MapPosition(
                latitude = cameraPositionState.position.target.latitude,
                longitude = cameraPositionState.position.target.longitude,
                leftTopLatitude = cameraPositionState.contentBounds?.northWest?.latitude
                    ?: 0.0,
                leftTopLongitude = cameraPositionState.contentBounds?.northWest?.longitude
                    ?: 0.0,
                rightBottomLatitude = cameraPositionState.contentBounds?.southEast?.latitude
                    ?: 0.0,
                rightBottomLongitude = cameraPositionState.contentBounds?.southEast?.longitude
                    ?: 0.0,
            )
        ).collectLatest {
            Log.i("dlgocks1", "Response: $it")
            when (it) {
                is ApiResult.Success -> {
                    Log.i("dlgocks1", "list : ${it.data.result}")
                    updateState(
                        currentState.copy(
                            wineShops = it.data.result
                        )
                    )
                }

                else -> {
                    postEffect(MapContract.Effect.ShowSnackBar("와인샵 정보를 불러오는데 실패하였습니다."))
                }
            }
        }
    }
}