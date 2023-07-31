package com.jozu.compose.planfun.domain

import android.content.Intent
import kotlinx.coroutines.flow.Flow

/**
 *
 * Created by jozuko on 2023/07/21.
 * Copyright (c) 2023 Studio Jozu. All rights reserved.
 */
interface AccountRepository {
    val accountFuture: Flow<AccountFuture<Account>>

    fun requestGoogleAuth(): Intent
    suspend fun signInGoogle(resultData: Intent)
    suspend fun signOut()
}