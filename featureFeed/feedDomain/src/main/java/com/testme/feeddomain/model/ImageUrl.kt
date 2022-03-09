package com.testme.feeddomain.model

import java.net.URL
import kotlin.jvm.Throws

@JvmInline
value class ImageUrl(private val value: String) {
  init {
    require(runCatching { URL(value) }.isSuccess)
  }

  operator fun invoke() = value

  companion object {
    @Throws(IllegalArgumentException::class)
    operator fun invoke(value: String?): ImageUrl {
      requireNotNull(value) { "Url cannot be null" }
      return ImageUrl(value)
    }
  }
}


