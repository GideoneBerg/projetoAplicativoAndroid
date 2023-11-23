package com.example.projeto.activity.classes

import android.os.Parcelable
import android.text.Editable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Pix (
    val qrcode: String? = null
) :Parcelable