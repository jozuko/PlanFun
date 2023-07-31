package com.jozu.compose.planfun.usecase

import com.jozu.compose.planfun.domain.AccountRepository
import javax.inject.Inject

/**
 *
 * Created by jozuko on 2023/07/31.
 * Copyright (c) 2023 Studio Jozu. All rights reserved.
 */
class SignOutCase @Inject constructor(
    private val accountRepository: AccountRepository,
) {
    suspend fun signOut() {
        accountRepository.signOut()
    }
}