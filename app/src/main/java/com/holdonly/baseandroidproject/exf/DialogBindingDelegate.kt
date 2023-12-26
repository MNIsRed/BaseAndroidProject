package com.holdonly.baseandroidproject.exf

import android.app.Dialog
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * <pre>
 *     author : holdonly
 *     e-mail : suliliveinchina@gmail.com
 *     time   : 2023/12/15
 *     desc   : 为dialog绑定ViewBinding
 *     version: 1.0
 * </pre>
 */

class DialogViewBindingProperty<VB:ViewBinding>(private val bindingClass: Class<VB>,dialog:Dialog) : ReadOnlyProperty<Dialog,VB>{
    private var binding: VB? = null
    override fun getValue(thisRef: Dialog, property: KProperty<*>): VB {
        binding?.let { return it }

        val inflateMethod = bindingClass.getMethod("inflate", LayoutInflater::class.java)
        @Suppress("UNCHECKED_CAST")
        binding = inflateMethod.invoke(null, thisRef.layoutInflater) as VB
        thisRef.setContentView(binding!!.root)
        return binding!!
    }

}

inline fun <reified T : ViewBinding> Dialog.viewBinding() = DialogViewBindingProperty(T::class.java,this)