package com.bassem.demo_task.utils

import android.util.Log

val Any.logTag: String
    get() = this::class.java.simpleName

object Logger {

    fun Any.d(message: String) {
        Log.d(this.logTag, message)
    }

    fun Any.i(message: String) {
        Log.i(this.logTag, message)
    }

    fun Any.e(message: String) {
        Log.e(this.logTag, message)
    }


}