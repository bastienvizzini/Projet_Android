package be.technifutur.projet_android.models

import android.os.Parcelable
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.ServerTimestamp
import kotlinx.android.parcel.Parcelize
import java.util.*
import kotlin.math.max

@Parcelize
data class Room(
    @ServerTimestamp
    val dateCreated: Date? = null,
    val maxUsersInRoom: Int? = null,
    val language: String? = null,
    val gameDuration: String? = null,
    val gameIntensity: String? = null,
    @DocumentId
    val roomUid: String? = null,
    val gameId: Int? = null): Parcelable {
}