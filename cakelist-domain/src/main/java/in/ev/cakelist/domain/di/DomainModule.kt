package `in`.ev.cakelist.domain.di

import `in`.ev.cakelist.domain.usecase.GetCakeListUseCaseImpl
import `in`.ev.cakelist.domain.usecase.GetCakeListUsecase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class UseCaseModule {
    @Binds
    @Singleton
    abstract fun bindGetCakeListUseCase(useCaseImpl: GetCakeListUseCaseImpl):
            GetCakeListUsecase

}