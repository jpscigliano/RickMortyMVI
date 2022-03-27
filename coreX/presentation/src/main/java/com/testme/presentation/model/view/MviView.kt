package com.testme.presentation.model.view

import com.testme.domain.model.Effect
import com.testme.domain.model.ViewIntent
import com.testme.domain.model.ViewState
import kotlinx.coroutines.flow.Flow

interface MviView<in STATE: ViewState,in EFFECT: Effect> {
  fun renderState(state:STATE)
  fun handleEffect(effect:EFFECT)
}