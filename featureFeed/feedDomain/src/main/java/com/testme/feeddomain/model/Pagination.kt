package com.testme.feeddomain.model

data class Pagination(
  val count: Int,
  val pages: Int,
  val next: String?,
  val prev: String?
)

