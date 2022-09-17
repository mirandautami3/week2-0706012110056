package com.example.week1.model


import android.os.Parcel
import android.os.Parcelable

data class animal(
    var title:String?,
    var rating:Int?,
    var genre:String?,
    var company:String?,
    var synopsis:String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }
    var imageUri: String = ""

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeValue(rating)
        parcel.writeString(genre)
        parcel.writeString(company)
        parcel.writeString(synopsis)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<animal> {
        override fun createFromParcel(parcel: Parcel): animal {
            return animal(parcel)
        }

        override fun newArray(size: Int): Array<animal?> {
            return arrayOfNulls(size)
        }
    }
}