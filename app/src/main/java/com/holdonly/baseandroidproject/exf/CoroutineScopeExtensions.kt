package com.holdonly.baseandroidproject.exf

import android.view.View
import com.holdonly.baseandroidproject.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

/**
 * <pre>
 *     author : holdonly
 *     e-mail : suliliveinchina@gmail.com
 *     time   : 2023/09/20
 *     desc   :
 *     version: 1.0
 * </pre>
 */
const val COROUTINE_SCOPE_EXTENSIONS_TAG = R.id.view_auto_coroutine_scope_id
val View.autoDisposeScope: CoroutineScope
    get() {
        return (getTag(COROUTINE_SCOPE_EXTENSIONS_TAG) as? CoroutineScope) ?: run {
            val scope =
                CoroutineScope(SupervisorJob() + Dispatchers.Main + ViewAutoDisposeInterceptor(this))
            setTag(COROUTINE_SCOPE_EXTENSIONS_TAG, scope)
            scope
        }.also {
            it
        }
    }