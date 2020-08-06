package be.technifutur.projet_android.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Game (
    val id: Int,
    val name: String?,
    @SerializedName("background_image")
    val posterPath: String?,
    val genres: ArrayList<Genres>?
) : Parcelable {
}