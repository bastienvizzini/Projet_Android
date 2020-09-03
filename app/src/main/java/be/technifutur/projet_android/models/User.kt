package be.technifutur.projet_android.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    val uid: String? = null,
    var username: String? = null,
    var urlPicture: String? = null
): Parcelable {
}