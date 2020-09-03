package be.technifutur.projet_android.models

import android.os.Parcelable
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ServerTimestamp
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue
import java.util.*

@Parcelize
data class Message(
    val message: String? = null,
    @ServerTimestamp
    val dateCreated: Date? = null,
    val userSenderUid: String? = null,
    val urlImage: String? = null
): Parcelable {
}