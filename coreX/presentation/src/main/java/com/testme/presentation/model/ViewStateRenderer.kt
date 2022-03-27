package com.testme.presentation.model

import com.testme.domain.model.ViewState


interface ViewStateRenderer<in STATE: ViewState> {
    fun renderState(state: STATE)
}