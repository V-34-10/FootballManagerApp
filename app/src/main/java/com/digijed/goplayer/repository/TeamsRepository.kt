package com.digijed.goplayer.repository

import com.digijed.goplayer.R
import com.digijed.goplayer.model.Teams
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class TeamsRepository {
    fun createTeam(team: Teams) {
        val database = Firebase.database
        val userRef = database.getReference("teams")
        val userNodeRef = userRef.child(team.uid)
        userNodeRef.setValue(team)
    }

    fun getTeams() = FirebaseDatabase.getInstance().getReference("teams").limitToLast(10)
}

fun mapFirebaseTeam(team: FirebaseUser) =
    Teams(
        team.uid,
        team.displayName ?: R.string.unknown_team.toString(),
        team.displayName.toString(),
        team.displayName.toString(),
        team.displayName.toString(),
        team.photoUrl.toString()
    )