package com.testme.feedframework.remote.rest.mapper


import com.testme.feeddomain.model.Gender
import com.testme.framework.mapper.Mapper

class GenderResponseDtoToGenderMapper() : Mapper<String?, Gender> {
  override fun map(input: String?): Gender = when (input?.lowercase()) {
    "male" -> Gender.MALE
    "female" -> Gender.FEMALE
    "genderless" -> Gender.GENDERLESS
    else -> Gender.UNKNOW
  }
}