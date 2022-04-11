package com.testme.feedpresentation.characterDetail.di

import com.testme.domain.mapper.Mapper
import com.testme.feeddomain.model.Character
import com.testme.feedpresentation.characterList.mapper.CharacterToCharacterUiModel
import com.testme.feedpresentation.characterList.view.adapter.CharacterUiModel
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class MappersModule {

  @Binds
  abstract fun bindCharacterToCharacterItemViewState(mapper: CharacterToCharacterUiModel): Mapper<Character, CharacterUiModel>
}
