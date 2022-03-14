package com.testme.feeddomain.useCase

import com.testme.domain.DataSourceResult
import com.testme.domain.FlowUseCase
import com.testme.feeddomain.model.Character
import com.testme.feeddomain.model.Id
import com.testme.feeddomain.repository.FeedRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class GetCharacterUseCase @Inject constructor(
  private val feedRepository: FeedRepository
) : FlowUseCase<Id, Character> {
  override suspend fun invoke(request: Id): Flow<DataSourceResult<Character>> =
    feedRepository.getCharacterById(request)
}