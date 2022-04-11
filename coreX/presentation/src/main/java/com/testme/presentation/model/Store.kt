package com.testme.presentation.model

import com.testme.domain.model.Effect
import com.testme.domain.model.Action
import com.testme.domain.model.ViewEvent
import com.testme.domain.model.ViewState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.CONFLATED
import kotlinx.coroutines.flow.*

class Store<EVENT : ViewEvent, STATE : ViewState, EFFECT : Effect, ACTION : Action>(
  initialState: STATE,
  private val actor: Actor<STATE, EVENT, ACTION, EFFECT>,
  private val reducer: Reducer<STATE, ACTION>
) : IStore<STATE, EVENT> {

  private val mStateFlow: MutableStateFlow<STATE> = MutableStateFlow(initialState)
  val stateFlow: Flow<STATE> = mStateFlow

  private val mEffectFlow: Channel<EFFECT> = Channel(CONFLATED)
  val effectFlow: Flow<EFFECT> = mEffectFlow.receiveAsFlow()

  override val state: STATE
    get() = mStateFlow.value


  override fun accept(event: EVENT) {
    actor.handle(state = state, event = event,
      effects = { effect ->
        mEffectFlow.trySend(effect)

      },
      actions = { action ->
        reducer.reduce(previousState = state, action = action) { state ->
          mStateFlow.tryEmit(state)
        }
      })
  }

  override fun dispose() {
  }
}

interface IStore<STATE : ViewState, EVENT : ViewEvent> {
  val state: STATE
  fun accept(event: EVENT)
  fun dispose()
}