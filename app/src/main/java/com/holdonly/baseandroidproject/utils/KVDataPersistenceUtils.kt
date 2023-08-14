package com.holdonly.baseandroidproject.utils

import android.util.Log
import com.tencent.mmkv.MMKV

/**
 * @Description: 键值对数据存储工具类，当前利用MMKV
 * @author: holdOnly
 * @email: suliliveinchina@gmail.com
 */
class KVDataPersistenceUtils private constructor() {
    /**
     * 注意：
     * 1. 非MMKV支持的数据类型，将不会进行保存。
     * TODO 添加类型限制，保证编译通过都能保存
     */
    fun put(key: String, value: Any?) {
        with(MMKV.defaultMMKV()) {
            when (value) {
                is ByteArray -> putBytes(key, value)
                is Boolean -> putBoolean(key, value)
                is Int -> putInt(key, value)
                is Long -> putLong(key, value)
                is Float -> putFloat(key, value)
                is String -> putString(key, value)
                else -> Log.e(TAG, "save unKnownValue")
            }
        }
    }

    fun <T> get(key: String, defaultValue: T): T {
        return with(MMKV.defaultMMKV()) {
            @Suppress("UNCHECKED_CAST")
            when (defaultValue) {
                is ByteArray -> getBytes(key, defaultValue) as T
                is Boolean -> getBoolean(key, defaultValue) as T
                is Int -> getInt(key, defaultValue) as T
                is Long -> getLong(key, defaultValue) as T
                is Float -> getFloat(key, defaultValue) as T
                is String -> getString(key, defaultValue) as T
                else -> defaultValue
            }
        }
    }

    companion object {
        private const val TAG = "KVUtils"
        val instance by lazy {
            KVDataPersistenceUtils()
        }
    }
}