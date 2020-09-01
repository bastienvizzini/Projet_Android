package be.technifutur.projet_android.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MyRoom(
    var users: ArrayList<MyUser>,
    val maxUsersInRoom: Int,
    val language: String,
    val gameDuration: String,
    val gameIntensity: String): Parcelable {
}