package com.testme.feedpresentation.characterList.reducer

import com.testme.domain.mapper.Mapper
import com.testme.domain.mapper.toList
import com.testme.feeddomain.model.Character
import com.testme.feedpresentation.characterList.model.CharactersFeedAction
import com.testme.feedpresentation.characterList.model.CharactersFeedViewState
import com.testme.feedpresentation.characterList.view.adapter.CharacterUiModel
import com.testme.presentation.model.Reducer
import javax.inject.Inject

class CharactersReducer @Inject constructor(private val mapper: Mapper<Character, CharacterUiModel>) :
  Reducer<CharactersFeedViewState, CharactersFeedAction> {

  override fun reduce(
    previousStateFeed: CharactersFeedViewState,
    feedAction: CharactersFeedAction,
    states: (CharactersFeedViewState) -> Unit
  ) {
    when (feedAction) {
      is CharactersFeedAction.SelectingCharacter -> states(previousStateFeed.copy(isLoading = false))
      is CharactersFeedAction.LoadingCharactersFeed -> states(previousStateFeed.copy(isLoading = true))
      is CharactersFeedAction.FinishLoadingCharactersFeed -> states(previousStateFeed.copy(isLoading = false))
      is CharactersFeedAction.CharactersFeedLoaded -> states(
        previousStateFeed.copy(
          isLoading = false,
          characters = mapper.toList().map(feedAction.data ?: listOf()),
        )
      )
    }
  }
}





