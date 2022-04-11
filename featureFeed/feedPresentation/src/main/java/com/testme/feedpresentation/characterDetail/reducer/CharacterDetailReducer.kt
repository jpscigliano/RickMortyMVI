package com.testme.feedpresentation.characterDetail.reducer

import com.testme.feedpresentation.characterDetail.model.CharacterDetailAction
import com.testme.feedpresentation.characterDetail.model.CharacterDetailViewState
import com.testme.presentation.model.Reducer

class CharacterDetailReducer : Reducer<CharacterDetailViewState, CharacterDetailAction> {
  override fun reduce(
    previousState: CharacterDetailViewState,
    action: CharacterDetailAction,
    states: (CharacterDetailViewState) -> Unit
  ) {
    when (action) {
      is CharacterDetailAction.CharacterDetailLoaded -> {
        states(
          previousState.copy(
            isLoading = false,
            showSelectionMessage = false,
            name = action.data?.name?.invoke() ?: "",
            pictureUrl = action.data?.imageUrl?.invoke() ?: "",
          )
        )
      }
      is CharacterDetailAction.FinishLoadingCharacterDetail -> states(previousState.copy(isLoading = false))
      is CharacterDetailAction.LoadingCharacterDetail -> states(previousState.copy(isLoading = true))
      is CharacterDetailAction.NoCharacterSelected -> states(
        previousState.copy(
          isLoading = false,
          showSelectionMessage = true
        )
      )
    }
  }
}