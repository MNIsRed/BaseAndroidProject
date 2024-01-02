package com.holdonly.baseandroidproject.exf

import android.os.Build
import android.text.Layout
import android.text.StaticLayout
import android.widget.TextView

/**
 * <pre>
 *     author : holdonly
 *     e-mail : suliliveinchina@gmail.com
 *     time   : 2024/01/02
 *     desc   :
 *     version: 1.0
 * </pre>
 */

//计算text一行的宽度，支持SpannedString
fun TextView.calculateTextWidth(): Float {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        StaticLayout.Builder.obtain(text, 0, text.length, paint, Int.MAX_VALUE).build()
            .getLineWidth(0)
    } else {
        StaticLayout(
            text, paint,
            Int.MAX_VALUE, Layout.Alignment.ALIGN_NORMAL, 1f, 0f, false
        ).getLineWidth(0)
    }
}