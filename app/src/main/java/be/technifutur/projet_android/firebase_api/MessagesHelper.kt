package be.technifutur.projet_android.firebase_api

import be.technifutur.projet_android.models.Message
import be.technifutur.projet_android.models.User
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.Query

class MessagesHelper {

    companion object {
        private const val COLLECTION_MESSAGES = "messages"

        // --- GET ---
        fun getAllMessageForRoom(gameId: String, roomUid: String): Query {
            return RoomsHelper.getRoomsCollection(gameId)
                .document(roomUid)
                .collection(COLLECTION_MESSAGES)
                .orderBy("dateCreated")
                .limit(50)
        }

        // --- CREATE ---
        fun createMessageForRoom(textMessage: String, gameId: String, roomUid: String, userSender: User?): Task<DocumentReference> {
            val message = Message(message = textMessage, userSender = userSender)

            return RoomsHelper.getRoomsCollection(gameId)
                .document(roomUid)
                .collection(COLLECTION_MESSAGES)
                .add(message)
        }
    }
}