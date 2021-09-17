package `in`.ev.cakelist.data.di

import `in`.ev.cakelist.data.datasource.remote.CakeListRemoteDataSource
import `in`.ev.cakelist.data.model.ErrorEntity
import `in`.ev.cakelist.data.network.api.CakeListApi
import com.squareup.moshi.JsonAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {
    @Provides
    @Singleton
    fun provideDataSource(
        api: CakeListApi, retrofit: Retrofit, moshiAdapter:
        JsonAdapter<ErrorEntity>
    ): CakeListRemoteDataSource {
        return CakeListRemoteDataSource(api, retrofit, moshiAdapter)
    }
}

/*@Module
@InstallIn(SingletonComponent::class)
object RepoModule {
    @Provides
    @Singleton
    fun provideRepoImpl(
        dataSource: CakeListRemoteDataSource
    ): SubRedditRepository {
        return RemoteRepoImpl(dataSource)
    }

}*/
