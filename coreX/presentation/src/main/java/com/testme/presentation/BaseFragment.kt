package com.testme.presentation

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment


abstract class BaseFragment(@LayoutRes layoutId: Int) : Fragment(layoutId)
