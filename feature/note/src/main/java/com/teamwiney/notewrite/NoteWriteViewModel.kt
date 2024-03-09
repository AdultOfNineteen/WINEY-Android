package com.teamwiney.notewrite

import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.graphics.toColorInt
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.teamwiney.core.common.base.BaseViewModel
import com.teamwiney.core.common.model.WineSmell
import com.teamwiney.core.common.navigation.NoteDestinations
import com.teamwiney.data.network.adapter.ApiResult
import com.teamwiney.data.network.model.response.SearchWine
import com.teamwiney.data.network.model.response.TastingNoteImage
import com.teamwiney.data.pagingsource.SearchWinesPagingSource
import com.teamwiney.data.repository.tastingnote.TastingNoteRepository
import com.teamwiney.data.repository.wine.WineRepository
import com.teamwiney.notewrite.model.WineNote
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteWriteViewModel @Inject constructor(
    private val wineRepository: WineRepository,
    private val tastingNoteRepository: TastingNoteRepository,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<NoteWriteContract.State, NoteWriteContract.Event, NoteWriteContract.Effect>(
    initialState = NoteWriteContract.State()
) {
    private val noteId: Int

    init {
        noteId = savedStateHandle.get<String>("noteId")?.toInt() ?: -1
        updateState(currentState.copy(mode = if (noteId == -1) EditMode.ADD else EditMode.UPDATE))
    }

    override fun reduceState(event: NoteWriteContract.Event) {
        viewModelScope.launch {
            when (event) {
                is NoteWriteContract.Event.SearchWine -> {
                    getSearchWines()
                }
            }
        }
    }

    fun loadTastingNote() = viewModelScope.launch {
        if (noteId != -1) {
            tastingNoteRepository.getTastingNoteDetail(noteId).onStart {
                updateState(currentState.copy(isLoading = true))
            }.collectLatest {
                updateState(currentState.copy(isLoading = false))
                when (it) {
                    is ApiResult.Success -> {
                        val result = it.data.result

                        updateState(
                            currentState.copy(
                                wineNote = WineNote(
                                    wineId = -1L,
                                    vintage = if (result.vintage == null) "" else "${result.vintage}",
                                    officialAlcohol = result.officialAlcohol?.toDouble() ?: 0.0,
                                    price = "${result.price}",
                                    color = Color(result.color.toColorInt()),
                                    sweetness = result.myWineTaste.sweetness,
                                    acidity = result.myWineTaste.acidity,
                                    alcohol = result.myWineTaste.alcohol,
                                    body = result.myWineTaste.body,
                                    tannin = result.myWineTaste.tannin,
                                    finish = result.myWineTaste.finish,
                                    memo = result.memo,
                                    buyAgain = result.buyAgain,
                                    rating = result.star,
                                    loadImages = result.tastingNoteImage,
                                    selectedImages = result.tastingNoteImage,
                                    addImages = emptyList(),
                                    deleteImages = emptyList(),
                                    smellKeywordList = result.smellKeywordList.mapNotNull { smellKeyword ->
                                        val wineSmell = WineSmell.values()
                                            .firstOrNull { it.korName == smellKeyword }
                                        wineSmell?.let {
                                            WineSmellOption(it.korName, it.value)
                                        }
                                    },
                                    loadSmellKeywordList = result.smellKeywordList.mapNotNull { smellKeyword ->
                                        val wineSmell = WineSmell.values()
                                            .firstOrNull { it.korName == smellKeyword }
                                        wineSmell?.let {
                                            WineSmellOption(it.korName, it.value)
                                        }
                                    },
                                    addSmellKeywordList = emptyList(),
                                    deleteSmellKeywordList = emptyList()
                                )
                            )
                        )
                        if (currentState.mode == EditMode.UPDATE) {
                            postEffect(
                                NoteWriteContract.Effect.NavigateTo(
                                    NoteDestinations.Write.INFO_LEVEL
                                )
                            )
                        }
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

        tastingNoteRepository.postTastingNote(
            wineId = wineNote.wineId,
            officialAlcohol = wineNote.officialAlcohol,
            alcohol = wineNote.alcohol,
            color = colorToHexString(wineNote.color),
            sweetness = wineNote.sweetness,
            acidity = wineNote.acidity,
            body = wineNote.body,
            tannin = wineNote.tannin,
            finish = wineNote.finish,
            memo = wineNote.memo,
            rating = wineNote.rating,
            vintage = wineNote.vintage,
            price = wineNote.price,
            buyAgain = wineNote.buyAgain,
            smellKeywordList = wineNote.smellKeywordList.map { it.value },
            imgUris = wineNote.selectedImages.map { it.contentUri }
        ).onStart {
            updateState(currentState.copy(isLoading = true))
        }.collectLatest {
            updateState(currentState.copy(isLoading = false))
            when (it) {
                is ApiResult.Success -> {
                    postEffect(NoteWriteContract.Effect.NoteWriteSuccess)
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

    fun updateTastingNote() = viewModelScope.launch {
        val wineNote = currentState.wineNote

        tastingNoteRepository.updateTastingNote(
            noteId = noteId,
            officialAlcohol = wineNote.officialAlcohol,
            alcohol = wineNote.alcohol,
            color = colorToHexString(wineNote.color),
            sweetness = wineNote.sweetness,
            acidity = wineNote.acidity,
            body = wineNote.body,
            tannin = wineNote.tannin,
            finish = wineNote.finish,
            memo = wineNote.memo,
            rating = wineNote.rating,
            vintage = wineNote.vintage,
            price = wineNote.price,
            buyAgain = wineNote.buyAgain,
            smellKeywordList = wineNote.addSmellKeywordList.map { it.value },
            deleteSmellKeywordList = wineNote.deleteSmellKeywordList.map { it.value },
            deleteImgList = wineNote.deleteImages.map { it.imgUrl },
            imgUris = wineNote.addImages.map { it.contentUri }
        ).onStart {
            updateState(currentState.copy(isLoading = true))
        }.collectLatest {
            updateState(currentState.copy(isLoading = false))
            when (it) {
                is ApiResult.Success -> {
                    postEffect(NoteWriteContract.Effect.NoteWriteSuccess)
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

    fun hideHintPopup() {
        updateState(currentState.copy(hintPopupOpen = false))
    }

    fun updateOfficialAlcohol(officialAlcohol: Double?) {
        updateState(currentState.copy(wineNote = currentState.wineNote.copy(officialAlcohol = officialAlcohol)))
    }

    fun updateVintage(vintage: String) {
        updateState(currentState.copy(wineNote = currentState.wineNote.copy(vintage = vintage)))
    }

    fun updateAlcohol(alcohol: Int) {
        updateState(currentState.copy(wineNote = currentState.wineNote.copy(alcohol = alcohol)))
    }

    fun updatePrice(price: String) {
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

    private fun addSmellKeyword(smellKeyword: WineSmellOption) {
        val wineNote = currentState.wineNote.copy(
            smellKeywordList = currentState.wineNote.smellKeywordList + smellKeyword,
            addSmellKeywordList = currentState.wineNote.addSmellKeywordList + smellKeyword,
            deleteSmellKeywordList = currentState.wineNote.deleteSmellKeywordList - smellKeyword
        )
        updateState(currentState.copy(wineNote = wineNote))
    }

    private fun removeSmellKeyword(smellKeyword: WineSmellOption) {
        val selectedSmellKeywords = currentState.wineNote.smellKeywordList - smellKeyword
        val addSmellKeywords = currentState.wineNote.addSmellKeywordList - smellKeyword
        val deleteSmellKeywords =
            currentState.wineNote.deleteSmellKeywordList.toMutableList().apply {
                if (currentState.wineNote.loadSmellKeywordList.contains(smellKeyword)) add(
                    smellKeyword
                )
            }

        updateState(
            currentState.copy(
                wineNote = currentState.wineNote.copy(
                    smellKeywordList = selectedSmellKeywords,
                    addSmellKeywordList = addSmellKeywords,
                    deleteSmellKeywordList = deleteSmellKeywords
                )
            )
        )
    }

    fun addNoteImages(images: List<TastingNoteImage>) {
        val updatedSelectedImages = currentState.wineNote.selectedImages + images
        val updatedAddImages = currentState.wineNote.addImages + images

        updateState(
            currentState.copy(
                wineNote = currentState.wineNote.copy(
                    selectedImages = updatedSelectedImages,
                    addImages = updatedAddImages
                )
            )
        )
    }

    fun removeNoteImage(image: TastingNoteImage) {
        val updatedSelectedImages = currentState.wineNote.selectedImages - image

        val updatedDeleteImages = if (currentState.wineNote.loadImages.contains(image))
            currentState.wineNote.deleteImages + image
        else currentState.wineNote.deleteImages

        updateState(
            currentState.copy(
                wineNote = currentState.wineNote.copy(
                    selectedImages = updatedSelectedImages,
                    deleteImages = updatedDeleteImages
                )
            )
        )
    }

    fun updateMemo(memo: String) {
        if (memo.length <= 200) {
            updateState(currentState.copy(wineNote = currentState.wineNote.copy(memo = memo)))
        }
    }

    fun updateRating(rating: Int) {
        updateState(currentState.copy(wineNote = currentState.wineNote.copy(rating = rating)))
    }

    fun updateBuyAgain(buyAgain: Boolean) {
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

    fun updateSearchKeyword(searchKeyword: String) {
        updateState(currentState.copy(searchKeyword = searchKeyword))
    }

    fun updateSelectedWine(wine: SearchWine) {
        updateState(currentState.copy(wineNote = currentState.wineNote.copy(wineId = wine.wineId)))
        updateState(currentState.copy(selectedWine = wine))
    }

    fun updateSweetness(sweetness: Int) {
        updateState(currentState.copy(wineNote = currentState.wineNote.copy(sweetness = sweetness)))
    }

    fun updateAcidity(acidity: Int) {
        updateState(currentState.copy(wineNote = currentState.wineNote.copy(acidity = acidity)))
    }

    fun updateBody(body: Int) {
        updateState(currentState.copy(wineNote = currentState.wineNote.copy(body = body)))
    }

    fun updateTannin(tannins: Int) {
        updateState(currentState.copy(wineNote = currentState.wineNote.copy(tannin = tannins)))
    }

    fun updateFinish(finish: Int) {
        updateState(currentState.copy(wineNote = currentState.wineNote.copy(finish = finish)))
    }

    fun updateWineSmell(wineSmellOption: WineSmellOption) {
        val wineNote = currentState.wineNote
        Log.d("debugging", "선택 목록 : ${wineNote.smellKeywordList}")

        if (wineNote.smellKeywordList.contains(wineSmellOption)) {
            Log.d("debugging", "제거 : $wineSmellOption")
            removeSmellKeyword(wineSmellOption)
            Log.d("debugging", "제거 키워드 목록 : ${wineNote.deleteSmellKeywordList}")
        } else {
            Log.d("debugging", "추가 : $wineSmellOption")
            addSmellKeyword(wineSmellOption)
            Log.d("debugging", "추가 키워드 목록 : ${wineNote.addSmellKeywordList}")
        }
    }

    fun isWineSmellSelected(wineSmellOption: WineSmellOption): Boolean {
        val wineNote = currentState.wineNote

        return wineNote.smellKeywordList.contains(wineSmellOption)
    }
}