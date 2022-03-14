package com.testme.feedframework.local.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.testme.feedframework.local.room.entity.CharacterEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CharactersDao {
  @Query("SELECT * FROM characterEntity")
  fun getAll(): Flow<List<CharacterEntity>>

  @Query("SELECT * FROM characterEntity WHERE id IN (:id)")
  fun get(id: Int): Flow<CharacterEntity>

  @Insert
  fun insert(characters: List<CharacterEntity>)

  @Insert
  fun insert(characters: CharacterEntity)

  @Delete
  fun delete(character: CharacterEntity)

  @Query("DELETE  FROM characterEntity")
  fun deleteAll()
}