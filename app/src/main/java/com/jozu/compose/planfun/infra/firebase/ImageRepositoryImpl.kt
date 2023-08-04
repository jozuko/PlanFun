package com.jozu.compose.planfun.infra.firebase

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.jozu.compose.planfun.domain.ImageMergeStatus
import com.jozu.compose.planfun.domain.ImageRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.tasks.await
import timber.log.Timber
import java.io.File
import javax.inject.Inject

/**
 *
 * Created by jozuko on 2023/08/02.
 * Copyright (c) 2023 Studio Jozu. All rights reserved.
 */
class ImageRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val dispatcher: CoroutineDispatcher,
    private val auth: FirebaseAuth,
    private val storage: FirebaseStorage,
) : ImageRepository {
    companion object {
        private const val SERVER_PATH_IMAGES = "images"
        private const val LOCAL_PATH_IMAGES = "images"
    }

    private val imagesRef: StorageReference?
        get() = auth.currentUser?.let { user ->
            storage
                .reference
                .child(SERVER_PATH_IMAGES)
                .child(user.uid)
        }

    private val imagesDir: File
        get() {
            val imagesDir = File(context.filesDir, LOCAL_PATH_IMAGES)
            if (!imagesDir.exists()) {
                imagesDir.mkdirs()
            }
            return imagesDir
        }

    override fun merge(): Flow<ImageMergeStatus> {
        return flow<ImageMergeStatus> {
            Timber.d("ImageRepositoryImpl getFileList start ${imagesRef?.path}")
            val serverFileNames = getServerFileNames()
            emit(ImageMergeStatus.ServerNamesDone)

            val localFileNames = getLocalFileNames()
            emit(ImageMergeStatus.LocalFilesDone)

            val downloadFileNames = serverFileNames - localFileNames
            download(downloadFileNames)
            emit(ImageMergeStatus.DownloadDone)

            val removeFileNames = localFileNames - serverFileNames
            removeFromLocal(removeFileNames)
            emit(ImageMergeStatus.RemoveDone)

            emit(ImageMergeStatus.Done)
        }.onStart {
            emit(ImageMergeStatus.Start)
        }.catch { cause ->
            emit(ImageMergeStatus.Error(cause))
        }.flowOn(dispatcher)
    }

    private suspend fun getServerFileNames(): Set<String> {
        Timber.d("ImageRepositoryImpl getServerFileNames start ${imagesRef?.path}")
        val imagesRef: StorageReference = imagesRef ?: throw Throwable("user unauthorized")

        val files = imagesRef.listAll().await()
        return files.items.map { imageRef ->
            Timber.d("ImageRepositoryImpl getServerFileNames item=${imageRef.name}")
            imageRef.name
        }.toSet()
    }

    private fun getLocalFileNames(): Set<String> {
        return imagesDir.listFiles()?.map { it.name }?.toSet() ?: emptySet()
    }

    private suspend fun download(fileNames: Set<String>) {
        Timber.d("ImageRepositoryImpl download start ${fileNames.joinToString()}")
        val imagesRef: StorageReference = imagesRef ?: throw Throwable("user unauthorized")

        fileNames.forEach { fileName ->
            val localFile = File(imagesDir, fileName)
            localFile.createNewFile()
            imagesRef.child(fileName).getFile(localFile).await()
        }
        Timber.d("ImageRepositoryImpl download done")
    }

    private fun removeFromLocal(fileNames: Set<String>) {
        Timber.d("ImageRepositoryImpl removeFromLocal start ${fileNames.joinToString()}")

        fileNames.forEach { fileName ->
            val localFile = File(imagesDir, fileName)
            if (localFile.exists()) {
                localFile.delete()
            }
        }

        Timber.d("ImageRepositoryImpl removeFromLocal done")
    }
}