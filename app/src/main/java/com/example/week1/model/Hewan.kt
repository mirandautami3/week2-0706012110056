package com.example.week1.model

import android.os.Parcel
import android.os.Parcelable

data class Hewan(
    var nama:String?,
    var usia:Int?,
    open var imageUri: String?

) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),

        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString()
    ) {
    }


    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(nama)
       // parcel.writeString(jenis)
        usia?.let { parcel.writeInt(it) }
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Hewan> {
        override fun createFromParcel(parcel: Parcel): Hewan {
            return Hewan(parcel)
        }

        override fun newArray(size: Int): Array<Hewan?> {
            return arrayOfNulls(size)
        }
    }
}