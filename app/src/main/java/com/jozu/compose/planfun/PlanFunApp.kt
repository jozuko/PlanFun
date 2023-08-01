package com.jozu.compose.planfun

import android.app.Application
import com.jozu.compose.planfun.infra.log.LogTree
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

/**
 *
 * Created by jozuko on 2023/07/29.
 * Copyright (c) 2023 Studio Jozu. All rights reserved.
 */
@HiltAndroidApp
class PlanFunApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(LogTree())
        Timber.d("<hello>")
    }
}