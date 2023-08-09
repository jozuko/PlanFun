package com.jozu.compose.planfun.presentation.screen.spot

import androidx.compose.runtime.Stable
import com.jozu.compose.planfun.domain.spot.Spot

/**
 *
 * Created by jozuko on 2023/08/04.
 * Copyright (c) 2023 Studio Jozu. All rights reserved.
 */
// データを保持するクラスがStableであれば、アイテムが最初に画面に表示されるときにコンポジションが発生し、その後は画面から消えるまで、データが変更されない限り、再コンポジションは発生しません。
@Stable
data class SpotListUiState(
    val spotList: List<Spot> = emptyList(),
) {
    fun updateSpotList(spotList: List<Spot>): SpotListUiState {
        return copy(spotList = spotList)
    }
}