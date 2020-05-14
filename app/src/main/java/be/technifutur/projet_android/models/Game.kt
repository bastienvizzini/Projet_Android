package be.technifutur.projet_android.models

class Game(title: String, platform: Platform, imageResource: Int) {
    var mTitle: String =  title
    var mPlatform: String = platform.platformName
    var mImageResource: Int = imageResource
}
