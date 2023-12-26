package com.holdonly.baseandroidproject.application

import android.app.Application

/**
 * <pre>
 *     author : holdonly
 *     e-mail : suliliveinchina@gmail.com
 *     time   : 2023/12/26
 *     desc   :
 *     version: 1.0
 * </pre>
 */
class AppApplication : Application(){
    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
    }
    companion object{
        private lateinit var INSTANCE : AppApplication
        fun getInstance():AppApplication{
            return INSTANCE
        }
    }
}