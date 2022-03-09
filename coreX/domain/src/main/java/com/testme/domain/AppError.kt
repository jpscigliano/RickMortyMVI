package com.testme.domain

sealed interface AppError {
  object InternetError : AppError
  object InvalidRequest : AppError
  object NotFound : AppError
  object UnknownError : AppError
}