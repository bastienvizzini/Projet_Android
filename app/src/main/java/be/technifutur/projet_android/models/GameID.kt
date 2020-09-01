package be.technifutur.projet_android.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GameID(val uid: String = "", val name: String = ""): Parcelable {
}