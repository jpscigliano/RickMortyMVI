package com.testme.presentation.utils

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import androidx.annotation.StringRes


fun Context.showPopUp(@StringRes title:  Int,@StringRes text: Int, @StringRes buttonText: Int, onClick: () -> Unit) {
  val builder: AlertDialog.Builder = AlertDialog.Builder(this)
  builder.setTitle(title).setMessage(text).setCancelable(false)
    .setNegativeButton(buttonText) { _, _ ->
      onClick()
    }.setPositiveButton("Cancel") { _, _ ->

    }

  builder.show()
}


fun Context.showPopUp(@StringRes title:  Int, text:String) {
  val builder: AlertDialog.Builder = AlertDialog.Builder(this)
  builder.setTitle(title).setMessage(text).setCancelable(false)
    .setPositiveButton("Cancel") { _, _ ->

    }
  builder.show()
}
