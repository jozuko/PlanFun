package com.jozu.compose.planfun.infra.firebase

import com.google.android.gms.maps.model.LatLng
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.snapshots
import com.google.firebase.firestore.ktx.toObject
import com.jozu.compose.planfun.domain.spot.Spot
import com.jozu.compose.planfun.domain.spot.SpotChange
import com.jozu.compose.planfun.domain.spot.SpotRepository
import com.jozu.compose.planfun.domain.spot.SpotStatus
import com.jozu.compose.planfun.infra.firebase.model.FirestoreSpot
import kotlinx.coroutines.CoroutineDispatcher
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
import javax.inject.Inject

/**
 *
 * Created by jozuko on 2023/08/01.
 * Copyright (c) 2023 Studio Jozu. All rights reserved.
 */
class SpotRepositoryImpl @Inject constructor(
    private val dispatcher: CoroutineDispatcher,
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
                            val spot = firestoreSpot.toDomain()
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

    override fun getAll(): Flow<SpotStatus<List<Spot>>> {
        val spotListRef = this.spotListRef ?: return flowOf(SpotStatus.Error(Throwable("user unauthorized")))

        return spotListRef
            .snapshots()
            .map { querySnapshot: QuerySnapshot ->
                val firestoreSpotList = querySnapshot.documents.map { documentSnapshot ->
                    documentSnapshot.toObject<FirestoreSpot>()
                }
                @Suppress("USELESS_CAST")
                SpotStatus.Success(
                    firestoreSpotList.mapNotNull {
                        it?.toDomain()
                    }
                ) as SpotStatus<List<Spot>>
            }
            .catch { cause ->
                emit(SpotStatus.Error(cause))
            }
            .flowOn(dispatcher)
    }

    override fun add(name: String, location: LatLng?, address: String, tel: String, url: String, memo: String, imageName: String): Flow<SpotStatus<String>> {
        val spotListRef = this.spotListRef ?: return flowOf(SpotStatus.Error(Throwable("user unauthorized")))

        return flow<SpotStatus<String>> {
            val docRef = spotListRef
                .add(FirestoreSpot.fromInput(name, location, address, tel, url, memo, imageName))
                .addOnFailureListener {
                    throw it
                }
                .await()

            Timber.d("<SpotRepository>add ${docRef.id}")
            emit(SpotStatus.Success(docRef.id))
        }.onStart {
            emit(SpotStatus.Proceeding)
        }.catch { cause ->
            emit(SpotStatus.Error(cause))
        }.flowOn(dispatcher)
    }
}