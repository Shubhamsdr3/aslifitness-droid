package com.aslifitness.fitracker.model

import androidx.annotation.Keep
import androidx.annotation.StringDef

/**
 * @author Shubham Pandey
 */
@Keep
class CtaActionType {

    @Retention(AnnotationRetention.SOURCE)
    @StringDef(
        DROP_DOWN,
        REDIRECT
    )
    annotation class Type

    companion object {
        const val DROP_DOWN = "DROP_DOWN"
        const val REDIRECT = "REDIRECT"
    }
}
