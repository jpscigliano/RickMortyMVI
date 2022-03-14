package com.testme.feedframework.local.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.testme.feeddomain.model.Gender
import com.testme.feeddomain.model.Status

@Entity
class CharacterEntity(
  @PrimaryKey val id: Int,
  @ColumnInfo(name = "name") val name: String?,
  @ColumnInfo(name = "status") val status: Status?,
  @ColumnInfo(name = "image") val image: String?,
  @ColumnInfo(name = "gender") val gender: Gender?
)