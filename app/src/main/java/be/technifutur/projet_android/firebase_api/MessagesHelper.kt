package be.technifutur.projet_android.firebase_api

import be.technifutur.projet_android.models.Message
import be.technifutur.projet_android.models.User
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class MessagesHelper {

    companion object {
        private const val COLLECTION_MESSAGES = "messages"

        // --- GET ---
        fun getAllMessageForRoom(gameId: Int, roomUid: String): Query {
            return RoomsHelper.getRoomsCollection(gameId)
                .document(roomUid)
                .collection(COLLECTION_MESSAGES)
                .orderBy("dateCreated")
        }

        // --- CREATE ---
        fun createMessageForRoom(textMessage: String, gameId: Int, roomUid: String, userSender: User?): Task<DocumentReference> {
            val message = Message(message = textMessage, userSenderUid = userSender?.uid!!)

            return RoomsHelper.getRoomsCollection(gameId)
                .document(roomUid)
                .collection(COLLECTION_MESSAGES)
                .add(message)
        }
    }
}