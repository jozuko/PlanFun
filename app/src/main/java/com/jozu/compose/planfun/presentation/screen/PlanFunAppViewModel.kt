package com.jozu.compose.planfun.presentation.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jozu.compose.planfun.domain.account.Account
import com.jozu.compose.planfun.domain.account.AccountRepository
import com.jozu.compose.planfun.domain.account.AccountStatus
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
    val accountState: StateFlow<AccountStatus<Account>> = accountRepository.accountFuture.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = AccountStatus.Proceeding,
    )
}