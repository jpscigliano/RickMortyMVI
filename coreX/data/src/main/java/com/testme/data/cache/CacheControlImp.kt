package com.testme.data.cache

import java.util.*
import java.util.concurrent.TimeUnit

class CacheControlImp(
  rate: Long = DEFAULT_REFRESH_RATE_MS,
  private var lastUpdateDate: Date? = null
) : CacheControl {
  companion object {
    val DEFAULT_REFRESH_RATE_MS = TimeUnit.MINUTES.toMillis(0)
  }


  private val listeners: MutableList<CacheCleaner> = mutableListOf()
  private val children: MutableList<CacheControl> = mutableListOf()


  override var refreshRate: Long = rate
  override val lastUpdate: Date?
    get() = lastUpdateDate

  //Public API
  override suspend fun dispose(cleanup: Boolean) {
    lastUpdateDate = null
    children.forEach { it.dispose(cleanup) }
    if (cleanup) {
      listeners.forEach { it.cleanup() }
    }
  }

  override fun createChild(): CacheControl =
    CacheControlImp(refreshRate, lastUpdateDate).also { children.add(it) }

  override fun addCacheCleaner(listener: CacheCleaner) {
    listeners.add(listener)
  }

  override fun refresh() {
    lastUpdateDate = Date()
  }

  override fun isExpired(): Boolean =
    lastUpdateDate?.let { (Date().time - it.time) > refreshRate } ?: true
}