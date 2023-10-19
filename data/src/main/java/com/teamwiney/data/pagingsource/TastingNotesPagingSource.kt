package com.teamwiney.data.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.teamwiney.data.network.adapter.ApiResult
import com.teamwiney.data.network.model.response.TastingNote
import com.teamwiney.data.network.model.response.toDomain
import com.teamwiney.data.repository.tastingnote.TastingNoteRepository
import kotlinx.coroutines.flow.first

class TastingNotesPagingSource(
    private val tastingNoteRepository: TastingNoteRepository,
    private val order: Int,
    private val countries: List<String>,
    private val wineTypes: List<String>,
    private val buyAgain: Int
) : PagingSource<Int, TastingNote>() {

    override fun getRefreshKey(state: PagingState<Int, TastingNote>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TastingNote> {
        val currentPage = params.key ?: 0

        val loadData =
            tastingNoteRepository.getTastingNotes(
                page = currentPage,
                size = params.loadSize,
                order = order,
                countries = countries,
                wineTypes = wineTypes,
                buyAgain = buyAgain
            ).first()
        return when (loadData) {
            is ApiResult.Success -> {
                try {
                    val contents = loadData.data.result.contents.map { it.toDomain() }

                    LoadResult.Page(
                        data = contents,
                        prevKey = if (currentPage == 0) null else currentPage - 1,
                        nextKey = if (loadData.data.result.isLast) null else currentPage + 1,
                    )
                } catch (e: Exception) {
                    LoadResult.Error(e)
                }
            }

            is ApiResult.ApiError  -> {
                LoadResult.Error(Exception(loadData.message))
            }

            else -> {
                LoadResult.Error(Exception("네트워크 오류가 발생했습니다."))
            }
        }
    }
}