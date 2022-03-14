package com.testme.feedframework.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.testme.feedframework.local.room.dao.CharactersDao
import com.testme.feedframework.local.room.database.CharacterDatabase
import com.testme.feedframework.local.room.database.DB_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

  @Provides
  @Singleton
  internal fun provideCharacterDataBase(@ApplicationContext context: Context):CharacterDatabase =
    Room.databaseBuilder(
      context,
      CharacterDatabase::class.java,
      DB_NAME
    ).build()

  @Provides
  internal fun provideChannelDao(database: CharacterDatabase): CharactersDao {
    return database.characterDao()
  }
}