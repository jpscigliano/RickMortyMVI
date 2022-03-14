package com.testme.framework.datasource

import com.testme.domain.DataSourceResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

object LocalDataSource {
  fun <MODEL_ROOM, MODEL> getLocalResultAsFlow(
    call: () -> Flow<MODEL_ROOM>,
    entityToModelMapper: (MODEL_ROOM) -> MODEL,
  ): Flow<DataSourceResult<MODEL>> {
    return call().map { it?.let { entityToModelMapper(it) } }.map { DataSourceResult.Success(it) }
      .flowOn(Dispatchers.Default)
  }

  suspend fun <MODEL_ROOM> insertToLocal(
    transformModelToEntity: () -> MODEL_ROOM,
    call: suspend (MODEL_ROOM) -> Unit
  ) {
    withContext(Dispatchers.Default) {
      call(transformModelToEntity())
    }
  }
}