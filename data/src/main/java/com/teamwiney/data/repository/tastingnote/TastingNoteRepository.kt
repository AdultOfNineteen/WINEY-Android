package com.teamwiney.data.repository.tastingnote

import com.teamwiney.core.common.base.ResponseWrapper
import com.teamwiney.data.network.adapter.ApiResult
import com.teamwiney.data.network.model.response.PagingData
import com.teamwiney.data.network.model.response.TasteAnalysis
import com.teamwiney.data.network.model.response.TastingNote
import com.teamwiney.data.network.model.response.TastingNoteDetail
import com.teamwiney.data.network.model.response.TastingNoteFilters
import kotlinx.coroutines.flow.Flow

interface TastingNoteRepository {

    fun getTasteAnalysis(): Flow<ApiResult<ResponseWrapper<TasteAnalysis>>>

    fun getTastingNotes(
        page: Int,
        size: Int,
        order: Int,
        countries: List<String>,
        wineTypes: List<String>,
        buyAgain: Int
    ): Flow<ApiResult<ResponseWrapper<PagingData<List<TastingNote>>>>>

    fun getTastingNoteFilters(): Flow<ApiResult<ResponseWrapper<TastingNoteFilters>>>

    fun getTastingNoteDetail(noteId: Int): Flow<ApiResult<ResponseWrapper<TastingNoteDetail>>>
}