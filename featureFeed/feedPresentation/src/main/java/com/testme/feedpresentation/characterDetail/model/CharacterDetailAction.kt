package com.testme.feedpresentation.characterDetail.model

import com.testme.domain.model.Action
import com.testme.domain.model.ViewEvent
import com.testme.domain.model.ViewState
import com.testme.feeddomain.model.Character
import com.testme.feeddomain.model.Id
import com.testme.feedpresentation.characterList.view.adapter.CharacterUiModel

sealed class CharacterDetailAction() : Action {
  data class LoadingCharacterDetail(val id: Id) : CharacterDetailAction()
  class CharacterDetailLoaded(val data: Character?) : CharacterDetailAction()
  object FinishLoadingCharacterDetail : CharacterDetailAction()
  object NoCharacterSelected : CharacterDetailAction()
}

