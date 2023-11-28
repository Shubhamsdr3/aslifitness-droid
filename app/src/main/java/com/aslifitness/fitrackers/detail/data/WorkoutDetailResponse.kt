package com.aslifitness.fitrackers.detail.data

/**
 * @author Shubham Pandey
 */
import android.os.Parcelable
import androidx.annotation.Keep
import com.aslifitness.fitrackers.model.CardItem
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class WorkoutDetailResponse(val header: CardItem? = null, val workoutList: List<Workout>? = null, val history: WorkoutHistory? = null): Parcelable