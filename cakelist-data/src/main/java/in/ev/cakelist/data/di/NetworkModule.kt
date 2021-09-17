package `in`.ev.cakelist.data.di
import `in`.ev.cakelist.data.BuildConfig
import `in`.ev.cakelist.data.model.ErrorEntity
import `in`.ev.cakelist.data.network.api.CakeListApi
import `in`.ev.subreddit.data.utils.Constants
import androidx.core.os.BuildCompat
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    }

    @Provides
    @Singleton
    fun provideOkhttpClient(): OkHttpClient {
        val httpClient = OkHttpClient().newBuilder()
        if (BuildConfig.DEBUG) {
            val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            return httpClient.addInterceptor(interceptor).build()
        }
        return httpClient.build()
    }

    @Provides
    @Singleton
    fun provideRetrofitClient(
        okHttpClient: OkHttpClient, converterFactory:
        MoshiConverterFactory,  url: String
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(url)
            .client(okHttpClient)
            .addConverterFactory(converterFactory)
            .build()
    }

    @Provides
    @Singleton
    fun provideMoshiErrorAdapter(moshi: Moshi): JsonAdapter<ErrorEntity> {
        return moshi.adapter(ErrorEntity::class.java)
    }


    @Provides
    @Singleton
    fun provideUrl(): String {
        //we can move the url to buildConfig
        return Constants.ENDPOINT
    }

    @Provides
    @Singleton
    fun provideMoshiConverterFactory(moshi: Moshi): MoshiConverterFactory{
        return  MoshiConverterFactory.create(moshi)
    }

    @Provides
    @Singleton
    fun provideSubRedditApiService(retrofit: Retrofit): CakeListApi {
        return retrofit.create(CakeListApi::class.java)
    }

}