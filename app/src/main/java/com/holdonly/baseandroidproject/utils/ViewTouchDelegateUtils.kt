package com.holdonly.baseandroidproject.utils

import android.graphics.Rect
import android.view.TouchDelegate
import android.view.View
import com.holdonly.baseandroidproject.exf.dp2px
import java.lang.ref.WeakReference
import kotlin.math.abs

/**
 * 帮助扩大 View的点击范围，未详细测试，不排除部分view无法设置
 * 标准是48dp
 * from https://www.jianshu.com/p/cb5181418c7a
 */
class ViewTouchDelegateUtils {
    companion object {
        private val standardPixel = 48f.dp2px.toInt()

        /**setStandardViewDelegate
         *
         * @param viewReferences 批量设置多个view的标准点击范围
         */
        @JvmStatic
        fun setStandardViewDelegate(vararg viewReferences: WeakReference<View>) {
            for (viewRef in viewReferences) {
                setViewDelegate(viewRef, standardPixel, standardPixel)
            }
        }

        /**
         * 设置view的标准点击范围 48dp
         * @param viewReference
         */
        @JvmStatic
        fun setStandardViewDelegate(viewReference: WeakReference<View>) {
            setViewDelegate(viewReference, standardPixel, standardPixel)
        }

        /**
         * 设置view的点击范围,期望高度和宽度不可短于view宽高和高于父布局宽高
         * @param viewReference
         * @param hopeHeight
         * @param hopeWidth
         */
        @JvmStatic
        fun setViewDelegate(viewReference: WeakReference<View>, hopeWidth: Int, hopeHeight: Int) {
            viewReference.get()?.apply {
                post {
                    if (parent == null) return@post
                    val bounds = Rect()
                    getHitRect(bounds)
                    var leftExtra = (hopeWidth - bounds.right + bounds.left) / 2
                    var rightExtra = (hopeWidth - bounds.right + bounds.left) / 2
                    var topExtra = (hopeHeight - bounds.bottom + bounds.top) / 2
                    var bottomExtra = (hopeHeight - bounds.bottom + bounds.top) / 2
                    if (leftExtra > 0) {
                        if ((bounds.left - leftExtra) < 0) {
                            leftExtra = abs(bounds.left)
                        }
                        if ((bounds.right + rightExtra) > (parent as View).width) {
                            rightExtra = abs((parent as View).width - bounds.right)
                        }
                        bounds.left -= leftExtra
                        bounds.right += rightExtra
                    }

                    if (bottomExtra > 0) {
                        if ((bounds.top - topExtra) < 0) {
                            topExtra = abs(bounds.top)
                        }
                        if ((bounds.bottom + bottomExtra) > (parent as View).height) {
                            bottomExtra = (parent as View).height - bounds.bottom
                        }
                        bounds.top -= topExtra
                        bounds.bottom += bottomExtra
                    }

                    val touchDelegate = TouchDelegate(bounds, this)
                    if (View::class.java.isInstance(parent)) {
                        (parent as View).touchDelegate = touchDelegate
                    }
                }
            }
        }
    }
}