package com.testme.feedpresentation.characterList.mapper

import com.testme.domain.mapper.Mapper
import com.testme.feeddomain.model.Character
import com.testme.feedpresentation.characterList.view.adapter.CharacterItemViewType
import com.testme.feedpresentation.characterList.view.adapter.CharacterUiModel
import javax.inject.Inject

class CharacterToCharacterUiModel @Inject constructor() :
  Mapper<Character, CharacterUiModel> {
  override fun map(input: Character?): CharacterUiModel {
    return CharacterUiModel(
      viewType = CharacterItemViewType.CHARACTER,
      id = input?.id?.invoke().toString(),
      name = input?.name?.invoke().toString()
    )
  }
}