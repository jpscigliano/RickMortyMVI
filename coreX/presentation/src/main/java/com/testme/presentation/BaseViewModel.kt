package com.testme.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.testme.domain.model.*
import com.testme.presentation.model.Store
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

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
    viewModelScope.launch {
      store.accept(event)
    }
  }


  override fun onCleared() {
    store.dispose()
    super.onCleared()
  }
}