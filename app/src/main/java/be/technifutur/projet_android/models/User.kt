package be.technifutur.projet_android.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    val userName: String,
    val age: Int,
    val city: String,
    val profilePicture: Int,
    val games: ArrayList<MyGame>,
    val isOnline: Boolean
):Parcelable {

}