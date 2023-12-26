package com.holdonly.baseandroidproject.exf

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * <pre>
 *     author : holdonly
 *     e-mail : suliliveinchina@gmail.com
 *     time   : 2023/11/17
 *     desc   :
 *     version: 1.0
 * </pre>
 */
class ActivityViewModelDelegate<VM : ViewModel>(private val clazz: Class<VM>) :
    ReadOnlyProperty<AppCompatActivity, VM> {
    private var viewModel: VM? = null
    override fun getValue(thisRef: AppCompatActivity, property: KProperty<*>): VM {
        viewModel?.let {
            return it
        }
        return ViewModelProvider(thisRef)[clazz].also {
            viewModel = it
        }
    }
}

inline fun <reified T : ViewModel> AppCompatActivity.bindViewModel() =
    ActivityViewModelDelegate(T::class.java)