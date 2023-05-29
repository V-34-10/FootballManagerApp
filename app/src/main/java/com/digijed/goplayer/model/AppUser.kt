package com.digijed.goplayer.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AppUser(
    val uid: String = "",
    val name: String = "",
    var image: String? = ""
) : Parcelable