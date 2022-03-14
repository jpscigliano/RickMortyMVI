package com.testme.rickandmoryfeed

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.testme.feedpresentation.HelloWorld
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main) {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

  }
}