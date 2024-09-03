package com.teamwiney.data.repository.tastingnote

import android.content.Context
import android.net.Uri
import com.teamwiney.core.common.base.CommonResponse
import com.teamwiney.core.common.`typealias`.BaseResponse
import com.teamwiney.data.datasource.tastingnote.TastingNoteDataSource
import com.teamwiney.data.network.adapter.ApiResult
import com.teamwiney.data.network.model.response.PagingResponse
import com.teamwiney.data.network.model.response.TasteAnalysis
import com.teamwiney.data.network.model.response.TastingNote
import com.teamwiney.data.network.model.response.TastingNoteDetail
import com.teamwiney.data.network.model.response.TastingNoteExists
import com.teamwiney.data.network.model.response.TastingNoteFilters
import com.teamwiney.data.network.model.response.TastingNoteIdRes
import com.teamwiney.data.util.fileFromContentUri
import com.teamwiney.data.util.resizeAndSaveImage
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import javax.inject.Inject

class TastingNoteRepositoryImpl @Inject constructor(
    private val tastingNoteDataSource: TastingNoteDataSource,
    @ApplicationContext private val context: Context
) : TastingNoteRepository {

    override fun getTasteAnalysis(): Flow<ApiResult<CommonResponse<TasteAnalysis>>> =
        tastingNoteDataSource.getTasteAnalysis()

    override fun getCheckTastingNotes(): Flow<ApiResult<CommonResponse<TastingNoteExists>>> =
        tastingNoteDataSource.getCheckTastingNotes()

    override fun getTastingNotes(
        page: Int,
        size: Int,
        order: Int,
        countries: List<String>,
        wineTypes: List<String>,
        buyAgain: Int?,
        wineId: Int?
    ): Flow<ApiResult<CommonResponse<PagingResponse<List<TastingNote>>>>> =
        tastingNoteDataSource.getTastingNotes(page, size, order, countries, wineTypes, buyAgain, wineId)

    override fun getTastingNotesCount(
        order: Int,
        countries: List<String>,
        wineTypes: List<String>,
        buyAgain: Int?,
    ): Flow<ApiResult<CommonResponse<PagingResponse<List<TastingNote>>>>> =
        tastingNoteDataSource.getTastingNotes(1, 1, order, countries, wineTypes, buyAgain, null)

    override fun getTastingNoteFilters(): Flow<ApiResult<CommonResponse<TastingNoteFilters>>> =
        tastingNoteDataSource.getTastingNoteFilters()

    override fun getTastingNoteDetail(noteId: Int): Flow<ApiResult<CommonResponse<TastingNoteDetail>>> =
        tastingNoteDataSource.getTastingNoteDetail(noteId)

    override fun deleteTastingNote(noteId: Int): Flow<ApiResult<BaseResponse>> =
        tastingNoteDataSource.deleteTastingNote(noteId)

    override fun postTastingNote(
        wineId: Long,
        officialAlcohol: Double?,
        alcohol: Int,
        color: String,
        sweetness: Int,
        acidity: Int,
        body: Int,
        tannin: Int,
        finish: Int,
        memo: String,
        rating: Int,
        vintage: String,
        price: String,
        buyAgain: Boolean?,
        public: Boolean?,
        smellKeywordList: List<String>,
        imgUris: List<Uri>
    ): Flow<ApiResult<CommonResponse<TastingNoteIdRes>>> {
        val jsonObjectBuilder = JSONObject().apply {
            put("wineId", wineId)
            put("officialAlcohol", officialAlcohol)
            put("alcohol", alcohol)
            put("color", color)
            put("sweetness", sweetness)
            put("acidity", acidity)
            put("body", body)
            put("tannin", tannin)
            put("finish", finish)
            put("memo", memo)
            put("rating", rating)
            if (vintage.isNotEmpty()) put("vintage", vintage.toInt())
            if (price.isNotEmpty()) put("price", price.toInt())
            buyAgain?.let { put("buyAgain", it) }
            public?.let { put("public", it) }
            put("smellKeywordList", JSONArray().apply { smellKeywordList.forEach { put(it) } })
        }

        val request = jsonObjectBuilder.toString().toRequestBody("application/json".toMediaType())
        val multipartFiles = imgUris.map {
            val originalFile = fileFromContentUri(context, it)
            val compressedFile = resizeAndSaveImage(context, originalFile)
            val requestBody: RequestBody = compressedFile.asRequestBody("image/*".toMediaType())
            MultipartBody.Part.createFormData("multipartFiles", compressedFile.name, requestBody)
        }

        return tastingNoteDataSource.postTastingNote(request, multipartFiles)
    }

    override fun updateTastingNote(
        noteId: Int,
        officialAlcohol: Double?,
        alcohol: Int,
        color: String,
        sweetness: Int,
        acidity: Int,
        body: Int,
        tannin: Int,
        finish: Int,
        memo: String,
        rating: Int,
        vintage: String,
        price: String,
        buyAgain: Boolean?,
        public: Boolean?,
        smellKeywordList: List<String>,
        deleteSmellKeywordList: List<String>,
        deleteImgList: List<String>,
        imgUris: List<Uri>
    ): Flow<ApiResult<CommonResponse<TastingNoteIdRes>>> {
        val jsonObjectBuilder = JSONObject().apply {
            put("officialAlcohol", officialAlcohol)
            put("alcohol", alcohol)
            put("color", color)
            put("sweetness", sweetness)
            put("acidity", acidity)
            put("body", body)
            put("tannin", tannin)
            put("finish", finish)
            put("memo", memo)
            put("rating", rating)
            if (vintage.isNotEmpty()) put("vintage", vintage.toInt())
            if (price.isNotEmpty()) put("price", price.toInt())
            buyAgain?.let { put("buyAgain", it) }
            public?.let { put("public", it) }
            put("smellKeywordList", JSONArray().apply { smellKeywordList.forEach { put(it) } })
            put("deleteSmellKeywordList", JSONArray().apply { deleteSmellKeywordList.forEach { put(it) }})
            put("deleteImgList", JSONArray().apply { deleteImgList.forEach { put(it) } })
        }

        val request = jsonObjectBuilder.toString().toRequestBody("application/json".toMediaType())
        val multipartFiles = imgUris.map {
            val originalFile = fileFromContentUri(context, it)
            val compressedFile = resizeAndSaveImage(context, originalFile)
            val requestBody: RequestBody = compressedFile.asRequestBody("image/*".toMediaType())
            MultipartBody.Part.createFormData("multipartFiles", compressedFile.name, requestBody)
        }

        return tastingNoteDataSource.updateTastingNote(noteId, request, multipartFiles)
    }
}