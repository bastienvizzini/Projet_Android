package be.technifutur.projet_android.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    val uid: String = "",
    var username: String = "",
    var urlPicture: String? = ""
): Parcelable {
}