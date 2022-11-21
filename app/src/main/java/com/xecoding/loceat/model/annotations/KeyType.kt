package com.xecoding.loceat.model.annotations

import androidx.annotation.IntDef
import com.xecoding.loceat.model.annotations.KeyType.Companion.BOOLEAN
import com.xecoding.loceat.model.annotations.KeyType.Companion.INT
import com.xecoding.loceat.model.annotations.KeyType.Companion.LONG
import com.xecoding.loceat.model.annotations.KeyType.Companion.STRING
import com.xecoding.loceat.model.annotations.KeyType.Companion.STRING_SET

@IntDef(STRING, INT, BOOLEAN, STRING_SET, LONG)
@MustBeDocumented
annotation class KeyType {
    companion object {
        const val STRING = 0
        const val INT = 1
        const val BOOLEAN = 2
        const val STRING_SET = 3
        const val LONG = 4
    }
}
