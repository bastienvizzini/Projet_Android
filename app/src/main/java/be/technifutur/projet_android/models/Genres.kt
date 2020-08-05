package be.technifutur.projet_android.models

import android.os.Parcel
import android.os.Parcelable

class Genres(
    var id: Int,
    var name: String?
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Genres> {
        override fun createFromParcel(parcel: Parcel): Genres {
            return Genres(parcel)
        }

        override fun newArray(size: Int): Array<Genres?> {
            return arrayOfNulls(size)
        }
    }
}