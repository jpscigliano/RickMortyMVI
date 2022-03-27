package com.testme.feedpresentation.characterList.model

import com.testme.domain.model.ViewEvent
import com.testme.feeddomain.model.Id

sealed class CharactersFeedViewEvent : ViewEvent {
  object LoadAllCharactersFeed : CharactersFeedViewEvent()
  data class SelectCharacter(val id: Id) : CharactersFeedViewEvent()
}