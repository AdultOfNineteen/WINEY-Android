package com.teamwiney.notewrite

import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.FileUtils.copy
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.teamwiney.core.common.base.BaseViewModel
import com.teamwiney.data.network.adapter.ApiResult
import com.teamwiney.data.network.model.response.SearchWine
import com.teamwiney.data.pagingsource.SearchWinesPagingSource
import com.teamwiney.data.repository.tastingnote.TastingNoteRepository
import com.teamwiney.data.repository.wine.WineRepository
import com.teamwiney.data.util.getFileExtension
import com.teamwiney.notewrite.model.SmellKeyword
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.FileOutputStream
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

    fun writeTastingNote() = viewModelScope.launch {
        /** List형태 MultiPart 설정 */
        val smellKeywordList: ArrayList<MultipartBody.Part> =
            ArrayList<MultipartBody.Part>().apply {
                currentState.wineNote.smellKeywordList.forEach {
                    add(MultipartBody.Part.createFormData("smellKeywordList", it))
                }
            }

        /** 단일 인자 MultiPart 설정 */
        val wineNoteWriteRequest = hashMapOf<String, RequestBody>()
        with(currentState.wineNote) {
            wineNoteWriteRequest["wineId"] = wineId.toString().toPlainRequestBody()
            wineNoteWriteRequest["alcohol"] = alcohol.toString().toPlainRequestBody()
            wineNoteWriteRequest["color"] = color.toPlainRequestBody()
            wineNoteWriteRequest["sweetness"] = sweetness.toString().toPlainRequestBody()
            wineNoteWriteRequest["acidity"] = acidity.toString().toPlainRequestBody()
            wineNoteWriteRequest["body"] = body.toString().toPlainRequestBody()
            wineNoteWriteRequest["tannin"] = body.toString().toPlainRequestBody()
            wineNoteWriteRequest["finish"] = body.toString().toPlainRequestBody()
            wineNoteWriteRequest["memo"] = memo.toPlainRequestBody()
            if (vintage.isNotEmpty()) {
                wineNoteWriteRequest["vintage"] = vintage.toPlainRequestBody()
            }
            if (price.isNotEmpty()) {
                wineNoteWriteRequest["price"] = price.toPlainRequestBody()
            }
            buyAgain?.let {
                wineNoteWriteRequest["buyAgain"] = buyAgain.toString().toPlainRequestBody()
            }
        }

        /** TODO 이미지 파일 변환 */
        val multipartFiles = convetProfileImageToMultipartFile()

        tastingNoteRepository.postTastingNote(
            wineNoteWriteRequest = wineNoteWriteRequest,
            smellKeywordList = smellKeywordList,
            multipartFiles = multipartFiles
        ).collectLatest {
            when (it) {
                is ApiResult.ApiError -> TODO()
                is ApiResult.NetworkError -> TODO()
                is ApiResult.Success -> TODO()
            }
        }
    }

    private fun convetProfileImageToMultipartFile(): List<MultipartBody.Part> {
        return currentState.wineNote.imgs.map {
            val file = fileFromContentUri(context, it)
            val requestBody: RequestBody = file.asRequestBody("image/*".toMediaType())
            MultipartBody.Part.createFormData("multipartFile", file.name, requestBody)
        }
    }

    private fun fileFromContentUri(context: Context, contentUri: Uri): File {
        val fileExtension = getFileExtension(context, contentUri)
        val fileName = "temp_file" + if (fileExtension != null) ".$fileExtension" else ""

        val tempFile = File(context.cacheDir, fileName)
        tempFile.createNewFile()
        try {
            val oStream = FileOutputStream(tempFile)
            val inputStream = context.contentResolver.openInputStream(contentUri)

            inputStream?.let {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    copy(inputStream, oStream)
                } else {
                    TODO("VERSION.SDK_INT < Q")
                }
            }

            oStream.flush()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return tempFile
    }

    private fun String.toPlainRequestBody() =
        requireNotNull(this).toRequestBody("text/plain".toMediaType())

    fun updateVintage(vintage: String) = viewModelScope.launch {
        updateState(currentState.copy(wineNote = currentState.wineNote.copy(vintage = vintage)))
    }

    fun updateAlcohol(alcohol: Int) = viewModelScope.launch {
        updateState(currentState.copy(wineNote = currentState.wineNote.copy(alcohol = alcohol)))
    }

    fun updatePrice(price: String) = viewModelScope.launch {
        updateState(currentState.copy(wineNote = currentState.wineNote.copy(price = price)))
    }

    fun updateColor(color: String) = viewModelScope.launch {
        updateState(currentState.copy(wineNote = currentState.wineNote.copy(color = color)))
    }

    fun updateSmellKeywordList(smellKeywordList: List<WineSmellOption>) = viewModelScope.launch {
        updateState(currentState.copy(wineNote = currentState.wineNote.copy(smellKeywordList = smellKeywordList.mapNotNull {
            SmellKeyword.find(
                it.name
            )
        })))
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
        updateState(currentState.copy(wineNote = currentState.wineNote.copy(memo = memo)))
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

}