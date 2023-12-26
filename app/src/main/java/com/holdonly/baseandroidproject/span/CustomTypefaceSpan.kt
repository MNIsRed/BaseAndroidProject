package com.holdonly.baseandroidproject.span

import android.graphics.Typeface
import android.text.TextPaint
import android.text.style.MetricAffectingSpan

/**
 * <pre>
 *     author : holdonly
 *     e-mail : suliliveinchina@gmail.com
 *     time   : 2023/11/29
 *     desc   : 在api28一下通过typeface创建typefaceSpan
 *     from https://stackoverflow.com/a/55368704
 *     version: 1.0
 * </pre>
 */
class CustomTypefaceSpan(private val typeface: Typeface?) : MetricAffectingSpan() {
    override fun updateDrawState(paint: TextPaint) {
        paint.typeface = typeface
    }

    override fun updateMeasureState(paint: TextPaint) {
        paint.typeface = typeface
    }
}