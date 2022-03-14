package com.testme.data.di

import com.testme.data.cache.CacheControl
import com.testme.data.cache.CacheControlImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoreXDataModule {

  @Provides
  @Singleton
  fun provideCacheControl(): CacheControl = CacheControlImp()
}