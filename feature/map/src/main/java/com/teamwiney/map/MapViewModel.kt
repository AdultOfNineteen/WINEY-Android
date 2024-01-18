package com.teamwiney.map

import androidx.lifecycle.viewModelScope
import com.teamwiney.core.common.base.BaseViewModel
import com.teamwiney.data.network.model.request.MapPosition
import com.teamwiney.data.repository.map.MapRepository
import dagger.hilt.android.lifecycle.HiltViewModel
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
//                    postEffect()
                }
            }
        }
    }

    fun getWineShops(
        shopFilter: String,
        mapPosition: MapPosition
    ) = viewModelScope.launch {
//        mapRepository.getWineShops(
//            shopFilter = shopFilter,
//            mapPosition = mapPosition
//        ).collectLatest {
//            Log.i("dlgocks1", "getWineShops: $it")
//            when (it) {
//                is ApiResult.ApiError -> TODO()
//                is ApiResult.NetworkError -> TODO()
//                is ApiResult.Success -> TODO()
//            }
//        }
    }
}