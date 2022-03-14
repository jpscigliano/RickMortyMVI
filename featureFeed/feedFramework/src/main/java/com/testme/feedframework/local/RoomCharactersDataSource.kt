package com.testme.feedframework.local

import com.testme.domain.DataSourceResult
import com.testme.feeddata.datasource.LocalCharactersDataSource
import com.testme.feeddomain.model.Character
import com.testme.feeddomain.model.Id
import com.testme.feedframework.local.room.dao.CharactersDao
import com.testme.feedframework.local.room.entity.CharacterEntity
import com.testme.framework.datasource.LocalDataSource
import com.testme.framework.mapper.Mapper
import com.testme.framework.mapper.toList
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class RoomCharactersDataSource @Inject constructor(
  private val charactersDao: CharactersDao,
  private val characterEntityToModelMapper: Mapper<CharacterEntity, Character>,
  private val characterModelToEntityMapper: Mapper<Character, CharacterEntity>
) : LocalCharactersDataSource {
  override suspend fun getCharactersList(): Flow<DataSourceResult<List<Character>>> =
    LocalDataSource.getLocalResultAsFlow(
      call = { charactersDao.getAll() },
      entityToModelMapper = { result -> characterEntityToModelMapper.toList().map(result) }
    )


  override suspend fun getCharacter(id: Id): Flow<DataSourceResult<Character>> =
    LocalDataSource.getLocalResultAsFlow(
      call = { charactersDao.get(id()) },
      entityToModelMapper = { result -> characterEntityToModelMapper.map(result) }
    )


  override suspend fun saveCharacters(characters: List<Character>) =
    LocalDataSource.insertToLocal(
      transformModelToEntity = { characterModelToEntityMapper.toList().map(characters) },
      call = { entity -> charactersDao.insert(entity) }
    )


  override suspend fun saveCharacter(character: Character) {
    LocalDataSource.insertToLocal(
      transformModelToEntity = { characterModelToEntityMapper.map(character) },
      call = { entity -> charactersDao.insert(entity) }
    )
  }

  override suspend fun deleteCharacters() {
    charactersDao.deleteAll()
  }
}