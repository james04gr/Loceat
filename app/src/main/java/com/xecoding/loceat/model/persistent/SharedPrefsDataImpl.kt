package com.xecoding.loceat.model.persistent

import android.content.Context
import android.content.SharedPreferences
import androidx.annotation.Nullable
import com.xecoding.loceat.model.annotations.KeyType
import java.util.*

class SharedPrefsDataImpl<K>(context: Context, name: String) :
    SharedPrefsData<K> where K : Enum<*>, K : SharedPrefsData.Key {

    private val sharedPref: SharedPreferences =
        context.getSharedPreferences(name, Context.MODE_PRIVATE)

    private val editor: SharedPreferences.Editor
        get() = sharedPref.edit()

    override fun write(key: K, value: String) {
        checkKeyValidity(key, KeyType.STRING)
        editor.putString(key.toString(), value).apply()
    }

    override fun write(key: K, value: Int) {
        checkKeyValidity(key, KeyType.INT)
        editor.putInt(key.toString(), value).apply()
    }

    override fun write(key: K, value: Boolean) {
        checkKeyValidity(key, KeyType.BOOLEAN)
        editor.putBoolean(key.toString(), value).apply()
    }

    override fun write(key: K, value: Set<String>) {
        checkKeyValidity(key, KeyType.STRING_SET)
        // Android BUG - overriding of String set is not working
        editor.remove(key.toString()).apply()
        editor.putStringSet(key.toString(), value).apply()
    }

    override fun write(key: K, value: Array<String>) {
        write(key, HashSet(Arrays.asList(*value)))
    }

    override fun write(key: K, value: Long) {
        checkKeyValidity(key, KeyType.LONG)
        editor.putLong(key.toString(), value).apply()
    }

    @Nullable
    override fun read(key: K, @Nullable defaultValue: String?): String? {
        checkKeyValidity(key, KeyType.STRING)
        return sharedPref.getString(key.toString(), defaultValue)
    }

    override fun read(key: K, defaultValue: Int): Int {
        checkKeyValidity(key, KeyType.INT)
        return sharedPref.getInt(key.toString(), defaultValue)
    }

    override fun read(key: K, defaultValue: Boolean): Boolean {
        checkKeyValidity(key, KeyType.BOOLEAN)
        return sharedPref.getBoolean(key.toString(), defaultValue)
    }

    override fun read(key: K, defaultValue: Set<String>): Set<String> {
        checkKeyValidity(key, KeyType.STRING_SET)
        val set = sharedPref.getStringSet(key.toString(), defaultValue)
        return if (set === defaultValue)
            defaultValue
        else
            Collections.unmodifiableSet(set)
    }

    override fun read(key: K, defaultValue: Array<String>): Array<String> {
        val set = read(key, HashSet(Arrays.asList(*defaultValue)))
        return set.toTypedArray()
    }

    override fun read(key: K, defaultValue: Long): Long {
        checkKeyValidity(key, KeyType.LONG)
        return sharedPref.getLong(key.toString(), defaultValue)
    }

    override fun remove(key: K) {
        remove(key.toString())
    }

    override fun remove(key: String) {
        sharedPref.edit().remove(key).apply()
    }

    override fun commit() {
        editor.commit()
    }

    private fun checkKeyValidity(key: SharedPrefsData.Key, @KeyType type: Int) {
        if (key.type != type) {
            throw IllegalArgumentException(
                "Preference key:" + key.toString()
                        + " has the wrong type: " + key.type + " expected: " + type
            )
        }
    }

}
