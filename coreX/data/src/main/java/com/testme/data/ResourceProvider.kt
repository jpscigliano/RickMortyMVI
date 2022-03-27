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
internal class ResourceProvider<in Input, Output>(
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

  /** Public API
   * Executes the flow that emit a [DataSource<Output>]
   * @param args [Input],  arguments for the remote API call.
   * @param force [Boolean]. Force the update of the Cache.
   * Steps
   * 1. Emit from Cache.
   * 2. If  Cache is expired or Forced, then make API call and update Cache.
   *  2.1 Make API Call
   *  2.2 Update Cache.
   *  2.3 Emit Result from Local. If  the Result is Error Then add LocalData.
   */
  suspend fun query(args: Input, force: Boolean = false): Flow<DataSourceResult<Output>> = flow {

    //1. Emit from Cache.
    val localData: DataSourceResult<Output>? = fetchFromLocal(args)
    localData?.run { emit(this) }

    //2.
    if (cacheController.isExpired() || force || localData == null) {
      emit(DataSourceResult.InProgress())
      //2.1 - 2.2  Fetch Remote and Update cache.
      fetchFromRemoteAndStoreInLocal(args)
        //2.3  Emit Result with Cache
        .mapWithData(fetchFromLocal(args)?.dataOrNull()).also {
          emit(it)
        }
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


  private suspend fun fetchFromLocal(args: Input): DataSourceResult<Output>? =
    localFetch(args).firstOrNull()


  /**
   * Executes the remote API call and if a Success response with data is retrieve then store in localDB
   * @param args  for the remote call.
   */
  private suspend fun fetchFromRemoteAndStoreInLocal(args: Input): DataSourceResult<Output> {
    return remoteFetch(args).also { dataSourceResult ->
      runCatching {
        if (dataSourceResult is DataSourceResult.Success) {
          if (dataSourceResult.data != null)
            localStore(dataSourceResult.data!!)
          else
            localDelete()

          cacheController.refresh()
        }
      }
    }
  }
}