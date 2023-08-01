package com.jozu.compose.planfun.infra.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.snapshots
import com.google.firebase.firestore.ktx.toObject
import com.jozu.compose.planfun.domain.Spot
import com.jozu.compose.planfun.domain.SpotChange
import com.jozu.compose.planfun.domain.SpotFuture
import com.jozu.compose.planfun.domain.SpotRepository
import com.jozu.compose.planfun.infra.firebase.model.FirestoreSpot
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.tasks.await
import timber.log.Timber
import kotlin.coroutines.CoroutineContext

/**
 *
 * Created by jozuko on 2023/08/01.
 * Copyright (c) 2023 Studio Jozu. All rights reserved.
 */
class SpotRepositoryImpl(
    private val coroutineContext: CoroutineContext,
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
) : SpotRepository {
    companion object {
        private const val COLLECTION_NAME_USERS = "users"
        private const val COLLECTION_NAME_SPOT_LIST = "spot_list"
    }

    private val spotListRef: CollectionReference?
        get() = auth.currentUser?.let { user ->
            firestore
                .collection(COLLECTION_NAME_USERS)
                .document(user.uid)
                .collection(COLLECTION_NAME_SPOT_LIST)
        }

    override val spotListFlow: Flow<List<SpotChange>>
        get() = callbackFlow {
            val listener: EventListener<QuerySnapshot> = EventListener<QuerySnapshot> { snapshotQuery, error ->
                if (error != null) {
                    Timber.e("FirestoreSnapshotListener(Spot) ${error.localizedMessage}")
                } else {
                    try {
                        snapshotQuery?.documentChanges?.mapNotNull { documentChange ->
                            val firestoreSpot = documentChange.document.toObject<FirestoreSpot>()
                            val spot = firestoreSpot.toDomain() ?: return@mapNotNull null
                            val type = when (documentChange.type) {
                                DocumentChange.Type.ADDED -> SpotChange.Type.ADDED
                                DocumentChange.Type.MODIFIED -> SpotChange.Type.MODIFIED
                                DocumentChange.Type.REMOVED -> SpotChange.Type.REMOVED
                            }
                            Timber.d("FirestoreSnapshotListener(Spot) $type ${firestoreSpot.id}")
                            SpotChange(type, spot)
                        }?.let { trySend(it) }
                    } catch (error: Exception) {
                        Timber.e("FirestoreSnapshotListener(Spot) ${error.localizedMessage}")
                    }
                }
            }

            val spotListRef = this@SpotRepositoryImpl.spotListRef ?: return@callbackFlow
            val registrationListener = spotListRef.addSnapshotListener(listener)
            awaitClose { registrationListener.remove() }
        }

    override fun getAll(): Flow<SpotFuture<List<Spot>>> {
        val spotListRef = this.spotListRef ?: return flowOf(SpotFuture.Error(Throwable("user unauthorized")))

        return spotListRef
            .snapshots()
            .map { querySnapshot: QuerySnapshot ->
                val firestoreSpotList = querySnapshot.documents.map { documentSnapshot ->
                    documentSnapshot.toObject<FirestoreSpot>()
                }
                @Suppress("USELESS_CAST")
                SpotFuture.Success(
                    firestoreSpotList.mapNotNull {
                        it?.toDomain()
                    }
                ) as SpotFuture<List<Spot>>
            }
            .catch { cause ->
                emit(SpotFuture.Error(cause))
            }
            .flowOn(coroutineContext)
    }

    override fun add(spot: Spot): Flow<SpotFuture<Spot>> {
        val spotListRef = this.spotListRef ?: return flowOf(SpotFuture.Error(Throwable("user unauthorized")))

        return flow<SpotFuture<Spot>> {
            val docRef = spotListRef
                .add(FirestoreSpot.fromSpot(spot))
                .addOnFailureListener {
                    throw it
                }
                .await()

            Timber.d("<SpotRepository>add ${docRef.id}")
            emit(SpotFuture.Success(spot.copy(id = docRef.id)))
        }.onStart {
            emit(SpotFuture.Proceeding)
        }.catch { cause ->
            emit(SpotFuture.Error(cause))
        }.flowOn(coroutineContext)
    }
}