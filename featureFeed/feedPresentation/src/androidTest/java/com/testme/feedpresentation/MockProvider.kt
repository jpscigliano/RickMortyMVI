package com.testme.feedpresentation

import com.testme.feeddomain.model.*

object MockProvider {

  fun mockCharacter() = Character(
    id = Id(1),
    name = Name("TestName"),
    imageUrl = ImageUrl("https://images.ctfassets.net/lzny33ho1g45/T5qqQQVznbZaNyxmHybDT/b76e0ff25a495e00647fa9fa6193a3c2/best-url-shorteners-00-hero.png?w=1520&fm=jpg&q=30&fit=thumb&h=760"),
    status = Status.UNKNOWN,
    gender = Gender.MALE
  )
}