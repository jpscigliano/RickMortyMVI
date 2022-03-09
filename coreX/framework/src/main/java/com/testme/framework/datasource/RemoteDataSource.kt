package com.testme.framework.datasource

import com.testme.domain.AppError
import com.testme.domain.DataSourceResult
import com.testme.framework.errorParser.ErrorParser
import com.testme.framework.utils.NetworkingUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

object RemoteDataSource {

  /**
   * Get RETROFIT response
   *
   * @param mapModelToRequestDto prepares the model to be sent in the request - if nothing is supposed to be sent then write 'preTransform = {}'
   * @param call the retrofit api call - returning a [Response]
   * @param mapResponseDtoToModel transforms from the api response model to the domain module
   */
  suspend fun <REQUEST_API_MODEL, RESPONSE_API_MODEL, MODEL> getRemoteResult(
    mapModelToRequestDto: (suspend () -> REQUEST_API_MODEL)? = null,
    call: suspend (REQUEST_API_MODEL?) -> Response<RESPONSE_API_MODEL>,
    mapResponseDtoToModel: (suspend (RESPONSE_API_MODEL) -> MODEL),
  ): DataSourceResult<MODEL> {
    try {
      // verify internet
      if (withContext(Dispatchers.IO) { !NetworkingUtils.hasInternetConnection() }) {
        return DataSourceResult.NoInternet()
      }

      // call api
      val response = call(mapModelToRequestDto?.invoke())

      return when {
        //////////////////////////
        // SUCCESSFUL
        response.isSuccessful && response.body() != null -> {
          // map/transform
          mapResponseDtoToModel(response.body()!!).let { model ->
            DataSourceResult.Success(model)
          }
        }

        //////////////////////////
        // UNSUCCESSFUL
        !response.isSuccessful -> {
          // parse and return error
          ErrorParser.parseError(response.code()).let { appError ->
            DataSourceResult.Error(appError)
          }
        }

        //////////////////////////
        // ELSE
        else -> {
          DataSourceResult.Error(AppError.UnknownError)
        }
      }
    } catch (e: Exception) {
      e.printStackTrace()
      return DataSourceResult.Error(AppError.UnknownError)
    }
  }
}
