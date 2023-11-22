package com.example.projeto.activity.classes

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Pix (
    val qrcode: String? = null
) :Parcelable