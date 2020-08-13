package be.technifutur.projet_android.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Room(var users: ArrayList<User>, val maxUsersInRoom: Int, val language: String, val gameDuration: String, val gameIntensity: String): Parcelable {
}