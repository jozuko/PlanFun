package com.jozu.compose.planfun.presentation.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jozu.compose.planfun.domain.Account
import com.jozu.compose.planfun.domain.AccountFuture
import com.jozu.compose.planfun.domain.AccountRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

/**
 *
 * Created by jozuko on 2023/07/29.
 * Copyright (c) 2023 Studio Jozu. All rights reserved.
 */
@HiltViewModel
class PlanFunAppViewModel @Inject constructor(
    accountRepository: AccountRepository,
) : ViewModel() {
    /** ログイン中ユーザを返却するcallbackFlow */
    val accountState: StateFlow<AccountFuture<Account>> = accountRepository.accountFuture.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = AccountFuture.Proceeding,
    )
}