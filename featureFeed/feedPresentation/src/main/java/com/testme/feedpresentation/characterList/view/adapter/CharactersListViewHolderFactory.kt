package com.testme.feedpresentation.characterList.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.testme.feedpresentation.databinding.ViewItemCharacterListBinding
import com.testme.presentation.adapter.BaseViewHolder
import com.testme.presentation.adapter.BaseViewHolderFactory
import com.testme.presentation.adapter.ViewType


class CharactersListViewHolderFactory(private val onCharacterClick: (id: String) -> Unit) :
  BaseViewHolderFactory<CharacterUiModel, ViewItemCharacterListBinding> {
  override val viewType: ViewType
    get() = CharacterItemViewType.CHARACTER

  override fun createViewHolder(
    parent: ViewGroup,
    viewType: Int
  ): BaseViewHolder<CharacterUiModel, ViewItemCharacterListBinding> =
    CharactersViewHolder(
      ViewItemCharacterListBinding.inflate(LayoutInflater.from(parent.context), parent, false),
      onCharacterClick = onCharacterClick
    )
}


private class CharactersViewHolder(
  binding: ViewItemCharacterListBinding,
  private inline val onCharacterClick: (id: String) -> Unit
) :
  BaseViewHolder<CharacterUiModel, ViewItemCharacterListBinding>(binding) {
  override fun bind(viewState: CharacterUiModel) {
    binding.tvName.text = viewState.name
    binding.tvName.setOnClickListener { onCharacterClick(viewState.id) }
  }
}

