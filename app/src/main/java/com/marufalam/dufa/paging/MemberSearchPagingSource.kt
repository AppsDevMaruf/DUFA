package com.marufalam.dufa.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.marufalam.dufa.api.SecuredApi
import com.marufalam.dufa.data.models.search.Data
import com.marufalam.dufa.data.models.search.ResponseSearch

class MemberSearchPagingSource(
    private val api: SecuredApi,
    var totalPage: Int,
    private var searchParam: String,

    ) :
    PagingSource<Int, Data>() {


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Data> {


        return try {
            val position = params.key ?: 1
            val response: ResponseSearch = api.getSearchResult(position)



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