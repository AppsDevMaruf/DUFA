package org.dufa.dufa9596

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class BaseFragment<T : ViewDataBinding> : Fragment() {


    private var _binding: T? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, getFragmentView(), container, false)

        return binding.root
    }

    protected fun Fragment.hideKeyboard() {
        view?.let { activity?.hideKeyboard(it) }
    }

    protected fun Context.hideKeyboard(view: View) {
        val inputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configUi()
        setupNavigation()
        binObserver()


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }

    abstract fun getFragmentView(): Int
    open fun configUi() {}
    open fun setupNavigation() {}
    open fun binObserver() {}


}