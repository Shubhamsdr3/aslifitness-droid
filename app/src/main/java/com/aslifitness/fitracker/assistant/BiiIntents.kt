package com.aslifitness.fitracker.assistant

object BiiIntents {

    const val START_EXERCISE = "startExercise"
    const val STOP_EXERCISE = "stopExercise"

    const val EXERCISE_TYPE = "exerciseType"
    const val EXERCISE_NAME = "exerciseName"

    const val ADD_WORKOUT_INTENT = "aslifitness.actions.intent.ADD_WORKOUT"
    const val ADD_ROUTINE_INTENT = "aslifitness.actions.intent.ADD_ROUTINE"

    object Actions {
        const val ACTION_TOKEN_EXTRA = "actions.fulfillment.extra.ACTION_TOKEN"
    }
}