package com.testme.presentation.adapter

/**
 * Used in [RecyclerBaseAdapter] to create different view types
 * Should be implemented in [RecyclerAdapterItemViewState] and [BaseViewHolderFactory]
 *
 * Use [DefaultViewType] when there's only one view type
 */
interface ViewType {
  val value: Int
}

