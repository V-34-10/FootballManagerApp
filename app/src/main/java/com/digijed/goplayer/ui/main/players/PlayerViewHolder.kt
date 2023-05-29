package com.digijed.goplayer.ui.main.players

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.digijed.goplayer.R
import com.digijed.goplayer.model.Players
import kotlinx.android.synthetic.main.view_holder_player.view.*

class PlayerViewHolder(
    itemView: View
) : RecyclerView.ViewHolder(itemView) {

    fun bind(player: Players) = with(itemView) {
        firstName.text = player.firstName
        secondName.text = player.secondName
        player.image?.let { url ->
            if (url.isNotEmpty()) {
                Glide.with(context).load(url)
                    .placeholder(R.drawable.log_user)
                    .into(ivPlayer)
            }
        }
    }
}