package com.jozu.compose.planfun.domain

/**
 *
 * Created by jozuko on 2023/07/22.
 * Copyright (c) 2023 Studio Jozu. All rights reserved.
 */
sealed class AccountFuture<out Account> {
    object Proceeding : AccountFuture<Nothing>()
    object Error : AccountFuture<Nothing>()
    object Unauthorized : AccountFuture<Nothing>()
    data class Authorized(val account: Account) : AccountFuture<Account>()
}