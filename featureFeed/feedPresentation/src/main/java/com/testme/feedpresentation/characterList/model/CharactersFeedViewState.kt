package com.testme.feedpresentation.characterList.model

import com.testme.domain.model.ViewState
import com.testme.feedpresentation.characterList.view.adapter.CharacterUiModel

data class CharactersFeedViewState(
  val isLoading: Boolean,
  val characters: List<CharacterUiModel>,
  val errorMessage: String
) : ViewState{
  override fun toString(): String {
    return "Loading: $isLoading  - Characters: ${characters.size} - Error: $errorMessage"
  }
}