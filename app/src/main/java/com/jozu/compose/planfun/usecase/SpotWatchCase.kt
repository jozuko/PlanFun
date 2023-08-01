package com.jozu.compose.planfun.usecase

import com.jozu.compose.planfun.domain.Spot
import com.jozu.compose.planfun.domain.SpotChange
import com.jozu.compose.planfun.domain.SpotRepository
import kotlinx.coroutines.flow.Flow
import timber.log.Timber
import javax.inject.Inject

/**
 *
 * Created by jozuko on 2023/08/01.
 * Copyright (c) 2023 Studio Jozu. All rights reserved.
 */
class SpotWatchCase @Inject constructor(
    private val spotRepository: SpotRepository,
) {
    fun addListener(): Flow<List<SpotChange>> {
        return spotRepository.spotListFlow
    }

    fun merge(currentList: List<Spot>, changeList: List<SpotChange>): List<Spot> {
        val list = currentList.toMutableList()
        Timber.d("merge ${currentList.joinToString { it.id ?: "" }}")

        val modList = changeList.filter { it.type == SpotChange.Type.ADDED || it.type == SpotChange.Type.MODIFIED }
        modList.forEach { spotChange ->
            val index = list.indexOfFirst { it.id == spotChange.spot.id }
            if (index < 0) {
                list.add(spotChange.spot)
                Timber.d("added ${list.joinToString { it.id ?: "" }}")
            } else {
                list[index] = spotChange.spot
                Timber.d("moded ${list.joinToString { it.id ?: "" }}")
            }
        }

        val removeList = changeList.filter { it.type == SpotChange.Type.REMOVED }
        removeList.forEach { spotChange ->
            val index = list.indexOfFirst { it.id == spotChange.spot.id }
            if (index >= 0) {
                list.removeAt(index)
            }
        }

        Timber.d("merged ${list.joinToString { it.id ?: "" }}")
        return list
    }
}