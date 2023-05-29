package com.digijed.goplayer.ui.login

import android.content.Context
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseAuth
import com.digijed.goplayer.R
import com.digijed.goplayer.repository.UsersRepository
import com.digijed.goplayer.repository.mapFirebaseUser

class AuthFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        runLogin()
    }

    private var userAuthStateListener: OnAuthListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if(context is OnAuthListener)
            userAuthStateListener = context
    }

    private val provider = arrayListOf(
        AuthUI.IdpConfig.EmailBuilder().build(),
        AuthUI.IdpConfig.GoogleBuilder().build()
    )

    private fun runLogin() {
        val signIntent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(provider)
            .setLogo(R.drawable.logo)
            .setIsSmartLockEnabled(false)
            .setTosAndPrivacyPolicyUrls(
                "https://t.me/vIaDe0", "https://www.dnu.dp.ua"
            )
            .build()

        signLauncher.launch(signIntent)
    }

    private val signLauncher = registerForActivityResult(
        FirebaseAuthUIActivityResultContract()
    ) {
        this.onSignResult(it)
    }

    private fun onSignResult(it: FirebaseAuthUIAuthenticationResult) {
        if (it.resultCode == AppCompatActivity.RESULT_OK) {
            FirebaseAuth.getInstance().currentUser?.also {

                UsersRepository().createUser(mapFirebaseUser(it))
                //auth correct
                userAuthStateListener?.onAuthStateChange()

            } ?: Toast.makeText(requireContext(), "Auth error", Toast.LENGTH_SHORT).show()
        } else {
            if (it.idpResponse == null) {
                requireActivity().finish()
            } else {
                Toast.makeText(requireContext(), "Auth error", Toast.LENGTH_SHORT).show()
            }
        }
    }
}