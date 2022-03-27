package com.testme.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewbinding.ViewBinding
import com.testme.domain.model.*


import com.testme.presentation.model.view.MviView
import com.testme.presentation.utils.launchAndCollectIn
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


abstract class BaseFragment<
  EVENT : ViewEvent, STATE : ViewState, EFFECT : Effect, ACTION : Action,
  VM : BaseViewModel<EVENT, STATE, EFFECT, ACTION>, ViewBindingType : ViewBinding>
  (@LayoutRes layoutId: Int) : Fragment(layoutId),
  MviView<STATE, EFFECT> {

  protected abstract val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> ViewBindingType

  // This property is only valid between onCreateView and onDestroyView.
  private var _binding: ViewBindingType? = null
  val binding get() = _binding!!

  abstract val viewModel: BaseViewModel<EVENT, STATE, EFFECT, ACTION>?

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {

    _binding = bindingInflater.invoke(inflater, container, false)
    return requireNotNull(_binding).root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    viewModel?.viewState?.launchAndCollectIn(viewLifecycleOwner, Lifecycle.State.STARTED) {
      renderState(it)
    }
    viewModel?.effect?.launchAndCollectIn(viewLifecycleOwner, Lifecycle.State.CREATED) {
      handleEffect(it)
    }

  }

  override fun onDestroyView() {
    _binding = null
    super.onDestroyView()
  }
}
