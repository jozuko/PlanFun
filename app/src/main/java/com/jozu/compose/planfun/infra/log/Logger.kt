package com.jozu.compose.planfun.infra.log

import org.jetbrains.annotations.NonNls
import timber.log.Timber

/**
 * Timberのwrapper
 *
 * Created by jozuko on 2023/07/29.
 * Copyright (c) 2023 Studio Jozu. All rights reserved.
 */
@Suppress("unused")
object Logger {
    fun d(@NonNls message: String?, vararg args: Any?) {
        Timber.d(message, args)
    }

    fun d(t: Throwable?) {
        Timber.d(t)
    }

    fun d(t: Throwable?, @NonNls message: String?, vararg args: Any?) {
        Timber.d(t, message, args)
    }

    fun i(@NonNls message: String?, vararg args: Any?) {
        Timber.i(message, args)
    }

    fun i(t: Throwable?) {
        Timber.i(t)
    }

    fun i(t: Throwable?, @NonNls message: String?, vararg args: Any?) {
        Timber.i(t, message, args)
    }

    fun e(@NonNls message: String?, vararg args: Any?) {
        Timber.e(message, args)
    }

    fun e(t: Throwable?) {
        Timber.e(t)
    }

    fun e(t: Throwable?, @NonNls message: String?, vararg args: Any?) {
        Timber.e(t, message, args)
    }
}