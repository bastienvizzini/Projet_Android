package be.technifutur.projet_android.network

import be.technifutur.projet_android.models.Game
import be.technifutur.projet_android.models.GameResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GameService {
    @GET("games?-added&dates=2019-01-01,2019-12-31&tags=multiplayer")
    fun mostPopularGames(): Call<GameResult>

    @GET("games/{id}")
    fun gameDetailsPerId(@Path("id") gameId: Int): Call<Game>

    @GET("games/{id}/suggested")
    fun similarGames(@Path("id") gameId: Int): Call<GameResult>

    @GET("games?tags=multiplayer&search=")
    fun gameSearchQuery(@Query("query") gameName: String): Call<GameResult>
}