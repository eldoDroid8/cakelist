package `in`.ev.cakelist.data.network.api

import `in`.ev.cakelist.data.model.CakeItem
import retrofit2.Response
import retrofit2.http.GET

interface CakeListApi {
    @GET("waracle_cake-android-client")
    suspend fun getCakeList(): Response<List<CakeItem>>

}