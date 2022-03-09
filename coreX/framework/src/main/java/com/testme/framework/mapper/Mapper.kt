package com.testme.framework.mapper

interface Mapper<Input, Output> {
  fun map(input: Input?): Output
}