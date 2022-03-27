package com.testme.presentation.adapter

import android.view.ViewGroup

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.viewbinding.ViewBinding

/**
 * Single generic adapter that can be used for any recyclerview.
 *
 * Configure a custom [BaseViewHolder] and a [BaseViewHolderFactory], and setup a recyclerview with
 *
 * ```
 * recyclerView.adapter = BaseAdapter(MyViewHolderFactory())
 * ```
 *
 * Since it's a [ListAdapter], data can be refreshed by calling
 * ```
 * recyclerView.adapter.submitList(myList)
 * ```
 *
 * It also supports item selection.
 * @see BaseViewHolder.bind
 *
 * @property viewHolderFactory the list of view holder factories, they must implement [BaseViewHolderFactory]. The order does not matter.
 */
class RecyclerBaseAdapter(
  private vararg val viewHolderFactory: BaseViewHolderFactory<*, *>
) : ListAdapter<RecyclerAdapterItemViewState, BaseViewHolder<RecyclerAdapterItemViewState, ViewBinding>>(object : DiffUtil.ItemCallback<RecyclerAdapterItemViewState>() {
  override fun areItemsTheSame(oldItem: RecyclerAdapterItemViewState, newItem: RecyclerAdapterItemViewState): Boolean = oldItem.id == newItem.id

  @Suppress("ReplaceCallWithBinaryOperator")
  override fun areContentsTheSame(oldItem: RecyclerAdapterItemViewState, newItem: RecyclerAdapterItemViewState): Boolean = oldItem.equals(newItem)
}) {

  override fun getItemViewType(position: Int): Int {
    return getItem(position).viewType.value
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<RecyclerAdapterItemViewState, ViewBinding> {
    return viewHolderFactory.first { it.viewType.value == viewType }.createViewHolder(parent, viewType)
  }

  override fun onBindViewHolder(holder: BaseViewHolder<RecyclerAdapterItemViewState, ViewBinding>, position: Int) {
    holder.apply {
      bindViewHolder(getItem(position))
    }
  }
}
