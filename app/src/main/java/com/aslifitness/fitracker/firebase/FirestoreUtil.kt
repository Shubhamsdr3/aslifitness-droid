package com.aslifitness.fitracker.firebase

import com.aslifitness.fitracker.model.UserDto
import com.aslifitness.fitracker.model.WorkoutDto
import com.aslifitness.fitracker.model.WorkoutSetData
import com.aslifitness.fitracker.network.NetworkState
import com.aslifitness.fitracker.sharedprefs.UserStore
import com.google.firebase.firestore.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.tasks.await
import timber.log.Timber

/**
 * @author Shubham Pandey
 */
object FirestoreUtil {

    private val db: FirebaseFirestore by lazy { Firebase.firestore }
    private val currentUserId = UserStore.getUserId()

    private val currentUser: DocumentReference = db.document("user/$currentUserId")

    private const val WORKOUT_DOC = "workouts"
    private const val USER_DOC = "user"
    private const val WORKOUT_SET_DOC = "workout_set"
    private const val WORKOUT_HISTORY = "history"
    private const val INITIAL_SET = "initial_set"
    private const val FCM_TOKEN = "token"
    private const val TIMESTAMP = "timestamp"

    // collection
    private const val COLLECTION_FCM_TOKEN = "fcmTokens"

    private suspend fun saveFCMToken(token: String) {
        val deviceToken = hashMapOf(
            FCM_TOKEN to token,
            TIMESTAMP to FieldValue.serverTimestamp(),
        )
        Firebase.firestore.collection(COLLECTION_FCM_TOKEN).document(currentUserId).set(deviceToken).await()
    }

    suspend fun getAndStoreRegToken(): String {
        val token = Firebase.messaging.token.await()
        if (UserStore.getFCMToken() != token) {
            saveFCMToken(token)
            UserStore.saveFCMToken(token)
        }
        return token
    }

    fun getCurrentUser(): Flow<UserDto?> {
        return db.collection(USER_DOC)
            .document(currentUserId)
            .getDocDataFlow { doc ->
                return@getDocDataFlow doc?.toObject(UserDto::class.java)
            }
    }

    fun saveUser(userDto: UserDto) = callbackFlow {
        db.collection(USER_DOC)
            .document(currentUserId)
            .set(userDto)
            .addOnSuccessListener {
                trySend(NetworkState.Success(userDto))
            }.addOnFailureListener {
                trySend(NetworkState.Error(it))
            }
    }

    fun saveInitialSet(workoutSetData: WorkoutSetData) = callbackFlow {
        currentUser
            .collection(INITIAL_SET)
            .add(workoutSetData)
            .addOnSuccessListener {
                trySend(NetworkState.Success(workoutSetData))
            }.addOnFailureListener {
                trySend(NetworkState.Error(it))
            }
    }.flowOn(Dispatchers.Default)

    fun saveWorkoutHistory(workoutSetData: WorkoutSetData) = callbackFlow {
        currentUser
            .collection(WORKOUT_HISTORY)
            .document(workoutSetData.date)
            .set(workoutSetData)
            .addOnSuccessListener {
                trySend(NetworkState.Success(workoutSetData))
            }.addOnFailureListener {
                trySend(NetworkState.Error(it))
            }
        awaitClose {}
    }.flowOn(Dispatchers.Default)

    fun getWorkoutSet() = callbackFlow {
        val listener = currentUser
            .collection(WORKOUT_HISTORY)
            .orderBy("date", Query.Direction.DESCENDING)
            .addSnapshotListener { querySnap, error ->
                if (error != null) {
                    trySend(NetworkState.Error(error))
                }
                val workoutList = mutableListOf<WorkoutSetData>()
                querySnap?.documents?.forEach { doc ->
                    val workout = doc.toObject(WorkoutSetData::class.java)
                    workout?.let { workoutList.add(it) }
                }
                trySend(NetworkState.Success(workoutList.take(3)))
            }
        awaitClose {
            listener.remove()
        }
    }

    fun addWorkout(workoutList: List<WorkoutDto>) {
        db.collection(WORKOUT_SET_DOC)
            .document(currentUserId)
            .update(WORKOUT_HISTORY, workoutList)
            .addOnSuccessListener {
                Timber.d("The workout is added with id: $it")
            }.addOnFailureListener {
                Timber.d("Error adding documents: $it")
            }
    }

    fun getWorkouts(): Flow<List<WorkoutDto>> {
        return db.collection(WORKOUT_DOC)
            .getQueryDataFlow { snap ->
                val workoutList = mutableListOf<WorkoutDto>()
                if (snap != null) {
                    for (doc in snap.documents) {
                        val workout = doc.toObject(WorkoutDto::class.java)
                        workout?.let { workoutList.add(it) }
                    }
                }
                return@getQueryDataFlow workoutList
            }
    }

    private fun DocumentReference.getDocSnapshotFlow(): Flow<DocumentSnapshot?> {
        return callbackFlow {
            val listener = addSnapshotListener { document, exception ->
                if (exception != null) {
                    cancel(
                        message = "error fetching collection data at path - $path",
                        cause = exception
                    )
                    return@addSnapshotListener
                }
                trySend(document).isSuccess
            }
            awaitClose {
                Timber.d("cancelling the listener on collection at path - $path")
                listener.remove()
            }
        }
    }

    private fun CollectionReference.getQuerySnapshotFlow(): Flow<QuerySnapshot?> {
        return callbackFlow {
            val listener = addSnapshotListener { querySnapshot, exception ->
                    if (exception != null) {
                        cancel(
                            message = "error fetching collection data at path - $path",
                            cause = exception
                        )
                        return@addSnapshotListener
                    }
                    trySend(querySnapshot).isSuccess
                }
            awaitClose {
                Timber.d("cancelling the listener on collection at path - $path")
                listener.remove()
            }
        }
    }

    private fun <T> CollectionReference.getQueryDataFlow(mapper: (QuerySnapshot?) -> T): Flow<T> {
        return getQuerySnapshotFlow()
            .map {
                return@map mapper(it)
            }
    }

    private fun <T> DocumentReference.getDocDataFlow(mapper: (DocumentSnapshot?) -> T): Flow<T> {
        return getDocSnapshotFlow()
            .map { return@map mapper(it) }
    }
}