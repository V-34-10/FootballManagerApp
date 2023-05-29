package com.digijed.goplayer.repository

import com.digijed.goplayer.R
import com.digijed.goplayer.model.Players
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class PlayersRepository {
    fun createPlayer(player: Players) {
        val database = Firebase.database
        val userRef = database.getReference("players")
        val userNodeRef = userRef.child(player.uid)
        userNodeRef.setValue(player)
    }

    fun getPlayers() = FirebaseDatabase.getInstance().getReference("players").limitToLast(50)
    fun getPlayersTeams(teamId: String? = null): Query {
        val playersRef = FirebaseDatabase.getInstance().getReference("players")
        if (teamId != null) {
            // Filter the players by teamId
            return playersRef.orderByChild("idTeam").equalTo(teamId)
        }
        // Return all players
        return playersRef.limitToLast(11)
    }
}

fun mapFirebasePlayer(player: FirebaseUser) =
    Players(
        player.uid,
        player.uid,
        player.displayName ?: R.string.unknown_player.toString(),
        player.displayName.toString(),
        player.displayName.toString(),
        player.displayName.toString(),
        player.photoUrl.toString()
    )