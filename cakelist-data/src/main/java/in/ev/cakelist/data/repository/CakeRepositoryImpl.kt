package `in`.ev.cakelist.data.repository

import `in`.ev.cakelist.data.datasource.remote.CakeListRemoteDataSource
import `in`.ev.cakelist.data.mappers.toDomain
import `in`.ev.cakelist.data.model.EntityResultWrapper
import `in`.ev.cakelist.domain.model.Cake
import `in`.ev.cakelist.domain.model.Response
import `in`.ev.cakelist.domain.repository.CakeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CakeRepositoryImpl @Inject constructor(private val dataSource: CakeListRemoteDataSource) :
    CakeRepository {

    override suspend fun getCakeList(): Flow<Response<List<Cake>>> {
        return flow {
            emit(Response.Loading(true))
            when(val result = dataSource.getCakeList()) {
                is EntityResultWrapper.Success -> {

                    val cakeList = result.data?.map { toDomain(it) }.distinctBy { it.title }.sortedBy { it.title }
                    emit(Response.Loading(false))
                    emit(Response.ApiCallSuccess(cakeList))
                }
                is EntityResultWrapper.Error -> {
                    val error = toDomain(result.error)
                    emit(Response.Loading(false))
                    emit(Response.ApiCallError(error))
                }
            }
        }.flowOn(Dispatchers.IO)
    }
}