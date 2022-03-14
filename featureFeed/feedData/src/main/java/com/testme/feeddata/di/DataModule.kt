package com.testme.feeddata.di


import com.testme.feeddata.repository.CharactersRepositoryImpl
import com.testme.feeddomain.repository.FeedRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * DotaModule will provide bindings for Repository abstractions.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

  @Binds
  @Singleton
  internal abstract fun bindCharactersRepository(charactersRepositoryImpl: CharactersRepositoryImpl): FeedRepository

}