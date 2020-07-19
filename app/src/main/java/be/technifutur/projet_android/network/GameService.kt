package be.technifutur.projet_android.network

import be.technifutur.projet_android.models.GameResult
import retrofit2.Call
import retrofit2.http.GET

interface GameService {
    @GET("games?dates=2019-01-01,2019-12-31&ordering=-added")
    fun mostPopularGames(): Call<GameResult>
}