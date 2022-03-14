package com.testme.feeddomain.useCase


import com.testme.domain.DataSourceResult
import com.testme.domain.FlowUseCase
import com.testme.feeddomain.model.Character
import com.testme.feeddomain.repository.FeedRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Use Case that returns a flow with the corresponsd list of characters.
 */
internal class GetCharactersListUseCase @Inject constructor(
  private val feedRepository: FeedRepository
) : FlowUseCase<Unit, List<@JvmSuppressWildcards Character>> {

  override suspend fun invoke(request: Unit): Flow<DataSourceResult<List<Character>>> =
    feedRepository.getCharacters()
}