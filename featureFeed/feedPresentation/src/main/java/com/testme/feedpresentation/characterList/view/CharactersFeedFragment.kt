package com.testme.feedpresentation.characterList.view


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.testme.feeddomain.model.Id

import com.testme.feedpresentation.R
import com.testme.feedpresentation.characterList.view.adapter.CharactersListViewHolderFactory

import com.testme.feedpresentation.characterList.view.viewmodel.CharactersViewModel
import com.testme.feedpresentation.characterList.model.CharactersFeedAction
import com.testme.feedpresentation.characterList.model.CharactersFeedEffect
import com.testme.feedpresentation.characterList.model.CharactersFeedViewEvent
import com.testme.feedpresentation.characterList.model.CharactersFeedViewState
import com.testme.feedpresentation.databinding.FragmentCharactersListBinding
import com.testme.presentation.BaseFragment
import com.testme.presentation.adapter.RecyclerBaseAdapter

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharactersFeedFragment :
  BaseFragment<CharactersFeedViewEvent, CharactersFeedViewState, CharactersFeedEffect, CharactersFeedAction,
    CharactersViewModel,
    FragmentCharactersListBinding>(
    layoutId = R.layout.fragment_characters_list
  ) {
  override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentCharactersListBinding
    get() = FragmentCharactersListBinding::inflate

  override val viewModel: CharactersViewModel by viewModels()

  private val adapter = RecyclerBaseAdapter(
    CharactersListViewHolderFactory { id ->
      viewModel.dispatchEvent(CharactersFeedViewEvent.SelectCharacter(Id(id.toInt())))
    }
  )

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    binding.rvCharacters.adapter = adapter
    binding.rvCharacters.layoutManager = LinearLayoutManager(requireContext())

    binding.button.setOnClickListener {
      viewModel.dispatchEvent(CharactersFeedViewEvent.LoadAllCharactersFeed)
    }
  }

  override fun onDestroyView() {
    binding.rvCharacters.adapter = null
    super.onDestroyView()
  }

  override fun renderState(stateFeed: CharactersFeedViewState) {
    println("New State ${stateFeed.toString()}")
    binding.loading.isVisible = stateFeed.isLoading
    binding.error.text = stateFeed.errorMessage
    adapter.submitList(stateFeed.characters)
  }

  override fun handleEffect(feedEffect: CharactersFeedEffect) {
    println("New Effect ${feedEffect.toString()}")
    when (feedEffect) {
      is CharactersFeedEffect.NavigateToCharacterDetail ->
        navigate(feedEffect.id)

      is CharactersFeedEffect.ShowNoInternetError ->
        Toast.makeText(requireContext(), "NO INTERNET", Toast.LENGTH_LONG).show()

      is CharactersFeedEffect.ShowDataOutOfDateError ->
        Toast.makeText(requireContext(), "ERROR - DATA IS OUT OF DATE", Toast.LENGTH_LONG).show()

      is CharactersFeedEffect.ShowNoDataError ->
        Toast.makeText(requireContext(), "ERROR - DATA NOT AVAILABLE", Toast.LENGTH_LONG).show()
    }
  }

  private fun navigate(id: Id) {
    println("Navigate to ${id()}")
  }
}
