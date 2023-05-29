package com.digijed.goplayer.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Players(
    val uid: String = "",
    val idTeam: String? = "",
    val firstName: String = "",
    val secondName: String = "",
    val position: String = "",
    val number: String = "",
    var image: String? = ""
) : Parcelable