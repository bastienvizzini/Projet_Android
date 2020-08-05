package be.technifutur.projet_android.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

class Game (
    var id: Int,
    var name: String?,
    @SerializedName("background_image")
    var posterPath: String?,
    var genres: ArrayList<Genres>?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        arrayListOf<Genres>().apply {
            parcel.readList(this as List<Genres>, Genres::class.java.classLoader)
        }
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(posterPath)
        parcel.writeList(genres as List<Genres>)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Game> {
        override fun createFromParcel(parcel: Parcel): Game {
            return Game(parcel)
        }

        override fun newArray(size: Int): Array<Game?> {
            return arrayOfNulls(size)
        }
    }
}