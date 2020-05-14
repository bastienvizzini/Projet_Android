package be.technifutur.projet_android.models

import be.technifutur.projet_android.models.Game

class User(
    userName: String,
    age: Int,
    city: String,
    profilePicture: Int,
    games: List<Game>,
    isOnline: Boolean
) {
    var mUserName: String = userName
    var mAge: Int = age
    var mCity: String = city
    var mProfilePicture = profilePicture
    var mGames = games
    var mIsOnline = isOnline

    override fun toString(): String {
        return mUserName
    }
}