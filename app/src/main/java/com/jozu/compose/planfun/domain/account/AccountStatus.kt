package com.jozu.compose.planfun.domain.account

/**
 *
 * Created by jozuko on 2023/07/22.
 * Copyright (c) 2023 Studio Jozu. All rights reserved.
 */
sealed class AccountStatus<out Account> {
    object Proceeding : AccountStatus<Nothing>()
    object Error : AccountStatus<Nothing>()
    object Unauthorized : AccountStatus<Nothing>()
    data class Authorized(val account: Account) : AccountStatus<Account>()
}