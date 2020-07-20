package be.technifutur.projet_android.models

import com.google.gson.annotations.SerializedName

class Game(
    var id: Int,
    var name: String,
    @SerializedName("background_image")
    var posterPath: String,
    var genres: Array<Genres>
) {

}