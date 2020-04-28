package com.xiaoge.org.base_test

import android.os.Parcel
import android.os.Parcelable

class Stu() : Parcelable {
    private var name: String? = ""

    constructor(parcel: Parcel) : this() {
        name = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Stu> {
        override fun createFromParcel(parcel: Parcel): Stu {
            return Stu(parcel)
        }

        override fun newArray(size: Int): Array<Stu?> {
            return arrayOfNulls(size)
        }
    }
}