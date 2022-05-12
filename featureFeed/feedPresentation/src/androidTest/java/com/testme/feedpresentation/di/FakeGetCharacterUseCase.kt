package com.testme.feedpresentation.di

import com.testme.domain.DataSourceResult
import com.testme.domain.FlowUseCase
import com.testme.feeddomain.model.Character
import com.testme.feeddomain.model.Id
import com.testme.feeddomain.repository.FeedRepository
import com.testme.feedpresentation.MockProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

internal class FakeGetCharacterUseCase @Inject constructor() : FlowUseCase<Id, Character> {

  override suspend fun invoke(request: Id): Flow<DataSourceResult<Character>> =
    flow{
     emit(DataSourceResult.Success( MockProvider.mockCharacter()))
    }

}