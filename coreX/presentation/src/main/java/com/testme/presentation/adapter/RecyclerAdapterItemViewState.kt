package com.testme.presentation.adapter

/**
 * Use ths interface for view Item used with [RecyclerBaseAdapter]
 *
 * @property id Unique id to use for Diff check
 */
interface RecyclerAdapterItemViewState {
  val viewType: ViewType
  val id: String
}