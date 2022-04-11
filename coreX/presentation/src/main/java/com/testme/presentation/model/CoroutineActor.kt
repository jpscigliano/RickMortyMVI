package com.testme.presentation.model

import com.testme.domain.model.Effect
import com.testme.domain.model.Action
import com.testme.domain.model.ViewEvent
import com.testme.domain.model.ViewState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

abstract class CoroutineActor<STATE : ViewState, EVENT : ViewEvent, ACTION : Action, EFFECT : Effect>(
  private val context: CoroutineContext
) :
  Actor<STATE, EVENT, ACTION, EFFECT> {

  /**
   * A [CoroutineScope] that can be used by the [CharactersActor] to launch coroutines.
   * The [CoroutineScope] is automatically cancelled on dispose.
   */
  private val scope: CoroutineScope = CoroutineScope(context)

  private var job: Job? = null

  override fun dispose() {
    job?.cancel()
  }

  fun launch(newJob: suspend () -> Unit) {
    job?.cancel()
    job = scope.launch {
      newJob()

    }
  }
}
