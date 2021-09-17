package `in`.ev.cakelist.domain.repository

import `in`.ev.cakelist.domain.model.Cake
import `in`.ev.cakelist.domain.model.Response
import kotlinx.coroutines.flow.Flow

interface CakeRepository {
    suspend fun getCakeList(): Flow<Response<List<Cake>>>
}