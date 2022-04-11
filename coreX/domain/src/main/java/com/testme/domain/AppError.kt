package com.testme.domain

sealed class AppError(val message:String) {
  object InvalidRequest : AppError("Invalid")
  object NotFound : AppError("Not Found")
  object UnknownError : AppError("Unknown Error")
}