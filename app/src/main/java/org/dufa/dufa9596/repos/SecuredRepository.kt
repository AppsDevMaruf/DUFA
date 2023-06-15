package org.dufa.dufa9596.repos

import androidx.paging.Pager
import androidx.paging.PagingConfig
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.dufa.dufa9596.api.SecuredApi
import org.dufa.dufa9596.data.models.fund_collection.RequestFundCollection
import org.dufa.dufa9596.data.models.locations.RequestSetCLocation
import org.dufa.dufa9596.data.models.payRenew.RequestPayRenew
import org.dufa.dufa9596.data.models.search.RequestSearch
import org.dufa.dufa9596.paging.MemberSearchPagingSource
import org.dufa.dufa9596.data.models.profile.RequestProfileUpdate
import javax.inject.Inject

class SecuredRepository @Inject constructor(private val securedApi: SecuredApi) {

    suspend fun getMemberSearchRepo(
        requestSearch: RequestSearch,
        hasData: (hasData: Boolean) -> Unit
    ) = Pager(
        config = PagingConfig(pageSize = 15, maxSize = 150),
        pagingSourceFactory = {
            MemberSearchPagingSource(
                securedApi,
                requestSearch,
                hasData
            )
        }
    ).flow

    suspend fun logout() = securedApi.logout()
    suspend fun getTransactionHistory() = securedApi.getTransactionHistory()
    suspend fun getDashboardInfo() = securedApi.getDashboardInfo()
    suspend fun getProfile() = securedApi.getProfileInfo()
    suspend fun getDistricts() = securedApi.getDistricts()
    suspend fun getOccupations() = securedApi.getOccupations()
    suspend fun getHalls() = securedApi.getHalls()
    suspend fun uploadProfilePic(userId: Int, part: MultipartBody.Part) =
        securedApi.uploadProfilePic(userId, part)

    suspend fun updateProfile(userId: Int, requestProfileUpdate: RequestProfileUpdate) =
        securedApi.updateProfile(userId, requestProfileUpdate)

    suspend fun uploadVoucher(
        date: RequestBody,
        amount: RequestBody,
        voucher_number: RequestBody,
        part: MultipartBody.Part
    ) = securedApi.uploadVoucher(
        date = date,
        amount = amount,
        voucher_number = voucher_number,
        image = part
    )

    fun payRenew(requestPayRenew: RequestPayRenew) = securedApi.payRenew(requestPayRenew)
    fun fundCollection(fund: RequestFundCollection) = securedApi.fundPayment(fund)
    suspend fun setCurrentLocation(requestSetCLocation: RequestSetCLocation) =
        securedApi.setCurrentLocations(requestSetCLocation)

    suspend fun userLocations() = securedApi.getUserLocations()
    suspend fun getFeeListRepo() = securedApi.getFeeList()
    suspend fun getDepartmentsRepo() = securedApi.getDepartments()
    suspend fun getBloodGroupsRepo() = securedApi.getBloodGroups()
    suspend fun getVoucherListRepo() = securedApi.getVoucherList()
}