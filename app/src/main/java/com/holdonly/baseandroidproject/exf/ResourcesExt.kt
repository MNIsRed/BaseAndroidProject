package com.holdonly.baseandroidproject.exf

import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.holdonly.baseandroidproject.application.AppApplication

/**
 * <pre>
 *     author : holdonly
 *     e-mail : suliliveinchina@gmail.com
 *     time   : 2023/11/22
 *     desc   :
 *     version: 1.0
 * </pre>
 */
fun Int.getColor(): Int {
    return ContextCompat.getColor(AppApplication.getInstance(), this)
}

fun Int.getDrawable(): Drawable? {
    return ContextCompat.getDrawable(AppApplication.getInstance(), this)
}