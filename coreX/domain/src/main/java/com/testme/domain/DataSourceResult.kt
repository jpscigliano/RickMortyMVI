package com.testme.domain

sealed interface DataSourceResult<out DATA> {
  class InProgress<out DATA> : DataSourceResult<DATA>
  class NoInternet<out DATA> : DataSourceResult<DATA>
  data class Error<out DATA>(val error: AppError? = null) : DataSourceResult<DATA>
  data class Success<out DATA>(val data: DATA) : DataSourceResult<DATA>
}

