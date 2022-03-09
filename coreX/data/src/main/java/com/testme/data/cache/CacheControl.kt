package com.testme.data.cache

import java.util.*

interface CacheControl {
  var refreshRate: Long
  val lastUpdate: Date?

  suspend fun dispose(cleanup: Boolean = false)
  fun createChild(): CacheControl
  fun addCacheCleaner(listener: CacheCleaner)
  fun refresh()
  fun isExpired():Boolean

}