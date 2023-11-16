package com.example.projeto.activity.classes

import android.os.Parcel
import android.os.Parcelable

data class Lancamento(
    val datavenc: String? = null,
    val linhadig: String? = null,
    val valor: String? = null,
    val status: String? = null,
//    val titulo: String? = null
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
//        parcel.readString()!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(datavenc)
        parcel.writeString(linhadig)
        parcel.writeString(valor)
        parcel.writeString(status)
//        parcel.writeString(titulo)
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
