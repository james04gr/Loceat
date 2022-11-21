package com.xecoding.loceat.model.persistent

import androidx.annotation.Nullable
import com.xecoding.loceat.model.annotations.KeyType

interface SharedPrefsData<K> where K : Enum<*>, K : SharedPrefsData.Key {

    interface Key {
        @get:KeyType
        val type: Int
    }

    fun write(key: K, value: String)

    fun write(key: K, value: Int)

    fun write(key: K, value: Boolean)

    fun write(key: K, value: Set<String>)

    fun write(key: K, value: Array<String>)

    fun write(key: K, value: Long)

    @Nullable
    fun read(key: K, @Nullable defaultValue: String?): String?

    fun read(key: K, defaultValue: Int): Int

    fun read(key: K, defaultValue: Boolean): Boolean

    fun read(key: K, defaultValue: Set<String>): Set<String>

    fun read(key: K, defaultValue: Array<String>): Array<String>

    fun read(key: K, defaultValue: Long): Long

    fun remove(key: K)

    fun remove(key: String)

    fun commit()

}
