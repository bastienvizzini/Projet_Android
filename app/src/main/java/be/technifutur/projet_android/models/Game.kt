package be.technifutur.projet_android.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Game (
    var id: Int? = 0,
    var name: String? = "",
    @SerializedName("background_image")
    var posterPath: String? = "",
    var image: Image? = null,
    var genres: ArrayList<Genres>? = null
) : Parcelable {

}