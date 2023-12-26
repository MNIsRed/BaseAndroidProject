package com.holdonly.baseandroidproject.exf

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

/**
 * <pre>
 *     author : holdonly
 *     e-mail : suliliveinchina@gmail.com
 *     time   : 2023/09/19
 *     desc   : 存一些顶层函数
 *     version: 1.0
 * </pre>
 */


/**
 * 于协程中实现倒计时
 * @param duration 倒计时时长
 * @param interval 倒计时步长
 * @param scope 协程作用域
 * @param onTick 倒计时变更，回调
 * @param onStart 倒计时开始，回调
 * @param onEnd 倒计时结束，回调
 *
 * @return [Job] 可用于在需要时，取消倒计时
 */
fun countDownCoroutine(
    duration: Int,
    interval: Int = 1,
    scope: CoroutineScope,
    onTick: (Int) -> Unit,
    onStart: (() -> Unit)? = null,
    onEnd: (() -> Unit)? = null,
    onException: (() -> Unit)? = null,
): Job {
    //默认不需要倒计时的话，直接执行onEnd回调
    if (duration <= 0){
        onEnd?.invoke()
    }
    if (interval <= 0) {
        throw IllegalArgumentException("interval can not less than zero")
    }
    //增加500ms作为偏差，避免
    val endTime = duration.toLong() * 1000 + System.currentTimeMillis()
    return scope.launch(Dispatchers.Default) {
        try {
            flow {
                var leftMillis = endTime - System.currentTimeMillis()
                while (leftMillis > 0) {
                    // +1 避免2900ms 这种被当作2s处理。
                    emit((leftMillis / 1000 + 1).toInt())
                    delay((interval * 1000).toLong())
                    leftMillis = endTime - System.currentTimeMillis()
                }
            }
                .onEach { onTick.invoke(it) }
                .onCompletion {
                    // 正常结束，才能回调。
                    if (it == null) {
                        onEnd?.invoke()
                    } else {
                        onException?.invoke()
                    }
                }
                .onStart { onStart?.invoke() }
                .flowOn(Dispatchers.Main)
                .collect {}
        } catch (e: Exception) {
            Log.e("CourouError", e.message ?: "")
            e.printStackTrace()
            onException?.invoke()
        }
    }
}