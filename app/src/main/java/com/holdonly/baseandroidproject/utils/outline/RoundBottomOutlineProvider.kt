package com.holdonly.baseandroidproject.utils.outline

import android.graphics.Outline
import android.graphics.Path
import android.os.Build
import android.view.View
import android.view.ViewOutlineProvider
import androidx.annotation.RequiresApi

/**
 * 低版本的setConvexPath无效，只能在Api30以上使用setPath达成预期效果
 */
@RequiresApi(Build.VERSION_CODES.R)
class RoundBottomOutlineProvider(private val mRadius: Float) : ViewOutlineProvider() {
    private val path = Path()

    override fun getOutline(view: View, outline: Outline) {
        val w = view.width.toFloat()
        val h = view.height.toFloat()
        path.apply {
            reset()
            moveTo(0f, 0f)
            lineTo(w, 0f)
            lineTo(w, h - mRadius)
            quadTo(w, h, w - mRadius, h) // 底部圆角
            lineTo(mRadius, h)
            quadTo(0f, h, 0f, h - mRadius)
            lineTo(0f, 0f)
        }
        outline.setPath(path)
    }
}