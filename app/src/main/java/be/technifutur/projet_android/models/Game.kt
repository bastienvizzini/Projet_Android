package be.technifutur.projet_android.models

class Game(title: String, platform: Platform) {
    var mTitle: String =  title
    var mPlatform: String = platform.platformName
}
