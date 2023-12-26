package com.holdonly.baseandroidproject.exf

import android.graphics.drawable.Drawable
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.AbsoluteSizeSpan
import android.text.style.ImageSpan
import android.text.style.TypefaceSpan
import androidx.annotation.FontRes
import androidx.core.content.res.ResourcesCompat
import androidx.core.text.inSpans
import com.holdonly.baseandroidproject.application.AppApplication
import com.holdonly.baseandroidproject.span.BaselineMarginSpan
import com.holdonly.baseandroidproject.span.CustomTypefaceSpan
import com.holdonly.baseandroidproject.span.CustomVerticalCenterSpan
import com.holdonly.baseandroidproject.span.DivideSpan

/**
 * <pre>
 *     author : holdonly
 *     e-mail : suliliveinchina@gmail.com
 *     time   : 2023/09/19
 *     desc   :
 *     version: 1.0
 * </pre>
 */
fun SpannableStringBuilder.imageLeft(
    drawable: Drawable?,
): SpannableStringBuilder {
    drawable?.apply {
        setBounds(0, 0, intrinsicWidth, intrinsicHeight)
    }?.let {
        append("# ", 0, 2)
        setSpan(ImageSpan(it), 0, 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
    }
    return this
}

fun SpannableStringBuilder.font(
    @FontRes font: Int,
    builderAction: SpannableStringBuilder.() -> Unit
): SpannableStringBuilder {
    ResourcesCompat.getFont(AppApplication.getInstance(), font)?.let {
        inSpans(CustomTypefaceSpan(it), builderAction)
    }
    return this
}

fun SpannableStringBuilder.font(
    fontFamily: String,
    builderAction: SpannableStringBuilder.() -> Unit
): SpannableStringBuilder {
    inSpans(TypefaceSpan(fontFamily), builderAction)
    return this
}

fun SpannableStringBuilder.spSize(
    spSize: Float,
    builderAction: SpannableStringBuilder.() -> Unit
): SpannableStringBuilder {
    inSpans(AbsoluteSizeSpan(spSize.sp2px.toInt()), builderAction)
    return this
}

fun SpannableStringBuilder.size(
    size: Int,
    builderAction: SpannableStringBuilder.() -> Unit
): SpannableStringBuilder {
    inSpans(AbsoluteSizeSpan(size), builderAction)
    return this
}

fun SpannableStringBuilder.divide(
    divideSize: Int,
): SpannableStringBuilder {
    append(" ")
    setSpan(DivideSpan(divideSize), length - 1, length, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
    return this
}

fun SpannableStringBuilder.centerVertical(
    fontSize: Float,
    builderAction: SpannableStringBuilder.() -> Unit
): SpannableStringBuilder {
    inSpans(CustomVerticalCenterSpan(fontSize.sp2px.toInt()), builderAction)
    return this
}

fun SpannableStringBuilder.baselineMargin(
    margin: Int,
    builderAction: SpannableStringBuilder.() -> Unit
): SpannableStringBuilder {
    inSpans(BaselineMarginSpan(margin), builderAction)
    return this
}