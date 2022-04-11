package com.testme.feedpresentation.characterList.view


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.testme.feeddomain.model.Id
import com.testme.feedpresentation.R
import com.testme.feedpresentation.characterDetail.view.CharacterDetailFragment
import com.testme.feedpresentation.characterDetail.view.CharacterDetailFragmentArgs
import com.testme.feedpresentation.characterList.model.CharactersFeedAction
import com.testme.feedpresentation.characterList.model.CharactersFeedEffect
import com.testme.feedpresentation.characterList.model.CharactersFeedViewEvent
import com.testme.feedpresentation.characterList.model.CharactersFeedViewState
import com.testme.feedpresentation.characterList.view.adapter.CharactersListViewHolderFactory
import com.testme.feedpresentation.databinding.FragmentCharactersListBinding
import com.testme.presentation.BaseFragment
import com.testme.presentation.adapter.RecyclerBaseAdapter
import com.testme.presentation.utils.TwoPaneOnBackPressedCallback
import com.testme.presentation.utils.showPopUp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharactersFeedFragment :
  BaseFragment<CharactersFeedViewEvent, CharactersFeedViewState, CharactersFeedEffect, CharactersFeedAction,
    CharactersViewModel, FragmentCharactersListBinding>(
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
    // Connect the SlidingPaneLayout to the system back button.
    requireActivity().onBackPressedDispatcher.addCallback(
      viewLifecycleOwner,
      TwoPaneOnBackPressedCallback(binding.slidingPaneLayout)
    )
  }

  override fun onDestroyView() {
    binding.rvCharacters.adapter = null
    super.onDestroyView()
  }

  override fun renderState(state: CharactersFeedViewState) {
    binding.loading.isVisible = state.isLoading
    binding.error.text = state.errorMessage
    adapter.submitList(state.characters)
  }

  override fun handleEffect(effect: CharactersFeedEffect) {
    when (effect) {
      is CharactersFeedEffect.NavigateToCharacterDetail ->
        navigate(effect.id)

      is CharactersFeedEffect.ShowNoInternetError -> requireContext().showPopUp(
        R.string.error_no_internet,
        R.string.error_data_not_acurrate,
        R.string.retry
      ) {
        viewModel.dispatchEvent(CharactersFeedViewEvent.LoadAllCharactersFeed)
      }

      is CharactersFeedEffect.ShowErrorWithOutdatedData ->
        requireContext().showPopUp(
          R.string.error,
          getString(R.string.error_data_not_acurrate) + effect.error.message
        )

      is CharactersFeedEffect.ShowErrorWithoutData ->
        requireContext().showPopUp(
          R.string.error,
          getString(R.string.error_no_data) + effect.error.message
        )

    }
  }

  private fun navigate(id: Id) {
    setupDetails(id)
    binding.slidingPaneLayout.open()
  }


  /**
   * Navigation with Navigation Component.
   */
  private fun setupDetails(itemId: Id) {
    val navHostFragment =
      childFragmentManager.findFragmentById(R.id.detail_container) as NavHostFragment
    val navController = navHostFragment.navController
    navController.navigate(
      R.id.fragmentCharacterDetail,
      CharacterDetailFragmentArgs(itemId()).toBundle(),
      NavOptions.Builder()
        .setPopUpTo(navController.graph.startDestinationId, true)
        .build()
    )
  }

  /**
   * Another option of navigation Navigation
   */
  private fun setupDetails_2(itemId: Id) {
    childFragmentManager.commit {
      replace(R.id.detail_container, CharacterDetailFragment.newInstance(itemId))
    }
  }
}
