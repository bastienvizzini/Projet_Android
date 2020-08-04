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
    private var mYourGamesList = mutableListOf<Game>()
    private var mRecommendedGamesList = mutableListOf<Game>()
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

        mContext = requireContext()

        val retrofit = Retrofit.Builder()
            .baseUrl(MainActivity.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        gameService = retrofit.create(GameService::class.java)

        val layoutManagerOne = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        this.getYourGames(gameService, mContext)
        this.getRecommendedGames(gameService, mContext)

    }

    private fun getYourGames(gameService: GameService, context: Context) {
        val layoutManagerOne = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        if (mYourGamesList.isEmpty()) {

            gameService.mostPopularGames().enqueue(object : Callback<GameResult>{
                override fun onFailure(call: Call<GameResult>, t: Throwable) {
                    Log.d("GrosProbleme", t.message)
                    firstRowShimmer.stopShimmer()
                    firstRowShimmer.visibility = View.INVISIBLE
                }

                override fun onResponse(call: Call<GameResult>, response: Response<GameResult>) {
                    response.body()?.results?.forEach { game ->
                        mYourGamesList.add(game)
                    }

                    firstRowShimmer.stopShimmer()
                    firstRowShimmer.visibility = View.INVISIBLE

                    val mAdapterOne = GameAdapter(context, mYourGamesList)

                    if (yourGamesRecyclerView != null) { // otherwise it can crash
                        yourGamesRecyclerView.layoutManager = layoutManagerOne
                        yourGamesRecyclerView.adapter = mAdapterOne
                    }
                }
            })
        } else {
            val mAdapterOne = GameAdapter(mContext, mYourGamesList)
            firstRowShimmer.stopShimmer()
            firstRowShimmer.visibility = View.INVISIBLE
            yourGamesRecyclerView.layoutManager = layoutManagerOne
            yourGamesRecyclerView.adapter = mAdapterOne
        }
    }

    private fun getRecommendedGames(gameService: GameService, context: Context) {
        val layoutManagerTwo = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        if (mRecommendedGamesList.isEmpty()) {
            mRecommendedGamesArray.map { gameId ->
                gameService.gameDetailsPerId(gameId).enqueue(object: Callback<Game>{
                    override fun onFailure(call: Call<Game>, t: Throwable) {
                        Log.d("GrosProbleme", t.message)
                        secondRowShimmer.stopShimmer()
                        secondRowShimmer.visibility = View.INVISIBLE
                    }

                    override fun onResponse(call: Call<Game>, response: Response<Game>) {
                        response.body()?.let { game ->
                            mRecommendedGamesList.add(game)
                        }
                        secondRowShimmer.stopShimmer()
                        secondRowShimmer.visibility = View.INVISIBLE

                        // Un peu pourri de faire ça ici non ?
                        val mAdapterTwo = GameAdapter(context, mRecommendedGamesList)

                        if (recommendedGamesRecyclerView != null) { // otherwise it can crash
                            recommendedGamesRecyclerView.layoutManager = layoutManagerTwo
                            recommendedGamesRecyclerView.adapter = mAdapterTwo
                        }
                    }
                })
            }
        } else {
            val mAdapterTwo = GameAdapter(mContext, mRecommendedGamesList)
            secondRowShimmer.stopShimmer()
            secondRowShimmer.visibility = View.INVISIBLE
            recommendedGamesRecyclerView.layoutManager = layoutManagerTwo
            recommendedGamesRecyclerView.adapter = mAdapterTwo
        }
    }

}
