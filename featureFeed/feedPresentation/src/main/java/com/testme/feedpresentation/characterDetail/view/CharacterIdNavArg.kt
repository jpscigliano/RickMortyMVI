package com.testme.feedpresentation.characterDetail.view

import android.os.Parcelable
import com.testme.feeddomain.model.Id
import kotlinx.parcelize.Parcelize


@Parcelize
class CharacterIdNavArg(val id: Int) : Parcelable {
  companion object {
    val default = CharacterIdNavArg(0)
  }
}