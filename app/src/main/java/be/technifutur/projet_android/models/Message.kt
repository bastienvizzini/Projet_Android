package be.technifutur.projet_android.models

import android.os.Parcelable
import com.google.firebase.firestore.ServerTimestamp
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Message(
    val message: String? = null,
    @ServerTimestamp
    val dateCreated: Date? = null,
    val userSender: User? = null,
    val urlImage: String? = null
): Parcelable {
}