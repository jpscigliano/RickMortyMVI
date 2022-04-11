package com.testme.feeddomain.useCase


import com.testme.domain.DataSourceResult
import com.testme.feeddomain.model.*
import com.testme.feeddomain.repository.FeedRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class GetCharacterUseCaseTest {

  private val feedRepository = mockk<FeedRepository>()
  private lateinit var getCharacterUseCase: GetCharacterUseCase


  @BeforeAll
  fun setUp() {
    coEvery { feedRepository.getCharacters() } returns flow {
      DataSourceResult.InProgress<Character>();DataSourceResult.Success(MockProvider.mockCharacter())
    }
    getCharacterUseCase = GetCharacterUseCase(feedRepository)
  }

  @Test
  fun `GIVEN any characterId WHEN getCharacterUseCase is called  THEN  a flow {InProgress,Success} should be emitted `() =
    runTest {
      val result = getCharacterUseCase(Id(1)).toList()
      val resultList=result.toList()
      assertEquals(DataSourceResult.InProgress(), resultList[0])
      assertEquals(DataSourceResult.Success(MockProvider.mockCharacter()), resultList[1])

    }
}