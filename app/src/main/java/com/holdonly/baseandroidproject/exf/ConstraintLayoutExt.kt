package com.holdonly.baseandroidproject.exf

import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.Group
import androidx.core.view.children

/**
 * <pre>
 *     author : holdonly
 *     e-mail : suliliveinchina@gmail.com
 *     time   : 2023/11/19
 *     desc   :
 *     version: 1.0
 * </pre>
 */
inline fun Group.setEach(crossinline func: (View) -> Unit) {
    val container = parent as ConstraintLayout
    referencedIds.forEach {
        func(container.findViewById<View>(it))
    }
}

inline fun ViewGroup.setEach(crossinline func: (View) -> Unit) {
    children.toList().forEach {
        func(it)
    }
}