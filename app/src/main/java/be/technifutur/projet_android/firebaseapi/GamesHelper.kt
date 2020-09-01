package be.technifutur.projet_android.firebaseapi

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

class GamesHelper {

    companion object {
        private const val COLLECTION_GAMES = "games"

        // --- COLLECTION REFERENCE ---
        fun getGamesCollection(): CollectionReference {
            return FirebaseFirestore.getInstance().collection(COLLECTION_GAMES)
        }
    }
}