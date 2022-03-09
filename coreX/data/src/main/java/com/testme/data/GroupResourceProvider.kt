package com.testme.data

import com.testme.data.cache.CacheControl
import com.testme.domain.DataSourceResult
import kotlinx.coroutines.flow.Flow

class GroupResourceProvider<in Input, in Key, out Output>(
  remoteGroupFetch: suspend (Input) -> DataSourceResult<List<Output>>,
  localGroupFetch: suspend (Input) -> Flow<DataSourceResult<List<Output>>>,
  localGroupStore: suspend (List<Output>) -> Unit,
  private val remoteFetch: suspend (Key, Input) -> DataSourceResult<Output>,
  private val localFetch: suspend (Key, Input) -> Flow<DataSourceResult<Output>>,
  private val localStore: suspend (Output) -> Unit,
  localDelete: suspend () -> Unit,
  private val cacheControl: CacheControl
) : CacheControl by cacheControl {

  private val groupResource = ResourceProvider(
    remoteGroupFetch,
    localGroupFetch,
    localGroupStore,
    localDelete,
    cacheControl
  )

  private val resourceMap: MutableMap<Key, ResourceProvider<Input, Output>> = mutableMapOf()

  // Public API
  suspend fun query(args: Input, force: Boolean = false): Flow<DataSourceResult<List<Output>>> =
    groupResource.query(args, force)

  suspend fun queryByKey(
    key: Key,
    args: Input,
    force: Boolean = false
  ): Flow<DataSourceResult<Output>> =
    singleResource(key).query(args, force)

  // Private API
  private fun singleResource(key: Key) =
    resourceMap[key] ?: ResourceProvider<Input, Output>(
      { remoteFetch(key, it) },
      { localFetch(key, it) },
      { localStore(it) },
      { }, // Delete will be triggered by parent
      cacheControl.createChild()
    ).also { resourceMap[key] = it }
}
