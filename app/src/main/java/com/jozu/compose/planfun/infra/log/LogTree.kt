package com.jozu.compose.planfun.infra.log

import android.util.Log
import com.jozu.compose.planfun.BuildConfig
import com.jozu.compose.planfun.infra.log.LogTree.Companion.SHOW_THREAD_NAME
import timber.log.Timber

/**
 * debugログはデバッグの時だけ出力する
 * [SHOW_THREAD_NAME]がtrueの時は、ログにスレッド名を出す
 *
 * Created by jozuko on 2023/07/29.
 * Copyright (c) 2023 Studio Jozu. All rights reserved.
 */
class LogTree : Timber.DebugTree() {
    companion object {
        private const val SHOW_THREAD_NAME = true
    }

    override fun isLoggable(tag: String?, priority: Int): Boolean {
        if (priority == Log.DEBUG) {
            return BuildConfig.DEBUG
        }

        return super.isLoggable(tag, priority)
    }

    override fun log(priority: Int, message: String?, vararg args: Any?) {
        this.log(
            priority = priority,
            t = null,
            message = message,
            args = args,
        )
    }

    override fun log(priority: Int, t: Throwable?) {
        this.log(
            priority = priority,
            t = t,
            message = null,
        )
    }

    override fun log(priority: Int, t: Throwable?, message: String?, vararg args: Any?) {
        if (!SHOW_THREAD_NAME) {
            super.log(priority, t, message, *args)
            return
        }

        val threadName = Thread.currentThread().name
        val logMessage = if (message.isNullOrEmpty()) {
            threadName
        } else if (args.isEmpty()) {
            "[$threadName]$message"
        } else {
            "[$threadName]${message.format(*args)}"
        }

        super.log(priority, t, logMessage)
    }
}