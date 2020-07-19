package be.technifutur.projet_android.models

class User(
    userName: String,
    age: Int,
    city: String,
    profilePicture: Int,
    games: MutableList<MyGame>,
    isOnline: Boolean
) {
    var mUserName: String = userName
    var mAge: Int = age
    var mCity: String = city
    var mProfilePicture = profilePicture
    var mGames = games
    var mIsOnline = isOnline

}