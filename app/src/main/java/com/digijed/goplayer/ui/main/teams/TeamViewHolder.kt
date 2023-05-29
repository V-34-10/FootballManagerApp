package com.digijed.goplayer.ui.main.teams

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.digijed.goplayer.R
import com.digijed.goplayer.model.Teams
import kotlinx.android.synthetic.main.custom_team_view.view.*

class TeamViewHolder(
    itemView: View
) : RecyclerView.ViewHolder(itemView) {

    fun bind(team: Teams) = with(itemView) {
        nameTeam.text = team.name
        stadiumTeam.text = team.stadium
        coachTeam.text = team.coach
        leagueTeam.text = team.league
        team.image?.let { url ->
            if (url.isNotEmpty()) {
                Glide.with(context).load(url)
                    .placeholder(R.drawable.logusershahtar)
                    .into(ivTeam)
            }
        }
    }
}