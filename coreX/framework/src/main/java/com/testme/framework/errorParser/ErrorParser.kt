package com.testme.framework.errorParser

import com.testme.domain.AppError


object ErrorParser {
  fun parseError(responseErrorCode: Int): AppError {
    return when (responseErrorCode) {
      // invalid request
      400 -> AppError.InvalidRequest

      // not found
      404 -> AppError.NotFound

      // etc..

      else -> AppError.UnknownError
    }
  }
}
