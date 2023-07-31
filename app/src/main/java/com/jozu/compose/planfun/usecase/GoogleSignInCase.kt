package com.jozu.compose.planfun.usecase

import android.app.Activity
import android.content.Intent
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import com.jozu.compose.planfun.domain.AccountRepository
import com.jozu.compose.planfun.infra.log.Logger
import com.jozu.compose.planfun.usecase.model.SigninGoogleLegacyError
import javax.inject.Inject

/**
 *
 * Created by jozuko on 2023/07/28.
 * Copyright (c) 2023 Studio Jozu. All rights reserved.
 */
class GoogleSignInCase @Inject constructor(
    private val accountRepository: AccountRepository,
) {
    fun signIn(launcher: ActivityResultLauncher<Intent>) {
        val intent = accountRepository.requestGoogleAuth()
        launcher.launch(intent)
    }

    suspend fun onResultSignIn(result: ActivityResult) {
        if (result.resultCode != Activity.RESULT_OK) {
            Logger.e("<GoogleSignInCase>onResultSignIn ${SigninGoogleLegacyError(result.data).resultMessage}")
            return
        }

        val resultData = result.data ?: let {
            Logger.e("<GoogleSignInCase>onResultSignIn result.data is null")
            return
        }

        accountRepository.signInGoogle(resultData)
    }
}