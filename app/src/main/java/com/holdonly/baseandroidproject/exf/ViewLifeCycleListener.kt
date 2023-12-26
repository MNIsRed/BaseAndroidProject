package com.holdonly.baseandroidproject.exf

import android.view.View
import kotlinx.coroutines.Job

/**
 * <pre>
 *     author : holdonly
 *     e-mail : suliliveinchina@gmail.com
 *     time   : 2023/09/20
 *     desc   :
 *     version: 1.0
 * </pre>
 */
class ViewLifeCycleListener(private val job:Job) : View.OnAttachStateChangeListener{
    override fun onViewAttachedToWindow(p0: View) {

    }

    override fun onViewDetachedFromWindow(v: View) {
        v.removeOnAttachStateChangeListener(this)
        job.cancel()
    }
}