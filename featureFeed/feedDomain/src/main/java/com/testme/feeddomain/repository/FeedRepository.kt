package com.testme.feeddomain.repository

import com.testme.domain.DataSourceResult
import com.testme.feeddomain.model.Character
import com.testme.feeddomain.model.Id
import kotlinx.coroutines.flow.Flow


interface FeedRepository {
  suspend fun getCharacters(): Flow<DataSourceResult<List<Character>>>
  suspend fun getCharacterById(characterId: Id): Flow<DataSourceResult<Character>>
}
