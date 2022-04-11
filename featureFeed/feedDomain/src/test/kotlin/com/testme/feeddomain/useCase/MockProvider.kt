package com.testme.feeddomain.useCase

import com.testme.feeddomain.model.*

object MockProvider {

  fun mockCharacter() = Character(
    id = Id(1),
    name = Name("TestName"),
    imageUrl = ImageUrl("url"),
    status = Status.UNKNOWN,
    gender = Gender.MALE
  )
}