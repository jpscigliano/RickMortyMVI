package com.testme.feedframework.remote

import com.testme.domain.DataSourceResult
import com.testme.feeddata.datasource.CharactersDataSource
import com.testme.feeddomain.model.Character
import com.testme.feeddomain.model.Id
import com.testme.feedframework.remote.rest.api.CharactersApi
import com.testme.feedframework.remote.rest.dto.CharacterResponseDto
import com.testme.feedframework.remote.rest.dto.PagedDataResponseDto
import com.testme.feedframework.remote.rest.mapper.PagedResponseDtoToPagedDataMapper
import com.testme.framework.datasource.RemoteDataSource
import com.testme.domain.mapper.Mapper
import com.testme.domain.mapper.toList
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class NetworkCharactersDataSource @Inject constructor(
  private val charactersApi: CharactersApi,
  private val characterResponseDtoToCharacterModelMapper: Mapper<CharacterResponseDto, Character>
) : CharactersDataSource {
  override suspend fun getCharactersList(): DataSourceResult<List<Character>> =
    RemoteDataSource.getRemoteResult<Unit, PagedDataResponseDto<List<CharacterResponseDto>>, List<Character>>(
      call = { charactersApi.getCharacterList() },
      mapResponseDtoToModel = { response ->
        PagedResponseDtoToPagedDataMapper(
          dataResponseMapper = characterResponseDtoToCharacterModelMapper.toList()
        ).map(response).data
      }
    )


  override suspend fun getCharacter(id: Id): DataSourceResult<Character> =
    RemoteDataSource.getRemoteResult<Unit, CharacterResponseDto, Character>(
      call = { charactersApi.getCharacterById(id()) },
      mapResponseDtoToModel = { response ->
        characterResponseDtoToCharacterModelMapper.map(response)
      }
    )
}