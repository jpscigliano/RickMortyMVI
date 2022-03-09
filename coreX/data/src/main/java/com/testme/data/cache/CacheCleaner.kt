package com.testme.data.cache

interface CacheCleaner {
    suspend fun cleanup()
}