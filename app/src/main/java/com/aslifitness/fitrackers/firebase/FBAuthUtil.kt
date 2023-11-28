package com.aslifitness.fitrackers.firebase

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

/**
 * @author Shubham Pandey
 */

object FBAuthUtil {

    fun isUserAuthenticated() = Firebase.auth.currentUser != null

    fun getFirebaseAuth() = Firebase.auth
}