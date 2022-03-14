package com.testme.feedframework.local.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.testme.feedframework.local.room.dao.CharactersDao
import com.testme.feedframework.local.room.entity.CharacterEntity
import com.testme.feedframework.local.room.mapper.Converters

internal const val DB_NAME = "CHARACTERS_DATABASE"

@Database(entities = [CharacterEntity::class], version = 1)
@TypeConverters(Converters::class)
internal abstract class CharacterDatabase : RoomDatabase() {
  abstract fun characterDao(): CharactersDao
}

