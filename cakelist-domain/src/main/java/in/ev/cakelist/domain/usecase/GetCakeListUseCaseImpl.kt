package `in`.ev.cakelist.domain.usecase

import `in`.ev.cakelist.domain.model.Cake
import `in`.ev.cakelist.domain.model.Response
import `in`.ev.cakelist.domain.repository.CakeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCakeListUseCaseImpl @Inject constructor(private val repository: CakeRepository):
    GetCakeListUsecase {
    override suspend fun execute(): Flow<Response<List<Cake>>> {
       return repository.getCakeList()
    }
}