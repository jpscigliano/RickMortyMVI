package com.testme.domain.mapper

interface Mapper<Input, Output> {
  fun map(input: Input?): Output
}