package com.testme.feedpresentation.characterList.view.viewmodel

import com.testme.domain.FlowUseCase
import com.testme.feeddomain.model.Character
import com.testme.feedpresentation.characterList.model.CharactersFeedAction
import com.testme.feedpresentation.characterList.model.CharactersFeedEffect
import com.testme.feedpresentation.characterList.model.CharactersFeedViewEvent
import com.testme.feedpresentation.characterList.reducer.CharactersActor
import com.testme.feedpresentation.characterList.reducer.CharactersReducer
import com.testme.feedpresentation.characterList.model.CharactersFeedViewState

import com.testme.feedpresentation.characterList.reducer.Store
import com.testme.presentation.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(
  private val getCharactersUseCase: FlowUseCase<Unit, List<Character>>,
  private val charactersReducer: CharactersReducer,
  ) :
  BaseViewModel<CharactersFeedViewEvent, CharactersFeedViewState, CharactersFeedEffect, CharactersFeedAction>() {

  override val store = Store(
    initialState = CharactersFeedViewState(isLoading = false, characters = listOf(), ""),
    actor = CharactersActor(getCharactersUseCase),
    reducer = charactersReducer
  )

  init {
    dispatchEvent(CharactersFeedViewEvent.LoadAllCharactersFeed)
  }
}