package com.testme.feedpresentation.characterDetail.view

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.testme.domain.FlowUseCase
import com.testme.feeddomain.model.Character
import com.testme.feeddomain.model.Id
import com.testme.feedpresentation.characterDetail.model.CharacterDetailAction
import com.testme.feedpresentation.characterDetail.model.CharacterDetailViewEvent
import com.testme.feedpresentation.characterDetail.model.CharacterDetailSideEffect
import com.testme.feedpresentation.characterDetail.model.CharacterDetailViewState
import com.testme.feedpresentation.characterDetail.reducer.CharacterDetailActor
import com.testme.feedpresentation.characterDetail.reducer.CharacterDetailReducer
import com.testme.presentation.model.Store
import com.testme.presentation.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CharacterDetailViewModel @Inject constructor(
  private val savedStateHandle: SavedStateHandle,
  private val loadCharacterDetailUseCase: FlowUseCase<Id, Character>
) :
  BaseViewModel<CharacterDetailViewEvent, CharacterDetailViewState, CharacterDetailSideEffect, CharacterDetailAction>() {
  override val store = Store(
    initialState = CharacterDetailViewState(),
    actor = CharacterDetailActor(viewModelScope.coroutineContext, loadCharacterDetailUseCase),
    reducer = CharacterDetailReducer()
  )

  private val characterId: Id =
    CharacterDetailFragmentArgs.fromSavedStateHandle(savedStateHandle).characterIdNavArg.let {
      Id(it)
    }

  init {
    store.accept(CharacterDetailViewEvent.LoadCharacterDetailView(characterId))
  }
}