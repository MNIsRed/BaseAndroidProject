package com.holdonly.baseandroidproject.span

import android.graphics.Canvas
import android.graphics.Paint
import android.text.TextPaint
import android.text.style.ReplacementSpan

/**
 * <pre>
 *     author : holdonly
 *     e-mail : suliliveinchina@gmail.com
 *     time   : 2023/12/18
 *     desc   : 文字居中span，应该只适用于一行文本的情况
 *     from https://blog.csdn.net/u010983881/article/details/53995020
 *     version: 1.0
 * </pre>
 */
class CustomVerticalCenterSpan(private val fontSize: Int) : ReplacementSpan() {
    private lateinit var mTextPaint: TextPaint
    override fun getSize(
        paint: Paint,
        text: CharSequence?,
        start: Int,
        end: Int,
        fm: Paint.FontMetricsInt?
    ): Int {
        return getCustomTextPaint(paint).measureText(
            text?.subSequence(start, end)?.toString() ?: ""
        ).toInt()
    }

    private fun getCustomTextPaint(srcPaint: Paint): TextPaint {
        return if (this::mTextPaint.isInitialized && srcPaint.color == mTextPaint.color) {
            mTextPaint
        } else {
            TextPaint(srcPaint).apply {
                textSize = fontSize.toFloat()
            }.also {
                mTextPaint = it
            }
        }
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
        val fm = getCustomTextPaint(paint).fontMetricsInt
        val resetY = y - ((y + fm.descent + y + fm.ascent) / 2 - (bottom + top) / 2)
        canvas.drawText(
            text?.subSequence(start, end)?.toString() ?: "",
            x,
            resetY.toFloat(),
            mTextPaint
        )
    }

}