package com.testme.feedpresentation.characterList.model

import com.testme.domain.model.Action
import com.testme.feeddomain.model.Character
import com.testme.feeddomain.model.Id

sealed class CharactersFeedAction : Action {
  data class SelectingCharacter(val characterID: Id) : CharactersFeedAction()
  object LoadingCharactersFeed : CharactersFeedAction()
  data class CharactersFeedLoaded(val data: List<Character>?) : CharactersFeedAction()
  object FinishLoadingCharactersFeed : CharactersFeedAction()
}