package com.digijed.goplayer.repository

import com.digijed.goplayer.R
import com.digijed.goplayer.model.AppUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class UsersRepository {
    fun createUser(user: AppUser) {
        val database = Firebase.database
        val userRef = database.getReference("users")
        val userNodeRef = userRef.child(user.uid)
        userNodeRef.setValue(user)
    }

    interface FetchUserListener{
        fun onFetchUser(user: AppUser)
    }

    fun getCurrentUser(firebaseUser: FirebaseUser, listener: FetchUserListener){
        val database = Firebase.database

        val userRef = database.getReference("users")
        userRef.child(firebaseUser.uid).addListenerForSingleValueEvent(
            object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    val user = snapshot.getValue<AppUser>()
                        ?: mapFirebaseUser(FirebaseAuth.getInstance().currentUser!!)
                    listener.onFetchUser(user)
                }

                override fun onCancelled(error: DatabaseError) {
                    val user = mapFirebaseUser(FirebaseAuth.getInstance().currentUser!!)
                    listener.onFetchUser(user)
                }
            }
        )
    }
}

fun mapFirebaseUser(user: FirebaseUser) =
    AppUser(
        user.uid,
        user.displayName ?: R.string.unknown_user.toString(),
        user.photoUrl.toString()
    )