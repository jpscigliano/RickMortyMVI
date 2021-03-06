package com.testme.feeddomain.model

import kotlin.jvm.Throws

@JvmInline
value class Name(private val value: String) {
  init {
    require(value.isNotEmpty())
  }

  operator fun invoke() = value

  companion object {
    @Throws(IllegalArgumentException::class)
    operator fun invoke(value: String?): Name? {
      return try {
        requireNotNull(value) { "Name cannot be null" }
        Name(value)
      } catch (e: IllegalArgumentException) {
        null
      }
     }
  }
}



