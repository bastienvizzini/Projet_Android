package be.technifutur.projet_android.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MyGame(val title: String, val platform: Platform, val imageResource: Int, val genre: String = "Action"): Parcelable {
}
