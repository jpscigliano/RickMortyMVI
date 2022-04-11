package com.testme.feedpresentation.characterList.view.adapter

import com.testme.presentation.adapter.RecyclerAdapterItemViewState
import com.testme.presentation.adapter.ViewType

data class CharacterUiModel(
  override val viewType: ViewType,
  override val id: String,
  val name: String,
) : RecyclerAdapterItemViewState{

}