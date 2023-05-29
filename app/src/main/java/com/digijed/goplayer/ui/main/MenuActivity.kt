package com.digijed.goplayer.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.digijed.goplayer.R
import com.digijed.goplayer.ui.main.players.PlayersFragment
import com.digijed.goplayer.ui.main.teams.TeamsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        val navigation = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        navigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        loadFragment(TeamsFragment())

    }

    private val onNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.teamsFragment -> {
                    loadFragment(TeamsFragment())
                    return@OnNavigationItemSelectedListener true
                }
                R.id.playersFragment -> {
                    loadFragment(PlayersFragment())
                    return@OnNavigationItemSelectedListener true
                }
                R.id.profileFragment -> {
                    loadFragment(ProfileFragment())
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }

    private fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentMenu, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}