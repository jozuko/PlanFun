package com.jozu.compose.planfun.usecase

import android.content.Intent
import com.jozu.compose.planfun.domain.share_target.SharedContent
import com.jozu.compose.planfun.domain.spot.SpotRepository
import javax.inject.Inject

/**
 *
 * Created by jozuko on 2023/08/18.
 * Copyright (c) 2023 Studio Jozu. All rights reserved.
 */
class SharedAnalyzeUseCase @Inject constructor(
    private val spotRepository: SpotRepository,
) {
    fun invoke(intent: Intent): SharedContent<String> {
        if (intent.action != Intent.ACTION_SEND) {
            return SharedContent.Error(IllegalArgumentException("action is not action_send. (${intent.action})"))
        }

        return if (intent.type?.startsWith("text/") == true) {
            val textContent = intent.getStringExtra(Intent.EXTRA_TEXT) ?: ""
            SharedContent.Analyzed(textContent)
        } else {
            return SharedContent.Error(IllegalArgumentException("type is not text. (${intent.type})"))
        }
    }
}