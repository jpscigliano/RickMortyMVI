package com.testme.presentation.model

import com.testme.domain.model.Action
import com.testme.domain.model.ViewState

interface Reducer<STATE : ViewState, ACTION : Action> {
  fun reduce(previousState: STATE, action: ACTION,states:(STATE)->Unit)
}
