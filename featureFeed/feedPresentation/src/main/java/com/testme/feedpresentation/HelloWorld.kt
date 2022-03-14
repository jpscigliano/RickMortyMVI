package com.testme.feedpresentation

import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.properties.Delegates
import kotlin.random.Random


class HelloWorld @Inject constructor(){
  private var x by Delegates.notNull<Int>()
  fun printIt(): String = "Hello Sir + -> ${x} <-"

  init {
    x = Random.nextInt(0, 100)
  }
}
