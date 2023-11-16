package com.example.projeto.activity.classes

import android.os.Parcel
import android.os.Parcelable

data class Lancamento(
    val datavenc: String,
    val linhadig: String,
    val valor: String,
    val status: String,
    val id: String
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(datavenc)
        parcel.writeString(linhadig)
        parcel.writeString(valor)
        parcel.writeString(status)
        parcel.writeString(id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Lancamento> {
        override fun createFromParcel(parcel: Parcel): Lancamento {
            return Lancamento(parcel)
        }

        override fun newArray(size: Int): Array<Lancamento?> {
            return arrayOfNulls(size)
        }
    }
}
