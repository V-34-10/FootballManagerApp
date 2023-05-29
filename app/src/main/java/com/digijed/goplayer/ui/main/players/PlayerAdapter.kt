package com.digijed.goplayer.ui.main.players

import android.view.LayoutInflater
import android.view.ViewGroup
import com.digijed.goplayer.R
import com.digijed.goplayer.model.Players
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions

class PlayerAdapter(
    private val options: FirebaseRecyclerOptions<Players>,
    private val onItemClickListener: OnItemClickListener?
) : FirebaseRecyclerAdapter<Players, PlayerViewHolder>(options) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PlayerViewHolder {
        val holder = PlayerViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.view_holder_player,
                parent,
                false
            )
        )

        holder.itemView.setOnClickListener {
            onItemClickListener?.onItemClick(
                options.snapshots[holder.absoluteAdapterPosition]
            )
        }

        return holder
    }

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int, model: Players) {
        holder.bind(model)
    }

    override fun onDataChanged() {}

    interface OnItemClickListener {
        fun onItemClick(player: Players)
    }
}