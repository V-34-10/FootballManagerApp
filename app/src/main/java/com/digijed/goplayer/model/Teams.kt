package com.digijed.goplayer.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Teams (
    val uid: String = "",
    val name: String = "",
    val stadium: String = "",
    val coach: String = "",
    val league: String = "",
    var image: String? = ""
) : Parcelable