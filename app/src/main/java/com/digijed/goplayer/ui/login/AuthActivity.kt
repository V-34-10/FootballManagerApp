package com.digijed.goplayer.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.digijed.goplayer.R
import com.digijed.goplayer.ui.main.MenuActivity
import com.google.firebase.auth.FirebaseAuth
import com.digijed.goplayer.utils.replaceFragment

class AuthActivity : AppCompatActivity(), OnAuthListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        showFragment()
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount == 0) {
            finish()
        } else {
            super.onBackPressed()
        }
    }

    private fun showFragment() {
        if (FirebaseAuth.getInstance().currentUser == null) {
            replaceFragment(R.id.fragment_container_view_tag, AuthFragment())
        } else {
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onAuthStateChange() {
        showFragment()
    }
}