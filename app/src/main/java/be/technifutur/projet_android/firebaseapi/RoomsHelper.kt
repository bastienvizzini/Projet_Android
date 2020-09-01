package be.technifutur.projet_android.firebaseapi

import be.technifutur.projet_android.models.Room
import be.technifutur.projet_android.models.User
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class RoomsHelper {

    companion object {
        private const val COLLECTION_ROOMS = "rooms"
        private const val COLLECTION_GAMES = "games"

        // --- COLLECTION REFERENCE ---
        fun getGamesCollection(): CollectionReference {
            return FirebaseFirestore.getInstance().collection(COLLECTION_GAMES)
        }

        fun getRoomsCollection(id: String): CollectionReference {
            return getGamesCollection().document(id).collection(COLLECTION_ROOMS)
        }

        // --- GET ---
        fun getAllRoomsForGame(id: String): Query? {
            return RoomsHelper.getRoomsCollection(id)
                .orderBy("dateCreated")
        }

        // --- CREATE ---
        fun createRoom(id: Int, users: Int, lang: String, duration: String, intensity: String): Task<Void> {
            val roomToCreate = Room(maxUsersInRoom = users, language = lang, gameDuration = duration, gameIntensity = intensity)
            return getRoomsCollection(id.toString()).document().set(roomToCreate)
        }
    }
}