package com.teamwiney.notewrite

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.teamwiney.core.common.base.BaseViewModel
import com.teamwiney.core.common.navigation.NoteDestinations
import com.teamwiney.data.network.adapter.ApiResult
import com.teamwiney.data.network.model.response.SearchWine
import com.teamwiney.data.pagingsource.SearchWinesPagingSource
import com.teamwiney.data.repository.tastingnote.TastingNoteRepository
import com.teamwiney.data.repository.wine.WineRepository
import com.teamwiney.data.util.fileFromContentUri
import com.teamwiney.data.util.resizeAndSaveImage
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class NoteWriteViewModel @Inject constructor(
    private val wineRepository: WineRepository,
    private val tastingNoteRepository: TastingNoteRepository,
    @ApplicationContext private val context: Context,
) : BaseViewModel<NoteWriteContract.State, NoteWriteContract.Event, NoteWriteContract.Effect>(
    initialState = NoteWriteContract.State()
) {

    override fun reduceState(event: NoteWriteContract.Event) {
        viewModelScope.launch {
            when (event) {
                is NoteWriteContract.Event.SearchWine -> {
                    getSearchWines()
                }
            }
        }
    }

    fun getSelectWineFlavor() = viewModelScope.launch {
        wineRepository.getWineDetail(currentState.selectedWine.wineId)
            .onStart {
                updateState(currentState.copy(isLoading = true))
            }
            .collectLatest {
                updateState(currentState.copy(isLoading = false))
                when (it) {
                    is ApiResult.Success -> {
                        updateState(
                            currentState.copy(
                                selectedWineInfo = it.data.result
                            )
                        )
                    }

                    is ApiResult.ApiError -> {
                        postEffect(NoteWriteContract.Effect.ShowSnackBar(it.message))
                    }

                    else -> {
                        postEffect(NoteWriteContract.Effect.ShowSnackBar("네트워크 오류가 발생했습니다."))
                    }
                }
            }
    }

    fun writeTastingNote() = viewModelScope.launch {
        val wineNote = currentState.wineNote

        val jsonObjectBuilder = JSONObject().apply {
            put("wineId", wineNote.wineId)
            put("officialAlcohol", wineNote.officialAlcohol)
            put("alcohol", wineNote.alcohol)
            put("color", colorToHexString(wineNote.color))
            put("sweetness", wineNote.sweetness)
            put("acidity", wineNote.acidity)
            put("body", wineNote.body)
            put("tannin", wineNote.tannin)
            put("finish", wineNote.finish)
            put("memo", wineNote.memo)
            put("rating", wineNote.rating)

            if (wineNote.vintage.isNotEmpty()) {
                put("vintage", wineNote.vintage.toInt())
            }

            if (wineNote.price.isNotEmpty()) {
                put("price", wineNote.price.toInt())
            }

            wineNote.buyAgain?.let {
                put("buyAgain", it)
            }

            val smellKeywordsArray = JSONArray().apply {
                wineNote.smellKeywordList.forEach { put(it) }
            }
            put("smellKeywordList", smellKeywordsArray)
        }

        val request = jsonObjectBuilder.toString().toRequestBody("application/json".toMediaType())
        val multipartFiles = convertImageToMultipartFile()

        tastingNoteRepository.postTastingNote(
            request = request,
            multipartFiles = multipartFiles
        ).onStart {
            updateState(currentState.copy(isLoading = true))
        }.collectLatest {
            updateState(currentState.copy(isLoading = false))
            Log.i("NoteWriteViewModel", "writeTastingNote: $it")
            when (it) {
                is ApiResult.Success -> {
                    postEffect(NoteWriteContract.Effect.NavigateTo(NoteDestinations.ROUTE))
                }

                is ApiResult.ApiError -> {
                    postEffect(NoteWriteContract.Effect.ShowSnackBar(it.message))
                }

                else -> {
                    postEffect(NoteWriteContract.Effect.ShowSnackBar("네트워크 오류가 발생했습니다."))
                }
            }
        }
    }

    private fun convertImageToMultipartFile(): List<MultipartBody.Part> {
        return currentState.wineNote.imgs.map {
            val originalFile = fileFromContentUri(context, it)
            val compressedFile = resizeAndSaveImage(context, originalFile)
            val requestBody: RequestBody = compressedFile.asRequestBody("image/*".toMediaType())
            MultipartBody.Part.createFormData("multipartFiles", compressedFile.name, requestBody)
        }
    }

    private fun colorToHexString(color: Color): String {
        val argb = color.toArgb()
        val red = (argb shr 16 and 0xFF).toString(16).padStart(2, '0')
        val green = (argb shr 8 and 0xFF).toString(16).padStart(2, '0')
        val blue = (argb and 0xFF).toString(16).padStart(2, '0')

        return "#$red$green$blue"
    }

    fun showHintPopup() = viewModelScope.launch {
        updateState(currentState.copy(hintPopupOpen = true))
        delay(5000)
        updateState(currentState.copy(hintPopupOpen = false))
    }

    fun hideHintPopup() = viewModelScope.launch {
        updateState(currentState.copy(hintPopupOpen = false))
    }

    fun updateOfficialAlcohol(officialAlcohol: Double) = viewModelScope.launch {
        updateState(currentState.copy(wineNote = currentState.wineNote.copy(officialAlcohol = officialAlcohol)))
    }

    fun updateVintage(vintage: String) = viewModelScope.launch {
        updateState(currentState.copy(wineNote = currentState.wineNote.copy(vintage = vintage)))
    }

    fun updateAlcohol(alcohol: Int) = viewModelScope.launch {
        updateState(currentState.copy(wineNote = currentState.wineNote.copy(alcohol = alcohol)))
    }

    fun updatePrice(price: String) = viewModelScope.launch {
        updateState(currentState.copy(wineNote = currentState.wineNote.copy(price = price)))
    }

    fun updateColor(color: Color) = viewModelScope.launch {
        updateState(
            currentState.copy(
                wineNote = currentState.wineNote.copy(
                    color = color
                )
            )
        )
    }

    fun updateUris(uris: List<Uri>) = viewModelScope.launch {
        updateState(currentState.copy(wineNote = currentState.wineNote.copy(imgs = uris)))
    }

    fun removeUri(uri: Uri) = viewModelScope.launch {
        updateState(currentState.copy(wineNote = currentState.wineNote.copy(imgs = currentState.wineNote.imgs.filter {
            it != uri
        })))
    }

    fun updateMemo(memo: String) = viewModelScope.launch {
        if (memo.length <= 200) {
            updateState(currentState.copy(wineNote = currentState.wineNote.copy(memo = memo)))
        }
    }

    fun updateRating(rating: Int) = viewModelScope.launch {
        updateState(currentState.copy(wineNote = currentState.wineNote.copy(rating = rating)))
    }

    fun updateBuyAgain(buyAgain: Boolean) = viewModelScope.launch {
        updateState(currentState.copy(wineNote = currentState.wineNote.copy(buyAgain = buyAgain)))
    }

    fun getSearchWines() = viewModelScope.launch {
        getSearchWinesCount()

        updateState(
            currentState.copy(
                searchWines = Pager(
                    config = PagingConfig(
                        pageSize = 10
                    ),
                    pagingSourceFactory = {
                        SearchWinesPagingSource(
                            wineRepository = wineRepository,
                            searchKeyword = currentState.searchKeyword
                        )
                    }
                ).flow.cachedIn(viewModelScope).onStart {
                    updateState(currentState.copy(isLoading = true))
                }.onCompletion {
                    updateState(currentState.copy(isLoading = false))
                }
            )
        )
    }

    private fun getSearchWinesCount() = viewModelScope.launch {
        wineRepository.getSearchWinesCount(
            currentState.searchKeyword
        ).onStart {
            updateState(currentState.copy(isLoading = true))
        }.collectLatest {
            when (it) {
                is ApiResult.Success -> {
                    updateState(
                        currentState.copy(
                            searchWinesCount = it.data.result.totalCnt
                        )
                    )
                }

                is ApiResult.ApiError -> {
                    postEffect(NoteWriteContract.Effect.ShowSnackBar(it.message))
                }

                else -> {
                    postEffect(NoteWriteContract.Effect.ShowSnackBar("네트워크 오류가 발생했습니다."))
                }
            }
        }
    }

    fun updateSearchKeyword(searchKeyword: String) = viewModelScope.launch {
        updateState(currentState.copy(searchKeyword = searchKeyword))
    }

    fun updateSelectedWine(wine: SearchWine) = viewModelScope.launch {
        updateState(currentState.copy(wineNote = currentState.wineNote.copy(wineId = wine.wineId)))
        updateState(currentState.copy(selectedWine = wine))
    }

    fun updateSweetness(sweetness: Int) = viewModelScope.launch {
        updateState(currentState.copy(wineNote = currentState.wineNote.copy(sweetness = sweetness)))
    }

    fun updateAcidity(acidity: Int) = viewModelScope.launch {
        updateState(currentState.copy(wineNote = currentState.wineNote.copy(acidity = acidity)))
    }

    fun updateBody(body: Int) = viewModelScope.launch {
        updateState(currentState.copy(wineNote = currentState.wineNote.copy(body = body)))
    }

    fun updateTannin(tannins: Int) = viewModelScope.launch {
        updateState(currentState.copy(wineNote = currentState.wineNote.copy(tannin = tannins)))
    }

    fun updateFinish(finish: Int) = viewModelScope.launch {
        updateState(currentState.copy(wineNote = currentState.wineNote.copy(finish = finish)))
    }

    fun updateWineSmells(wineSmellOption: WineSmellOption) = viewModelScope.launch {
        val wineNote = currentState.wineNote
        val updatedSmellKeywordList = if (wineNote.smellKeywordList.contains(wineSmellOption.value)) {
            wineNote.smellKeywordList.filter { it != wineSmellOption.value }
        } else {
            wineNote.smellKeywordList + wineSmellOption.value
        }
        updateState(currentState.copy(wineNote = wineNote.copy(smellKeywordList = updatedSmellKeywordList)))
    }

    fun updateThumbX(thumbX: Float) = viewModelScope.launch {
        updateState(currentState.copy(thumbX = thumbX))
    }

    fun isWineSmellSelected(wineSmellOption: WineSmellOption): Boolean {
        val wineNote = currentState.wineNote
        return wineNote.smellKeywordList.contains(wineSmellOption.value)
    }
}