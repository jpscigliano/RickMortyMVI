package com.testme.feedframework.remote.rest.mapper

import com.testme.feeddomain.model.*
import com.testme.feedframework.remote.rest.dto.CharacterResponseDto
import com.testme.domain.mapper.Mapper
import javax.inject.Inject

class CharacterResponseDtoToCharacterMapper @Inject constructor(
  private val statusMapper: Mapper<String?, Status>,
  private val genderMapper: Mapper<String?, Gender>
) : Mapper<CharacterResponseDto, Character> {

  override fun map(input: CharacterResponseDto?): Character = Character(
    id = Id(input?.id ?: -1),
    name = Name(input?.name),
    imageUrl = ImageUrl(input?.image),
    status = statusMapper.map(input?.status),
    gender = genderMapper.map(input?.gender)
  )
}
