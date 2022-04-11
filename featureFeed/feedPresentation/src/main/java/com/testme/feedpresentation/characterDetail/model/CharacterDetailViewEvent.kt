package com.testme.feedpresentation.characterDetail.model

import com.testme.domain.model.ViewEvent
import com.testme.feeddomain.model.Id


sealed class CharacterDetailViewEvent : ViewEvent {
  data class LoadCharacterDetailView(val id: Id? = null) : CharacterDetailViewEvent()
}

