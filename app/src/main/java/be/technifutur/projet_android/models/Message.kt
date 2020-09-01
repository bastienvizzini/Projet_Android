package be.technifutur.projet_android.models

import android.os.Parcelable
import com.google.firebase.firestore.ServerTimestamp
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Message(
    val message: String,
    @ServerTimestamp
    val dateCreated: Date,
    val userSender: User,
    val urlImage: String
): Parcelable {
}