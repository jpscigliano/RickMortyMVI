package com.testme.feedpresentation.characterDetail.model

import com.testme.domain.model.ViewState
import com.testme.feedpresentation.characterList.view.adapter.CharacterUiModel

data class CharacterDetailViewState(
  val isLoading: Boolean=false,
  val showSelectionMessage:Boolean=false,
  val name: String="",
  val pictureUrl: String=""
) : ViewState

