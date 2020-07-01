package be.technifutur.projet_android.models

class Game(title: String, platform: Platform, imageResource: Int, genre: String = "Action") {
    var mTitle: String =  title
    var mGenre: String = genre
    var mPlatform: String = platform.platformName
    var mImageResource: Int = imageResource
}
