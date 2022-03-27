package com.testme.presentation.model

import com.testme.domain.model.Effect
import com.testme.domain.model.Action
import com.testme.domain.model.ViewEvent
import com.testme.domain.model.ViewState

interface Actor<STATE : ViewState, EVENT: ViewEvent,ACTION:Action,EFFECT:Effect > {
  fun handle(
    state: STATE,
    event: EVENT,
    effects: ((EFFECT) -> Unit)?,
    actions: ((ACTION) -> Unit)?
  )

  fun dispose()
}
