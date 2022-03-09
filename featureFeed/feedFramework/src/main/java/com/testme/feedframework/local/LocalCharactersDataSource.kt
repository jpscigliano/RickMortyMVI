package com.testme.feedframework.local

import com.testme.domain.DataSourceResult
import com.testme.feeddata.datasource.LocalCharactersDataSource
import com.testme.feeddomain.model.Character
import com.testme.feeddomain.model.Id
import kotlinx.coroutines.flow.Flow


class LocalCharactersDataSource(

) : LocalCharactersDataSource {
  override suspend fun getCharactersList(): Flow<DataSourceResult<List<Character>>> {
    TODO("Not yet implemented")
  }

  override suspend fun saveCharacters(characters: List<Character>) {
    TODO("Not yet implemented")
  }

  override suspend fun getCharacter(id: Id): Flow<DataSourceResult<Character>> {
    TODO("Not yet implemented")
  }

  override suspend fun saveCharacter(character: Character) {
    TODO("Not yet implemented")
  }

  override suspend fun deleteCharacters() {
    TODO("Not yet implemented")
  }
}