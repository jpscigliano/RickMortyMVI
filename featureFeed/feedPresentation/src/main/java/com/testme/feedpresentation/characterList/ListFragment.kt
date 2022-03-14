package com.testme.feedpresentation.characterList


import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.testme.feedpresentation.HelloWorld
import com.testme.feedpresentation.R
import com.testme.feedpresentation.characterList.viewmodel.CharactersListViewModel
import com.testme.presentation.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ListFragment : BaseFragment(R.layout.fragment_characters_list) {

  val vm1: CharactersListViewModel by viewModels()

  @Inject
  lateinit var hy: HelloWorld

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    vm1
  }
}