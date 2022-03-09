package com.testme.feeddomain.useCase

import com.testme.domain.DataSourceResult
import com.testme.feeddomain.model.Character
import com.testme.feeddomain.model.Id
import com.testme.feeddomain.repository.FeedRepository
import kotlinx.coroutines.flow.Flow

class GetCharacterUseCase(
  private val feedRepository: FeedRepository
) {
  suspend operator fun invoke(characterId: Id): Flow<DataSourceResult<Character>> =
    feedRepository.getCharacterById(characterId)
}