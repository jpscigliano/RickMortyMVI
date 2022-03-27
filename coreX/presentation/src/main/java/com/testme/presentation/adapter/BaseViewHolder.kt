package com.testme.presentation.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

/**
 * @param VIEW_STATE Custom UI model for the view holder, must implement [RecyclerAdapterItemViewState]
 * @param BINDING The layout binding
 */
abstract class BaseViewHolder<out VIEW_STATE : RecyclerAdapterItemViewState, out BINDING : ViewBinding>(var binding: @UnsafeVariance BINDING) : RecyclerView.ViewHolder(binding.root) {

  fun bindViewHolder(viewState: @UnsafeVariance VIEW_STATE) {
    bind(viewState = viewState)
  }

  protected abstract fun bind(viewState: @UnsafeVariance VIEW_STATE)
}

/**
 * Factory to create instances of an implementation of [BaseViewHolder]
 *
 * @param MODEL Custom UI model for the view holder, must implement [RecyclerAdapterItemViewState]
 * @param BINDING The layout binding
 */
interface BaseViewHolderFactory<MODEL : RecyclerAdapterItemViewState, BINDING : ViewBinding> {
  val viewType: ViewType

  fun createViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<MODEL, BINDING>
}