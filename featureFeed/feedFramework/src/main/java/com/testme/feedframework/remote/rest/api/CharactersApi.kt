package com.testme.feedframework.remote.rest.api

import com.testme.feedframework.remote.rest.dto.CharacterResponseDto
import com.testme.feedframework.remote.rest.dto.PagedDataResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CharactersApi {
  @GET("/api/character")
  suspend fun getCharacterList(): Response<PagedDataResponseDto<List<CharacterResponseDto>>>

  @GET("/api/character/{id}")
  suspend fun getCharacterById(@Path("id") id: Int): Response<CharacterResponseDto>
}