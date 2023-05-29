package com.digijed.goplayer.ui.main.teams

import android.view.LayoutInflater
import android.view.ViewGroup
import com.digijed.goplayer.R
import com.digijed.goplayer.model.Teams
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions

class TeamAdapter(
    private val options: FirebaseRecyclerOptions<Teams>,
    private val onItemClickListener: OnItemClickListener
) : FirebaseRecyclerAdapter<Teams, TeamViewHolder>(options) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TeamViewHolder {
        val holder = TeamViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.custom_team_view,
                parent,
                false
            )
        )

        holder.itemView.setOnClickListener {
            onItemClickListener.onItemClick(
                options.snapshots[holder.absoluteAdapterPosition],
                holder.absoluteAdapterPosition
            )
        }

        return holder
    }

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int, model: Teams) {
        holder.bind(model)
    }

    override fun onDataChanged() {}

    interface OnItemClickListener {
        fun onItemClick(team: Teams, position: Int)
    }
}