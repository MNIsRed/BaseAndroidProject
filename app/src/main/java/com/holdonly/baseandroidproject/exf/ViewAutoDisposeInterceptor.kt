package com.holdonly.baseandroidproject.exf

import android.view.View
import kotlinx.coroutines.job
import kotlin.coroutines.Continuation
import kotlin.coroutines.ContinuationInterceptor
import kotlin.coroutines.CoroutineContext

/**
 * <pre>
 *     author : holdonly
 *     e-mail : suliliveinchina@gmail.com
 *     time   : 2023/09/20
 *     desc   :
 *     version: 1.0
 * </pre>
 */
class ViewAutoDisposeInterceptor(private val mView: View) : ContinuationInterceptor{
    override val key: CoroutineContext.Key<*>
        get() = ContinuationInterceptor

    override fun <T> interceptContinuation(continuation: Continuation<T>): Continuation<T> {
        continuation.context.job.let {
            mView.addOnAttachStateChangeListener(ViewLifeCycleListener(it))
        }
        return continuation
    }
}