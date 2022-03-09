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
 * [refreshControl] - Mechanism to handle the expiration of cache.
 */
internal class ResourceProvider<in Input, out Output>(
  private val remoteFetch: suspend (Input) -> DataSourceResult<Output>,
  private val localFetch: suspend (Input) -> Flow<DataSourceResult<Output>>,
  private val localStore: suspend (Output) -> Unit,
  private val localDelete: suspend () -> Unit,
  private val refreshControl: CacheControl
) : CacheCleaner, CacheControl by refreshControl {

  init {
    refreshControl.addCacheCleaner(this)
  }

  // Public API
  suspend fun query(args: Input, force: Boolean = false): Flow<DataSourceResult<Output>> = flow {

    //Emit from Cache only once.
    fetchFromLocal(args)?.firstOrNull()?.run { emit(this) }

    //Execute remote call if cache is expired or if the update is forced.
    if (refreshControl.isExpired() || force) {
      fetchFromRemote(args)?.run { emit(this) }
    }

    //Keep listening to cache in case an external mechanism updated it.
    fetchFromLocal(args)?.distinctUntilChanged()?.run { emitAll(this) }

  }

  //Cache cleaner
  override suspend fun cleanup() {
    deleteLocal()
  }

  //Private API
  private suspend fun deleteLocal() = runCatching {
    localDelete()
  }.getOrNull()


  private suspend fun fetchFromLocal(args: Input): Flow<DataSourceResult<Output>>? = runCatching {
    localFetch(args)
  }.getOrNull()

  private suspend fun fetchFromRemote(args: Input): DataSourceResult<Output>? = runCatching {
    remoteFetch(args)
  }.getOrNull()?.also { dataSourceResult ->
    runCatching {
      if (dataSourceResult is DataSourceResult.Success) {
        localStore(dataSourceResult.data)
        refreshControl.refresh()
      }

    }
  }
}