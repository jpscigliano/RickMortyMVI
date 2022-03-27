package com.testme.feedframework.local.room.mapper

import com.testme.feeddomain.model.*
import com.testme.feedframework.local.room.entity.CharacterEntity
import com.testme.domain.mapper.Mapper
import javax.inject.Inject

class CharacterEntityToModelMapper @Inject constructor() : Mapper<CharacterEntity, Character> {
  override fun map(input: CharacterEntity?): Character =
    Character(
      id = Id(input?.id ?: -1),
      name = Name(input?.name),
      imageUrl = ImageUrl(input?.image),
      status = input?.status ?: Status.UNKNOWN,
      gender = input?.gender ?: Gender.UNKNOW
    )
}