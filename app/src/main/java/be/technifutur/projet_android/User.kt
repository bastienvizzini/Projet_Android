package be.technifutur.projet_android

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
}