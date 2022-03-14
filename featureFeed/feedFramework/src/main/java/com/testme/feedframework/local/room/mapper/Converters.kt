package com.testme.feedframework.local.room.mapper

import androidx.room.TypeConverter
import com.testme.feeddomain.model.Gender
import com.testme.feeddomain.model.Status

class Converters {

  @TypeConverter
  fun toGender(value: Int) = enumValues<Gender>()[value]

  @TypeConverter
  fun fromGender(value: Gender) = value.ordinal

  @TypeConverter
  fun toStatus(value: Int) = enumValues<Status>()[value]

  @TypeConverter
  fun fromStatus(value: Status) = value.ordinal
}