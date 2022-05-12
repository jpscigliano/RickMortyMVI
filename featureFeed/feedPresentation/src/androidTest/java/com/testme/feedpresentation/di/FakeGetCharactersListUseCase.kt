package com.testme.feedpresentation.di


import com.testme.domain.DataSourceResult
import com.testme.domain.FlowUseCase
import com.testme.feeddomain.model.Character
import com.testme.feeddomain.repository.FeedRepository
import com.testme.feedpresentation.MockProvider
import dagger.hilt.android.testing.BindValue
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Use Case that returns a flow with the correspond list of characters.
 */
internal class FakeGetCharactersListUseCase @Inject constructor() :
    FlowUseCase<Unit, List<@JvmSuppressWildcards Character>> {

    override suspend fun invoke(request: Unit): Flow<DataSourceResult<List<Character>>> =
        flow {
            emit(DataSourceResult.Success(listOf(MockProvider.mockCharacter(),MockProvider.mockCharacter(),MockProvider.mockCharacter())))
        }
}


