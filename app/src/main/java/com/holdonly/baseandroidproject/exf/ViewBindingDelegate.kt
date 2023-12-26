package com.holdonly.baseandroidproject.exf

import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * <pre>
 *     author : holdonly
 *     e-mail : suliliveinchina@gmail.com
 *     time   : 2023/10/25
 *     desc   : 无反射绑定ViewBinding方式扩展函数
 *     version: 1.0
 * </pre>
 */

class ActivityViewBindingDelegateNoReflect<VB : ViewBinding>(private val inflateFunc: (LayoutInflater) -> VB) :
    ReadOnlyProperty<AppCompatActivity, VB> {
    private var binding: VB? = null
    override fun getValue(thisRef: AppCompatActivity, property: KProperty<*>): VB {
        binding?.let { return it }
        return inflateFunc(thisRef.layoutInflater).also {
            thisRef.setContentView(it.root)
            binding = it
        }
    }
}

class AppCompatActivityViewBinding<VB : ViewBinding>(bindingClass: Class<VB>) :
    ReadOnlyProperty<AppCompatActivity, VB> {
    private val bindMethod = bindingClass.getMethod("bind", View::class.java)

    private var binding: VB? = null
    override fun getValue(thisRef: AppCompatActivity, property: KProperty<*>): VB {
        binding?.let { return it }
        return (bindMethod.invoke(null, thisRef.window.decorView) as VB).also {
            binding = it
        }

    }
}

fun <VB : ViewBinding> AppCompatActivity.viewBinding(inflateFunc: (LayoutInflater) -> VB) =
    ActivityViewBindingDelegateNoReflect(inflateFunc)

inline fun <reified VB : ViewBinding> AppCompatActivity.viewBinding() =
    AppCompatActivityViewBinding(VB::class.java)

