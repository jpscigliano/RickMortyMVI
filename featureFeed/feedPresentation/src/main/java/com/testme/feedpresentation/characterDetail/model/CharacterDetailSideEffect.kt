package com.testme.feedpresentation.characterDetail.model

import com.testme.domain.model.Effect
import com.testme.feeddomain.model.Id
import com.testme.feedpresentation.characterList.model.CharactersFeedEffect

sealed class CharacterDetailSideEffect() : Effect {
  object ShowErrorWithoutData : CharacterDetailSideEffect()
  object ShowErrorWithOutdatedData : CharacterDetailSideEffect()
  object ShowNoInternetError : CharacterDetailSideEffect()
}