package be.technifutur.projet_android.firebaseapi

import com.google.firebase.firestore.Query

class MessagesHelper {

    companion object {
        private const val COLLECTION_MESSAGES = "messages"

        // --- GET ---
        fun getAllMessageForRoom(id: String, room: String): Query? {
            return RoomsHelper.getRoomsCollection(id)
                .document(room)
                .collection(COLLECTION_MESSAGES)
                .orderBy("dateCreated")
                .limit(50)
        }
    }
}