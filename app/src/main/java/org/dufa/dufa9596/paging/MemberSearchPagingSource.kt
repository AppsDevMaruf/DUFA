package org.dufa.dufa9596.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import org.dufa.dufa9596.api.SecuredApi
import org.dufa.dufa9596.data.models.search.Data
import org.dufa.dufa9596.data.models.search.RequestSearch
import org.dufa.dufa9596.data.models.search.ResponseSearch

class MemberSearchPagingSource(
    private val api: SecuredApi,
    var requestSearch: RequestSearch,
    var hasData: (hasData: Boolean) -> Unit,

) : PagingSource<Int, Data>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Data> {

        return try {
            val position = params.key ?: 1
            val newRequestSearch = requestSearch.copy(page = position)
            val response: ResponseSearch = api.getSearchResult(newRequestSearch)
            if (response.searchData.data.isEmpty()) {
                hasData.invoke(false)
            } else {
                hasData.invoke(true)
            }

            LoadResult.Page(
                data = response.searchData.data,
                prevKey = if (position == 1) null else position - 1,
                nextKey = if (position == response.searchData.lastPage) null else position + 1
            )


        } catch (e: Exception) {
            LoadResult.Error(e)
        }

    }

    override fun getRefreshKey(state: PagingState<Int, Data>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

}