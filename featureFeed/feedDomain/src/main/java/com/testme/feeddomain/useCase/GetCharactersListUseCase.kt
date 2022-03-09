package com.testme.feeddomain.useCase

import com.testme.domain.DataSourceResult
import com.testme.feeddomain.model.Character
import com.testme.feeddomain.repository.FeedRepository
import kotlinx.coroutines.flow.Flow

class GetCharactersListUseCase(
  private val feedRepository: FeedRepository
) {
  suspend operator fun invoke(): Flow<DataSourceResult<List<Character>>> =
    feedRepository.getCharacters()
}