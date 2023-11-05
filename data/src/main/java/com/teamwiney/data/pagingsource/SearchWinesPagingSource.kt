package com.teamwiney.data.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.teamwiney.data.network.adapter.ApiResult
import com.teamwiney.data.network.model.response.SearchWine
import com.teamwiney.data.repository.wine.WineRepository
import kotlinx.coroutines.flow.first

class SearchWinesPagingSource(
    private val wineRepository: WineRepository,
    private val searchKeyword: String
) : PagingSource<Int, SearchWine>() {

    override fun getRefreshKey(state: PagingState<Int, SearchWine>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SearchWine> {
        val currentPage = params.key ?: 0

        val loadData =
            wineRepository.getSearchWines(
                page = currentPage,
                size = params.loadSize,
                content = searchKeyword
            ).first()
        return when (loadData) {
            is ApiResult.Success -> {
                try {
                    val contents = loadData.data.result.contents
                    
                    LoadResult.Page(
                        data = contents,
                        prevKey = if (currentPage == 0) null else currentPage - 1,
                        nextKey = if (loadData.data.result.isLast) null else currentPage + 1
                    )
                } catch (e: Exception) {
                    LoadResult.Error(e)
                }
            }

            is ApiResult.ApiError -> {
                LoadResult.Error(Exception(loadData.message))
            }

            else -> {
                LoadResult.Error(Exception("네트워크 오류가 발생했습니다."))
            }
        }
    }

}