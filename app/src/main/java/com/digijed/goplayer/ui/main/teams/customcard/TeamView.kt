package com.digijed.goplayer.ui.main.teams.customcard

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.View
import androidx.annotation.ColorInt
import androidx.constraintlayout.widget.ConstraintLayout
import com.digijed.goplayer.R
import kotlinx.android.synthetic.main.custom_team_view.view.*

class TeamView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var selectedColor = -1
    private var unSelectedColor = -1

    init {
        View.inflate(context, R.layout.custom_team_view, this)
        context.obtainStyledAttributes(
            attrs,
            R.styleable.TeamView
        ).use {
            selectedColor = it.getColor(R.styleable.TeamView_TV_selectedColor, -1)
            unSelectedColor = it.getColor(R.styleable.TeamView_TV_unSelectedColor, -1)
            nameTeam.text = it.getText(R.styleable.TeamView_TV_nameTeam)
            stadiumTeam.text = it.getText(R.styleable.TeamView_TV_stadiumTeam)
            coachTeam.text = it.getText(R.styleable.TeamView_TV_coachTeam)
            leagueTeam.text = it.getText(R.styleable.TeamView_TV_leagueTeam)
            ivTeam.setImageDrawable(it.getDrawable(R.styleable.TeamView_TV_imgTeam))
            root.setBackground(unSelectedColor)
        }
        setOnClickListener()
    }

    private fun setOnClickListener() {
        ivTeam.setOnClickListener {
            val selectedColor = context.getColor(R.color.yellow)
            val unSelectedColor = context.getColor(R.color.black)
            val newBackgroundColor = if (isClickable) selectedColor else unSelectedColor
            setBackground(newBackgroundColor)
        }
    }

    private fun View.setBackground(@ColorInt color: Int) {
        val drawable = background
        if (drawable is GradientDrawable) {
            drawable.apply {
                cornerRadius = resources.getDimension(R.dimen.dp20)
                setStroke(resources.getDimensionPixelSize(R.dimen.dp2), color)
            }
        }
    }
}