package com.teamwiney.data.repository.tastingnote

import android.net.Uri
import com.teamwiney.core.common.base.CommonResponse
import com.teamwiney.core.common.`typealias`.BaseResponse
import com.teamwiney.data.network.adapter.ApiResult
import com.teamwiney.data.network.model.response.PagingResponse
import com.teamwiney.data.network.model.response.TasteAnalysis
import com.teamwiney.data.network.model.response.TastingNote
import com.teamwiney.data.network.model.response.TastingNoteDetail
import com.teamwiney.data.network.model.response.TastingNoteExists
import com.teamwiney.data.network.model.response.TastingNoteFilters
import com.teamwiney.data.network.model.response.TastingNoteIdRes
import kotlinx.coroutines.flow.Flow

interface TastingNoteRepository {

    fun getTasteAnalysis(): Flow<ApiResult<CommonResponse<TasteAnalysis>>>

    fun getCheckTastingNotes(): Flow<ApiResult<CommonResponse<TastingNoteExists>>>

    fun getTastingNotes(
        page: Int,
        size: Int,
        order: Int,
        countries: List<String>,
        wineTypes: List<String>,
        buyAgain: Int?
    ): Flow<ApiResult<CommonResponse<PagingResponse<List<TastingNote>>>>>

    fun getTastingNotesCount(
        order: Int,
        countries: List<String>,
        wineTypes: List<String>,
        buyAgain: Int?
    ): Flow<ApiResult<CommonResponse<PagingResponse<List<TastingNote>>>>>

    fun getTastingNoteFilters(): Flow<ApiResult<CommonResponse<TastingNoteFilters>>>

    fun getTastingNoteDetail(noteId: Int): Flow<ApiResult<CommonResponse<TastingNoteDetail>>>

    fun deleteTastingNote(noteId: Int): Flow<ApiResult<BaseResponse>>

    fun postTastingNote(
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
        smellKeywordList: List<String>,
        imgUris: List<Uri>
    ): Flow<ApiResult<CommonResponse<TastingNoteIdRes>>>

    fun updateTastingNote(
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
        smellKeywordList: List<String>,
        deleteSmellKeywordList: List<String>,
        deleteImgList: List<String>,
        imgUris: List<Uri>
    ): Flow<ApiResult<CommonResponse<TastingNoteIdRes>>>
}