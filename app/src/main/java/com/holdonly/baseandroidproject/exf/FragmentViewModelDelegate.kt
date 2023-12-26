package com.holdonly.baseandroidproject.exf

import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * <pre>
 *     author : holdonly
 *     e-mail : suliliveinchina@gmail.com
 *     time   : 2023/11/15
 *     desc   :
 *     version: 1.0
 * </pre>
 */
class FragmentViewModelDelegate<T : ViewModel>(private val clazz: Class<T>) :
    ReadOnlyProperty<Fragment, T> {
    private var viewModel: T? = null
    override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
        viewModel?.let {
            return it
        }
        return ViewModelProvider(thisRef)[clazz].also {
            viewModel = it
        }
    }
}

class FragmentActivityViewModelDelegate<T : ViewModel>(
    private val fragment: Fragment,
    private val clazz: Class<T>
) : ReadOnlyProperty<Fragment, T?> {
    private var viewModel: T? = null

    init {
        fragment.lifecycle.addObserver(object : DefaultLifecycleObserver {
            override fun onCreate(owner: LifecycleOwner) {
                super.onCreate(owner)
                ViewModelProvider(fragment.requireActivity())[clazz].also {
                    viewModel = it
                }
            }
        })
    }

    override fun getValue(thisRef: Fragment, property: KProperty<*>): T? {
        return viewModel
    }
}

inline fun <reified T : ViewModel> Fragment.bindViewModel() =
    FragmentViewModelDelegate(T::class.java)

inline fun <reified T : ViewModel> Fragment.bindActivityViewModel() =
    FragmentActivityViewModelDelegate(this, T::class.java)

