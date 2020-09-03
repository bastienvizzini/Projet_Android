package be.technifutur.projet_android.firebase_api

import be.technifutur.projet_android.models.Room
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class RoomsHelper {

    companion object {
        private const val COLLECTION_ROOMS = "rooms"
        private const val COLLECTION_GAMES = "games"

        // --- COLLECTION REFERENCE ---
        private fun getGamesCollection(): CollectionReference {
            return FirebaseFirestore.getInstance().collection(COLLECTION_GAMES)
        }

        fun getRoomsCollection(gameId: Int): CollectionReference {
            return getGamesCollection().document(gameId.toString()).collection(COLLECTION_ROOMS)
        }

        // --- GET ---
        fun getAllRoomsForGame(gameId: Int): Query {
            return RoomsHelper.getRoomsCollection(gameId)
                .orderBy("dateCreated")
        }

        // --- CREATE ---
        fun createRoom(gameId: Int, users: Int, lang: String, duration: String, intensity: String, gameID: Int): Task<Void> {
            val roomToCreate = Room(maxUsersInRoom = users, language = lang, gameDuration = duration, gameIntensity = intensity, gameId = gameID)
            return getRoomsCollection(gameId).document().set(roomToCreate)
        }
    }
}