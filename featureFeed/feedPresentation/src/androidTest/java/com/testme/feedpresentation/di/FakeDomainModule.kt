package com.testme.feedpresentation.di

import com.testme.domain.FlowUseCase
import com.testme.feeddomain.di.DomainModule
import com.testme.feeddomain.model.Id
import com.testme.feeddomain.model.Character

import dagger.Binds
import dagger.Module

import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DomainModule::class]
)
abstract class FakeDomainModule {

    @Binds
    internal abstract fun bindGetCharacterUseCases(getCharacterUseCase: FakeGetCharacterUseCase): FlowUseCase<Id, Character>

    @Binds
    internal abstract fun bindGetCharactersListUseCases(getCharactersListUseCase: FakeGetCharactersListUseCase): FlowUseCase<Unit, List<Character>>
}