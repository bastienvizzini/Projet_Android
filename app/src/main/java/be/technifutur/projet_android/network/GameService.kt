package be.technifutur.projet_android.network

import be.technifutur.projet_android.models.Game
import be.technifutur.projet_android.models.GameResult
import be.technifutur.projet_android.models.GamesResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GameService {
    @GET("games?-added&dates=2019-01-01,2019-12-31&tags=multiplayer")
    fun mostPopularGames(): Call<GamesResult>

    //@GET("games/?api_key=093057996cb9ab50686ac48b4f237e18743eff56&format=json&resources=game")
    //fun mostPopularGames(): Call<GamesResult>

    //https://www.giantbomb.com/api/games/?api_key=093057996cb9ab50686ac48b4f237e18743eff56&format=json

    @GET("games/{id}")
    fun gameDetailsPerId(@Path("id") gameId: Int): Call<Game>

    //@GET("game/{id}/?format=json&api_key=093057996cb9ab50686ac48b4f237e18743eff56")
    //fun gameDetailsPerId(@Path("id") gameId: Int): Call<GameResult>

    @GET("games/{id}/suggested")
    fun similarGames(@Path("id") gameId: Int): Call<GamesResult>

    @GET("games?platforms=4,1,18,7")
    fun gameSearchQuery(@Query("search") gameName: String): Call<GamesResult>

    @GET("games?")
    fun gameByGenre(@Query("genres") genre: String): Call<GamesResult>

    @GET("games?dates=2000-01-01,2020-12-31&page_size=400000")
    fun getGames(): Call<GamesResult>

    //@GET("search/?api_key=093057996cb9ab50686ac48b4f237e18743eff56&format=json&resources=game")
    //fun gameSearchQuery(@Query("query") gameName: String): Call<GamesResult>

    // search by name
    // https://www.giantbomb.com/api/search/?api_key=093057996cb9ab50686ac48b4f237e18743eff56&format=json&query=dofus

    // search by id (path)
    // https://www.giantbomb.com/api/game/22718/?format=json&api_key=093057996cb9ab50686ac48b4f237e18743eff56

    // https://api.rawg.io/api/games?-added&dates=2010-01-01,2019-12-31&tags=multiplayer&page_size=13850
    // https://api.rawg.io/api/games?dates=2000-01-01,2020-12-31&tags=multiplayer
}