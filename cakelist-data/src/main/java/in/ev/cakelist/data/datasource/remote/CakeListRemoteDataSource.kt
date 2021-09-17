package `in`.ev.cakelist.data.datasource.remote

import `in`.ev.cakelist.data.model.CakeItem
import `in`.ev.cakelist.data.model.EntityResultWrapper
import `in`.ev.cakelist.data.model.ErrorEntity
import `in`.ev.cakelist.data.network.api.CakeListApi
import com.squareup.moshi.JsonAdapter
import retrofit2.Retrofit
import javax.inject.Inject

class CakeListRemoteDataSource @Inject constructor(
    private val cakeListApi: CakeListApi,
    retrofitClient: Retrofit, moshiAdapter: JsonAdapter<ErrorEntity>
) : BaseDataSource(
    retrofitClient,
    moshiAdapter
) {
    suspend fun getCakeList(): EntityResultWrapper<List<CakeItem>> {
        return getResponse(
            request = { cakeListApi.getCakeList() }
        )
    }

}