package com.testme.feedframework.local.room.mapper

import com.testme.feeddomain.model.*
import com.testme.feedframework.local.room.entity.CharacterEntity
import com.testme.framework.mapper.Mapper
import javax.inject.Inject


class CharacterModelToEntityMapper @Inject constructor() : Mapper<Character,CharacterEntity> {
  override fun map(input: Character?): CharacterEntity =
    CharacterEntity(
      id = input?.id?.invoke()!!,
      name = input.name?.invoke(),
      image = input.imageUrl?.invoke(),
      status = input.status,
      gender = input.gender
    )
}