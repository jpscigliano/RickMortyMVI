package com.testme.presentation.model

import com.testme.domain.model.ViewIntent

interface ViewIntentHandler<INTENT : ViewIntent> {
  fun handle(intent: INTENT)
}