package com.xecoding.loceat.model.persistent

import com.xecoding.loceat.model.annotations.KeyType

enum class Settings constructor(
    @param:KeyType
    @field:KeyType private val mType: Int
) : SharedPrefsData.Key {
    DEFAULT_ADDRESS(KeyType.STRING),
    DEFAULT_LAT(KeyType.STRING),
    DEFAULT_LNG(KeyType.STRING);

    @KeyType
    override val type: Int
        get() = mType

    companion object {
        val prefsName: String get() = Settings::class.java.simpleName
    }
}

