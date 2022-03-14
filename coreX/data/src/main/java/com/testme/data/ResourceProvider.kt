package com.testme.data

import com.testme.data.cache.CacheCleaner
import com.testme.data.cache.CacheControl
import com.testme.domain.DataSourceResult
import kotlinx.coroutines.flow.*

/**
 * ResourceProvider is manager class that emits [DataSourceResult] objects base on the following high order functions;
 * [remoteFetch] - Executes an async network request. Success results will be store as cache.
 * [localFetch] - Makes an async call (e.g. network call), returns the result wrapped in a [DataSourceResult]
 * [localStore] - Saves the response of [remoteFetch] as new cache data
 * [localDelete] - query to be execute to clean the cache.
 * [cacheController] - Mechanism to handle the expiration of cache.
 */
internal class ResourceProvider<in Input, out Output>(
  private val remoteFetch: suspend (Input) -> DataSourceResult<Output>,
  private val localFetch: suspend (Input) -> Flow<DataSourceResult<Output>>,
  private val localStore: suspend (Output) -> Unit,
  private val localDelete: suspend () -> Unit,
  private val cacheController: CacheControl
) : CacheCleaner, CacheControl by cacheController {

  init {
    cacheController.addCacheCleaner(this)
  }

  fun x() {
  }

  // Public API
  suspend fun query(args: Input, force: Boolean = false): Flow<DataSourceResult<Output>> = flow {

    if (cacheController.isExpired() || force) {
      //1. Emit from Cache.  Only once at the beginig of the flow.
      fetchFromLocal(args)?.firstOrNull()?.run { emit(this) }
      //2. Emit InProgress, since api cal will be executed.
      emit(DataSourceResult.InProgress())
      val remoteResponse = fetchFromRemoteAndStoreInLocal(args)
      if (remoteResponse !is DataSourceResult.Success) {
        //3. Emit Errors or No internet.
        remoteResponse?.run { emit(this) }
      } else {
        //4. Keep emitting.
        fetchFromLocal(args)?.run { emitAll(this) }
      }
    } else {
      //Keep listening to cache in case an external mechanism updated it.
      fetchFromLocal(args)?.run { emitAll(this) }
    }

  }

  //Cache cleaner
  override suspend fun cleanup() {
    deleteLocal()
  }

  //Private API
  private suspend fun deleteLocal(): Unit? = runCatching {
    localDelete()
  }.getOrNull()


  private suspend fun fetchFromLocal(args: Input): Flow<DataSourceResult<Output>>? = runCatching {
    localFetch(args)
  }.getOrNull()

  /**
   * Executes the remote API call and if a Success response with data is retrieve then store in localDB
   * Accepts [Input] as arguments.
   *
   */

  private suspend fun fetchFromRemoteAndStoreInLocal(args: Input): DataSourceResult<Output>? = runCatching {
    remoteFetch(args)
  }.getOrNull()?.also { dataSourceResult ->
    runCatching {
      if (dataSourceResult is DataSourceResult.Success) {
        if (dataSourceResult.data != null)
          localStore(dataSourceResult.data!!)
        cacheController.refresh()
      }
    }
  }
}