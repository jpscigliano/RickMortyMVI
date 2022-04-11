package com.testme.feedpresentation.characterDetail.view

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.testme.feeddomain.model.Id
import com.testme.feedpresentation.R
import com.testme.feedpresentation.characterDetail.model.CharacterDetailAction
import com.testme.feedpresentation.characterDetail.model.CharacterDetailViewEvent
import com.testme.feedpresentation.characterDetail.model.CharacterDetailSideEffect
import com.testme.feedpresentation.characterDetail.model.CharacterDetailViewState
import com.testme.feedpresentation.databinding.FragmentCharacterDetailBinding
import com.testme.presentation.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharacterDetailFragment :
  BaseFragment<CharacterDetailViewEvent, CharacterDetailViewState, CharacterDetailSideEffect, CharacterDetailAction,
    CharacterDetailViewModel, FragmentCharacterDetailBinding>
    (R.layout.fragment_character_detail) {
  override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentCharacterDetailBinding
    get() = FragmentCharacterDetailBinding::inflate

  override val viewModel: CharacterDetailViewModel by viewModels()

  override fun renderState(state: CharacterDetailViewState) {
    binding.loading.isVisible = state.isLoading
    binding.tvName.text = state.name
    binding.tvSelectCharacter.isVisible = state.showSelectionMessage
  }

  override fun handleEffect(effect: CharacterDetailSideEffect) {
    when (effect) {
      is CharacterDetailSideEffect.ShowNoInternetError ->
        Toast.makeText(requireContext(), "NO INTERNET", Toast.LENGTH_LONG).show()

      is CharacterDetailSideEffect.ShowErrorWithOutdatedData ->
        Toast.makeText(requireContext(), "ERROR - DATA IS OUT OF DATE", Toast.LENGTH_LONG).show()

      is CharacterDetailSideEffect.ShowErrorWithoutData ->
        Toast.makeText(requireContext(), "ERROR - DATA NOT AVAILABLE", Toast.LENGTH_LONG).show()

    }
  }

  companion object {
    private const val MY_BOOLEAN = "my_boolean"
    private const val MY_INT = "my_int"

    fun newInstance(id: Id) = CharacterDetailFragment().apply {
      arguments = CharacterDetailFragmentArgs(id()).toBundle()
    }
  }
}