package `in`.ev.cakelist.data.datasource.remote

import `in`.ev.cakelist.data.model.EntityResultWrapper
import `in`.ev.cakelist.data.model.ErrorEntity
import com.squareup.moshi.JsonAdapter
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.Retrofit
import java.io.IOException
import java.net.SocketException

abstract class BaseDataSource constructor(
        val retrofit: Retrofit, private val moshiErrorAdapter:
        JsonAdapter<ErrorEntity>
    ) {
        protected suspend fun <T : Any> getResponse(
            request: suspend () -> Response<T>
        ): EntityResultWrapper<T> {
            return try {
                val result = request.invoke()
                return if (result.isSuccessful) {
                    EntityResultWrapper.Success(result.body()!!)
                } else {
                    val errorResponse: ErrorEntity = parseError(result)
                    EntityResultWrapper.Error(errorResponse)
                }
            } catch (e: IOException) {
                EntityResultWrapper.Error(ErrorEntity(status_message = "Unknown error"))
            } catch (e: SocketException) {
                EntityResultWrapper.Error(ErrorEntity(status_message = "Please check your network connection"))
            } catch (e: HttpException) {
                val errorResponse = convertErrorBody(e)
                EntityResultWrapper.Error(errorResponse)
            } catch (e: KotlinNullPointerException) {
                EntityResultWrapper.Error(ErrorEntity(status_message = "Empty response"))
            }

        }

        private fun parseError(response: Response<*>): ErrorEntity {
            val converter = retrofit.responseBodyConverter<ErrorEntity>(
                ErrorEntity::class.java, arrayOfNulls
                    (0)
            )
            return try {
                val errorEntity = converter.convert(response.errorBody()) ?: ErrorEntity(
                    status_message = "Unknown " + "error")
                errorEntity

            } catch (e: IOException) {
                ErrorEntity(status_message = "Io Exception")
            }
        }

        private fun convertErrorBody(throwable: HttpException): ErrorEntity {
            return try {
                val response = moshiErrorAdapter.fromJson(throwable.response()?.errorBody()?.source())
                response ?: ErrorEntity(status_message = "Unknown Error")
            } catch (exception: Exception) {
                val errorEntity = ErrorEntity(status_message = "Unknown error")
                errorEntity
            }
        }

}