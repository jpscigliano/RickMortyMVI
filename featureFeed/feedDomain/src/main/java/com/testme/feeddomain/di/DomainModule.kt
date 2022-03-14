package com.testme.feeddomain.di


import com.testme.domain.FlowUseCase
import com.testme.feeddomain.model.Character
import com.testme.feeddomain.model.Id
import com.testme.feeddomain.useCase.GetCharacterUseCase
import com.testme.feeddomain.useCase.GetCharactersListUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * DomainModule will provide bindings for UseCase abstractions.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class DomainModule {


  @Binds
  internal abstract fun bindGetCharacterUseCases(getCharacterUseCase: GetCharacterUseCase): FlowUseCase<Id, Character>

  @Binds
  internal abstract fun bindGetCharactersListUseCases(getCharactersListUseCase: GetCharactersListUseCase): FlowUseCase<Unit,List<Character>>
}