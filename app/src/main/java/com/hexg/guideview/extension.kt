package com.hexg.guideview

import android.util.Log

//quick way to write try/catch all error
inline fun Safe(protected: () -> Unit) {
    try {
        protected()
    } catch (e: Throwable) {
        Log.e("hxg", "err: ${e.message}")
    }
}