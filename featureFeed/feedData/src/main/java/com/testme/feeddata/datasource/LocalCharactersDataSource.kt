package com.testme.feeddata.datasource

import com.testme.domain.DataSourceResult
import com.testme.feeddomain.model.Character
import com.testme.feeddomain.model.Id
import kotlinx.coroutines.flow.Flow

interface LocalCharactersDataSource {

  suspend fun getCharactersList(): Flow<DataSourceResult<List<Character>>>
  suspend fun getCharacter(id: Id): Flow<DataSourceResult<Character>>

  suspend fun saveCharacters(characters: List<Character>)
  suspend fun saveCharacter(character:Character)
  suspend fun deleteCharacters()
}