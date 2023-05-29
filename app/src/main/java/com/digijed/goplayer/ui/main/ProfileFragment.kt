package com.digijed.goplayer.ui.main

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.digijed.goplayer.R
import com.digijed.goplayer.databinding.FragmentProfileBinding
import com.digijed.goplayer.model.AppUser
import com.digijed.goplayer.repository.UsersRepository
import com.digijed.goplayer.ui.login.AuthActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private var reposUser = UsersRepository()
    private lateinit var currentUser: AppUser
    private var profileImageUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        FirebaseAuth.getInstance().currentUser?.let {
            reposUser.getCurrentUser(it, object : UsersRepository.FetchUserListener {
                override fun onFetchUser(user: AppUser) {
                    currentUser = user
                    showDetails(currentUser, it)

                }
            })
        }

        username_layout.setEndIconOnClickListener {
            updateUserName()
        }
        email_layout.setEndIconOnClickListener {
            updateUserEmail()
        }
        password_layout.setEndIconOnClickListener {
            updateUserPassword()
        }

        profileImageView.setOnClickListener {
            chooseProfileImage()
            FirebaseAuth.getInstance().currentUser?.uid?.let {
                updateProfileImage(it)
            }
        }

        signOutButton.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(context, AuthActivity::class.java))
            activity?.finish()
        }
    }

    private fun updateUserPassword() {
        if (TextUtils.isEmpty(password_edit.text)) {
            password_edit.error = "Поле - Пароль порожнє!!!"
            return
        }
        val newPassword = password_edit.text.toString()
        FirebaseAuth.getInstance().currentUser?.updatePassword(newPassword)
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Password updated successfully
                    updateDatabaseUser()
                } else {
                    Toast.makeText(
                        activity,
                        "Зміна пароля завершена некоректно!",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
    }

    private fun updateUserEmail() {
        if (TextUtils.isEmpty(email_edit.text)) {
            email_edit.error = "Поле - Email порожнє!!!"
            return
        }
        val newEmail = email_edit.text.toString()
        FirebaseAuth.getInstance().currentUser?.updateEmail(newEmail)
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Email updated successfully
                    updateDatabaseUser()
                } else {
                    Toast.makeText(
                        activity,
                        "Зміна email завершена некоректно!",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
    }

    private fun updateUserName() {
        if (TextUtils.isEmpty(username_edit.text)) {
            username_edit.error = "Поле - Ім'я порожнє!!!"
            return
        }
        val requestUserName = UserProfileChangeRequest.Builder()
            .setDisplayName(username_edit.text.toString())
            .build()
        FirebaseAuth.getInstance().currentUser?.updateProfile(requestUserName)
            ?.addOnCompleteListener {
                if (it.isSuccessful) {
                    updateDatabaseUser()
                }
            }
    }

    private fun updateProfileImage(userId: String) {
        val ref: StorageReference =
            FirebaseStorage.getInstance().reference.child("profileImage/${userId}")
        profileImageUri?.let {
            ref.putFile(it).addOnSuccessListener {

                ref.downloadUrl.addOnSuccessListener { uri ->
                    updateFirebaseUserImage(uri)
                    updateDatabaseUser(uri.toString())
                }
            }
        }
    }

    private fun updateDatabaseUser(path: String = currentUser.image ?: "") {
        val user = AppUser(
            uid = FirebaseAuth.getInstance().currentUser!!.uid,
            name = username_edit.text.toString(),
            image = path
        )
        UsersRepository().createUser(user)
    }

    private fun updateFirebaseUserImage(uri: Uri) {
        val imageRequest = UserProfileChangeRequest.Builder()
            .setPhotoUri(uri)
            .build()
        FirebaseAuth.getInstance().currentUser?.updateProfile(imageRequest)
    }

    private fun chooseProfileImage() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        profileImage.launch(intent)
    }

    private var profileImage =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                result.data?.let { uri ->
                    profileImageView.setImageURI(uri.data)
                    profileImageUri = uri.data
                }
            }
        }

    private fun showDetails(currentUser: AppUser, it: FirebaseUser) {
        username_edit.setText(currentUser.name)
        email_edit.setText(it.email)
        currentUser.image?.let {
            if (it.isNotEmpty()) {
                Glide.with(this).load(it)
                    .placeholder(R.drawable.log_user)
                    .into(profileImageView)
            }
        }
    }
}