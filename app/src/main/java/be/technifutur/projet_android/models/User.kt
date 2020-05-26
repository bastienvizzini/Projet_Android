package be.technifutur.projet_android.models

import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.RequiresApi
import be.technifutur.projet_android.models.Game
import java.io.Serializable

class User(
    userName: String,
    age: Int,
    city: String,
    profilePicture: Int,
    games: MutableList<Game>,
    isOnline: Boolean
) {
    var mUserName: String = userName
    var mAge: Int = age
    var mCity: String = city
    var mProfilePicture = profilePicture
    var mGames = games
    var mIsOnline = isOnline

}