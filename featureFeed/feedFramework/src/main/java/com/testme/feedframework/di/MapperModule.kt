package com.testme.feedframework.di

import com.testme.feeddomain.model.Character
import com.testme.feeddomain.model.Gender
import com.testme.feeddomain.model.Status
import com.testme.feedframework.local.room.entity.CharacterEntity
import com.testme.feedframework.local.room.mapper.CharacterEntityToModelMapper
import com.testme.feedframework.local.room.mapper.CharacterModelToEntityMapper
import com.testme.feedframework.remote.rest.dto.CharacterResponseDto
import com.testme.feedframework.remote.rest.mapper.CharacterResponseDtoToCharacterMapper
import com.testme.feedframework.remote.rest.mapper.GenderResponseDtoToGenderMapper
import com.testme.feedframework.remote.rest.mapper.StatusResponseDtoToStatusMapper
import com.testme.framework.mapper.Mapper
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class FrameworkMappersModule {

  @Binds
  abstract fun bindsCharactersResponseDtoToCharactersModelMapper(
    characterResponseDtoToCharacterMapper: CharacterResponseDtoToCharacterMapper
  ): Mapper<CharacterResponseDto, Character>

  @Binds
  abstract fun bindGenderResponseDtoToGenderMapper(genderResponseDtoToGenderMapper: GenderResponseDtoToGenderMapper): Mapper<String?, Gender>

  @Binds
  abstract fun bindStatusResponseDtoToStatusMapper(statusResponseDtoToStatusMapper: StatusResponseDtoToStatusMapper): Mapper<String?, Status>

  @Binds
  abstract fun bindCharacterEntityToCharacterModel(characterEntityToModelMapper: CharacterEntityToModelMapper): Mapper<CharacterEntity, Character>

  @Binds
  abstract fun bindCharacterModelToCharacterEntity(characterModelToEntityMapper: CharacterModelToEntityMapper): Mapper<Character, CharacterEntity>
}
