package com.testme.feeddata.datasource

import com.testme.domain.DataSourceResult
import com.testme.feeddomain.model.Character
import com.testme.feeddomain.model.Id

interface CharactersDataSource {
  suspend fun getCharactersList(): DataSourceResult<List<Character>>
  suspend fun getCharacter(id: Id): DataSourceResult<Character>

}