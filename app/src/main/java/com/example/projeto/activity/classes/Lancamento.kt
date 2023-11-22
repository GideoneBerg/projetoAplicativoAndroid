package com.example.projeto.activity.classes

import android.os.Parcelable

import kotlinx.android.parcel.Parcelize

@Parcelize
data class Lancamento(
    val datavenc: String? = null,
    val linhadig: String? = null,
    val valor: String? = null,
    val status: String? = null,
    val titulo: String? = null,
    val uuid_lanc: String? = null

) : Parcelable

