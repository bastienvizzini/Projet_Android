package be.technifutur.projet_android.models

import android.os.Parcelable
import com.google.firebase.firestore.ServerTimestamp
import kotlinx.android.parcel.Parcelize
import java.util.*
import kotlin.math.max

@Parcelize
data class Room(
    @ServerTimestamp
    val dateCreated: Date? = null,
    val maxUsersInRoom: Int?,
    val language: String?,
    val gameDuration: String?,
    val gameIntensity: String?): Parcelable {
}