package com.holdonly.baseandroidproject.utils

import android.content.Context
import android.util.Log
import com.blankj.utilcode.util.GsonUtils
import com.tencent.mmkv.MMKV

/**
 * @Description: 键值对数据存储工具类，当前利用MMKV
 * @author: holdOnly
 * @email: suliliveinchina@gmail.com
 */
class KVDataPersistenceUtils private constructor() {

    fun <T> getJson(key: String, type: Class<T>): T? {
        return try {
            get(key, String::class.java)?.also {
                return GsonUtils.fromJson(it, type)
            }
            return null
        } catch (e: Exception) {
            null
        }
    }

    fun <T> putJson(key: String, value: T?) {
        if (value == null) {
            put(key, null)
            return
        }
        put(key, GsonUtils.toJson(value))
    }

    /**
     * @param type 用于确定泛型类型
     */
    fun <T> getFromListType(key: String, type: Class<T>): List<T>? {
        val jsonContent = get(key, String::class.java)
        if (jsonContent.isNullOrEmpty()) return null
        return GsonUtils.fromJson(jsonContent, GsonUtils.getListType(type))
    }

    inline fun <reified T> getFromReified(key: String): T? {
        val jsonContent = get(key, String::class.java)
        if (jsonContent.isNullOrEmpty()) return null
        return GsonUtils.fromJson(jsonContent, T::class.java)
    }

    fun put(key: String, value: Any?) {
        with(MMKV.defaultMMKV()) {
            when (value) {
                is ByteArray -> putBytes(key, value)
                is Boolean -> putBoolean(key, value)
                is Int -> putInt(key, value)
                is Long -> putLong(key, value)
                is Float -> putFloat(key, value)
                is String -> putString(key, value)
                else ->{
                    if (value == null){
                        remove(key)
                    }else{
                        try {
                            GsonUtils.toJson(value).also {
                                putString(key, it)
                            }
                        } catch (e: Exception) {
                            Log.e(TAG, "save unKnownValue")
                        }
                    }
                }
            }
        }
    }

    fun <T> get(key: String, type: Class<T>): T? {
        return with(MMKV.defaultMMKV()) {
            @Suppress("UNCHECKED_CAST")
            when (type) {
                ByteArray::class.java -> getBytes(key, null) as T?
                Boolean::class.java -> getBoolean(key, false) as T?
                Int::class.java -> getInt(key, 0) as T?
                Long::class.java -> getLong(key, 0) as T?
                Float::class.java -> getFloat(key, 0f) as T?
                String::class.java -> getString(key, null) as T?
                else -> null
            }
        }
    }

    companion object {
        private const val TAG = "KVUtils"

        @JvmStatic
        val instance by lazy {
            KVDataPersistenceUtils()
        }

        @JvmStatic
        fun initMMKV(context: Context) {
            MMKV.initialize(context)
        }
    }
}