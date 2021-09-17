package `in`.ev.cakelist.domain.usecase

import `in`.ev.cakelist.domain.model.Cake
import `in`.ev.cakelist.domain.model.Response
import kotlinx.coroutines.flow.Flow

interface GetCakeListUsecase {
    suspend fun execute(): Flow<Response<List<Cake>>>
}