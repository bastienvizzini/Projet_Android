package be.technifutur.projet_android.firebaseapi

import be.technifutur.projet_android.models.Game
import be.technifutur.projet_android.models.User
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.*


class UserHelper {

    companion object {
        private const val COLLECTION_USERS = "users"
        private const val COLLECTION_GAMES = "games"

        // --- COLLECTION REFERENCE ---
        private fun getUsersCollection(): CollectionReference {
            return FirebaseFirestore.getInstance().collection(COLLECTION_USERS)
        }

        private fun getUserGamesCollection(gameUid: String): CollectionReference {
            return getUsersCollection().document(gameUid).collection(COLLECTION_GAMES)
        }

        // --- CREATE ---
        fun createUser(uid: String, username: String, urlPicture: String?): Task<Void> {
            val userToCreate = User(uid, username, urlPicture)
            return getUsersCollection().document(uid).set(userToCreate)
        }

        fun addGame(game: Game, uid: String): Task<Void> {
            val data = hashMapOf(
                "id" to game.id,
                "name" to game.name,
                "posterPath" to game.posterPath
            )
            return getUserGamesCollection(uid).document(game.id.toString()).set(data)
        }

        // --- GET ---

        fun getUser(uid: String): Task<DocumentSnapshot> {
            return getUsersCollection().document(uid).get()
        }

        fun getGame(uid: String, gameUid: String): Task<DocumentSnapshot> {
            return getUserGamesCollection(uid).document(gameUid).get()
        }

        // --- UPDATE ---

        fun updateUsername(username: String, uid: String): Task<Void> {
            return getUsersCollection().document(uid).update("username", username)
        }

        // --- DELETE ---
        fun deleteUser(uid: String): Task<Void> {
            return getUsersCollection().document(uid).delete()
        }
    }
}