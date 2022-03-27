package com.testme.feedpresentation.characterList.model

import com.testme.domain.model.Effect
import com.testme.feeddomain.model.Id

sealed class CharactersFeedEffect : Effect {
  object ShowNoDataError: CharactersFeedEffect()
  object ShowDataOutOfDateError: CharactersFeedEffect()
  object ShowNoInternetError : CharactersFeedEffect()
  data class NavigateToCharacterDetail(val id: Id) : CharactersFeedEffect()

  override fun toString(): String {
    return when(this){
      is NavigateToCharacterDetail -> "Navigate with id:$id()"
      is ShowDataOutOfDateError -> "Error with old Data"
      is ShowNoDataError -> "Error no Data"
      is ShowNoInternetError -> "Internet Error"
    }
  }
}
