package com.testme.feedframework.di

import com.testme.feeddata.datasource.CharactersDataSource
import com.testme.feeddata.datasource.LocalCharactersDataSource
import com.testme.feedframework.local.RoomCharactersDataSource
import com.testme.feedframework.remote.NetworkCharactersDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {
  @Binds
  @Singleton
  internal abstract fun bindNetworkCharactersDataSource(networkCharactersDataSource: NetworkCharactersDataSource): CharactersDataSource

  @Binds
  @Singleton
  internal abstract fun bindRoomCharacterDataSource(roomCharactersDataSource: RoomCharactersDataSource): LocalCharactersDataSource
}