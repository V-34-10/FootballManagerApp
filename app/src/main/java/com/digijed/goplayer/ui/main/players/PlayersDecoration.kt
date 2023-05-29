package com.digijed.goplayer.ui.main.players

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.digijed.goplayer.R
import com.digijed.goplayer.ui.main.players.PlayersFragment

class PlayersDecoration(context: PlayersFragment) : RecyclerView.ItemDecoration() {

    private val mPadding5 = context.resources.getDimensionPixelSize(R.dimen.dp5)
    private val mPadding10 = context.resources.getDimensionPixelSize(R.dimen.dp10)

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val itemPosition = parent.getChildAdapterPosition(view)
        if (itemPosition == RecyclerView.NO_POSITION) {
            return
        }
        outRect.left = mPadding10
        outRect.right = mPadding5
        when (itemPosition) {
            0 -> outRect.bottom = mPadding10
            parent.adapter?.itemCount?.dec() -> outRect.top = mPadding10
            else -> {
                outRect.top = mPadding10
                outRect.bottom = mPadding10
            }
        }
    }
}
