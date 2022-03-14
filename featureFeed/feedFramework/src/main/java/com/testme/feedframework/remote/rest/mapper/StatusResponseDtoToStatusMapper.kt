package com.testme.feedframework.remote.rest.mapper


import com.testme.feeddomain.model.Status
import com.testme.framework.mapper.Mapper
import javax.inject.Inject

class StatusResponseDtoToStatusMapper @Inject constructor() : Mapper<String?, Status> {
  override fun map(input: String?): Status = when (input?.lowercase()) {
    "alive" -> Status.ALIVE
    "dead" -> Status.DEAD
    else -> Status.UNKNOWN
  }
}