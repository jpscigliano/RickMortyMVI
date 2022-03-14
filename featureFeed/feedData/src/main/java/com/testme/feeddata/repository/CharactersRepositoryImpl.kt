package com.testme.feeddata.repository

import com.testme.data.GroupResourceProvider
import com.testme.data.cache.CacheControl
import com.testme.domain.DataSourceResult
import com.testme.feeddata.datasource.CharactersDataSource
import com.testme.feeddata.datasource.LocalCharactersDataSource
import com.testme.feeddomain.model.Character
import com.testme.feeddomain.model.Id
import com.testme.feeddomain.repository.FeedRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class CharactersRepositoryImpl @Inject constructor(
  private val networkCharactersDataSource: CharactersDataSource,
  private val localCharactersDataSource: LocalCharactersDataSource,
  private val cacheControl: CacheControl
) : FeedRepository {

  private val feedResourceProvider = GroupResourceProvider<Unit, Id, Character>(
    remoteGroupFetch = { networkCharactersDataSource.getCharactersList() },
    localGroupFetch = { localCharactersDataSource.getCharactersList() },
    localGroupStore = { characters -> localCharactersDataSource.saveCharacters(characters) },
    remoteFetch = { id, _ -> networkCharactersDataSource.getCharacter(id) },
    localFetch = { id, _ -> localCharactersDataSource.getCharacter(id) },
    localStore = { character -> localCharactersDataSource.saveCharacter(character) },
    localDelete = { localCharactersDataSource.deleteCharacters() },
    cacheControl = cacheControl
  )


  override suspend fun getCharacters(): Flow<DataSourceResult<List<Character>>> =
    feedResourceProvider.query(Unit, false)


  override suspend fun getCharacterById(characterId: Id): Flow<DataSourceResult<Character>> =
    feedResourceProvider.queryByKey(characterId, Unit, false)

}