package be.technifutur.projet_android.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import be.technifutur.projet_android.MainActivity
import be.technifutur.projet_android.R
import be.technifutur.projet_android.adapters.GameAdapter
import be.technifutur.projet_android.models.Game
import be.technifutur.projet_android.models.GameResult
import be.technifutur.projet_android.models.GamesResult
import be.technifutur.projet_android.network.GameService
import kotlinx.android.synthetic.main.fragment_explore.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * A simple [Fragment] subclass.
 */
class ExploreFragment : Fragment() {

    private lateinit var gameService: GameService
    private lateinit var mContext: Context
    private var mYourGamesList = arrayListOf<Game>()
    private var mRecommendedGamesList = arrayListOf<Game>()
    private var mRecommendedGamesArray = arrayOf(47137, 23598, 22509, 290856, 10142, 50781, 10213, 19301, 32, 3498)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_explore, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.context?.let { context ->
            mContext = context
        }
        this.setRecyclerViews()

        val retrofit = Retrofit.Builder()
            .baseUrl(MainActivity.BASE_URL_RAWG)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        gameService = retrofit.create(GameService::class.java)

        if (savedInstanceState != null) {
            savedInstanceState.getParcelableArrayList<Game>("YOUR_GAMES")?.let { yourGamesList ->
                mYourGamesList.addAll(yourGamesList)
            }
            savedInstanceState.getParcelableArrayList<Game>("RECOMMENDED_GAMES")?.let { recommendedGamesList ->
                mRecommendedGamesList.addAll(recommendedGamesList)
            }
            this.getYourGames(gameService, mContext)
            this.getRecommendedGames(gameService, mContext)
        } else {
            this.getYourGames(gameService, mContext)
            this.getRecommendedGames(gameService, mContext)
        }
    }

    private fun setRecyclerViews() {
        val layoutManagerOne = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val layoutManagerTwo = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val adapterOne = GameAdapter(mContext, mYourGamesList)
        val adapterTwo = GameAdapter(mContext, mRecommendedGamesList)
        yourGamesRecyclerView.layoutManager = layoutManagerOne
        yourGamesRecyclerView.adapter = adapterOne
        recommendedGamesRecyclerView.layoutManager = layoutManagerTwo
        recommendedGamesRecyclerView.adapter = adapterTwo
    }

    private fun getYourGames(gameService: GameService, context: Context) {
        if (mYourGamesList.isEmpty()) {

            yourGamesRecyclerView.visibility = View.GONE

            gameService.mostPopularGames().enqueue(object : Callback<GamesResult>{
                override fun onFailure(call: Call<GamesResult>, t: Throwable) {
                    Log.d("GrosProbleme", t.message)
                }

                override fun onResponse(call: Call<GamesResult>, response: Response<GamesResult>) {
                    response.body()?.results?.forEach { game ->
                        mYourGamesList.add(game)
                    }

                    if (yourGamesRecyclerView != null) {
                        yourGamesRecyclerView.adapter!!.notifyDataSetChanged()
                        yourGamesRecyclerView.visibility = View.VISIBLE
                    }
                }
            })
        }
    }

    private fun getRecommendedGames(gameService: GameService, context: Context) {
        if (mRecommendedGamesList.isEmpty()) {

            recommendedGamesRecyclerView.visibility = View.GONE

            mRecommendedGamesArray.forEach { gameId ->
                gameService.gameDetailsPerId(gameId).enqueue(object: Callback<Game>{
                    override fun onFailure(call: Call<Game>, t: Throwable) {
                        Log.d("GrosProbleme", t.message)
                    }

                    override fun onResponse(call: Call<Game>, response: Response<Game>) {
                        response.body()?.let { game ->
                            mRecommendedGamesList.add(game)
                        }

                        if (recommendedGamesRecyclerView != null && gameId == mRecommendedGamesArray.last()) {
                            recommendedGamesRecyclerView.adapter!!.notifyDataSetChanged()
                            recommendedGamesRecyclerView.visibility = View.VISIBLE
                        }
                    }
                })
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList("YOUR_GAMES", mYourGamesList)
        outState.putParcelableArrayList("RECOMMENDED_GAMES", mRecommendedGamesList)
    }

}
