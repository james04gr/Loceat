package com.xecoding.loceat.extension

import android.os.Build
import com.xecoding.loceat.BuildConfig

inline fun isDebug(code: () -> Unit) {
    if (BuildConfig.DEBUG) {
        code()
    }
}

inline fun isAboveMarsh(code: () -> Unit, other: () -> Unit) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        code()
    } else {
        other()
    }
}