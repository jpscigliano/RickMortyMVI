package com.testme.presentation

import androidx.lifecycle.ViewModel
import com.testme.domain.model.*
import com.testme.feedpresentation.characterList.reducer.Store
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow

abstract class BaseViewModel<EVENT : ViewEvent, STATE : ViewState, EFFECT : Effect, ACTION : Action> :
  ViewModel() {

  /**
   * VIEW STATE
   */
  internal val viewState: Flow<STATE>
    get() {
      return store.stateFlow
    }

  /**
   * EFFECTS
   */
  val effect: Flow<EFFECT>
    get() {
      return store.effectFlow
    }

  protected abstract val store: Store<EVENT, STATE, EFFECT, ACTION>

  fun dispatchEvent(event: EVENT) {
    store.accept(event)
  }


  override fun onCleared() {
    store.dispose()
    super.onCleared()
  }
}