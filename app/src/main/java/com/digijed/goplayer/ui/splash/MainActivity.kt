package com.digijed.goplayer.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import com.digijed.goplayer.ui.login.AuthActivity
import com.digijed.goplayer.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadingNextActivity()
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
    }

    private fun loadingNextActivity() {
        Handler().postDelayed({
            val go = Intent(this@MainActivity, AuthActivity::class.java)
            startActivity(go)
            finish()
        }, 5 * 1000.toLong())
    }
}