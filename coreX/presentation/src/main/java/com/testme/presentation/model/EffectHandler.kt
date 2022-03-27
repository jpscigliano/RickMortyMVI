package com.testme.presentation.model

import com.testme.domain.model.Effect
import com.testme.domain.model.ViewState


interface EffectHandler<in EFFECT : Effect> {
  fun handleEffect(effect: EFFECT)
}