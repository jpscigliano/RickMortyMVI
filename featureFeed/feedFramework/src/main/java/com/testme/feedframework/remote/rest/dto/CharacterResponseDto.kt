package com.testme.feedframework.remote.rest.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class CharacterResponseDto(
  @SerialName("id") val id: Int,
  @SerialName("name") val name: String?,
  @SerialName("status") val status: String?,
  @SerialName("image") val image: String?,
  @SerialName("species") val species: String?,
  @SerialName("type") val type: String?,
  @SerialName("gender") val gender: String?
)
