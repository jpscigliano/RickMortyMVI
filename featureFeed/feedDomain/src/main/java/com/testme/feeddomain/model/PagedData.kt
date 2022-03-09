package com.testme.feeddomain.model

data class PagedData<PaginatedData>(
  val pagination: Pagination,
  val data: PaginatedData
)
