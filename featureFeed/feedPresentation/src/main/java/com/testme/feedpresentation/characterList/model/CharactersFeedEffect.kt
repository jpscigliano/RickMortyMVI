package com.testme.feedpresentation.characterList.model

import com.testme.domain.AppError
import com.testme.domain.model.Effect
import com.testme.feeddomain.model.Id

sealed class CharactersFeedEffect : Effect {
  class ShowErrorWithoutData (val error:AppError): CharactersFeedEffect()
  class ShowErrorWithOutdatedData(val error:AppError): CharactersFeedEffect()
  object ShowNoInternetError : CharactersFeedEffect()
  data class NavigateToCharacterDetail(val id: Id) : CharactersFeedEffect()

  override fun toString(): String {
    return when(this){
      is NavigateToCharacterDetail -> "Navigate with id:$id()"
      is ShowErrorWithOutdatedData -> "Error with old Data"
      is ShowErrorWithoutData -> "Error no Data"
      is ShowNoInternetError -> "Internet Error"
    }
  }
}
