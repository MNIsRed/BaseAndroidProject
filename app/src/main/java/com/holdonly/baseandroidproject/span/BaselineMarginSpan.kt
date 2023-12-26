package com.holdonly.baseandroidproject.span

import android.graphics.Canvas
import android.graphics.Paint
import android.text.style.ReplacementSpan

/**
 * <pre>
 *     author : holdonly
 *     e-mail : suliliveinchina@gmail.com
 *     time   : 2023/12/18
 *     desc   : 让文字可以稍微向上抬的Span
 *     version: 1.0
 * </pre>
 */
class BaselineMarginSpan(private val baselineMargin: Int) : ReplacementSpan() {
    override fun getSize(
        paint: Paint,
        text: CharSequence?,
        start: Int,
        end: Int,
        fm: Paint.FontMetricsInt?
    ): Int {
        return paint.measureText(text?.subSequence(start, end)?.toString() ?: "").toInt()
    }

    override fun draw(
        canvas: Canvas,
        text: CharSequence?,
        start: Int,
        end: Int,
        x: Float,
        top: Int,
        y: Int,
        bottom: Int,
        paint: Paint
    ) {
        canvas.drawText(
            text?.subSequence(start, end)?.toString() ?: "",
            x,
            y + baselineMargin.toFloat(),
            paint
        )
    }

}