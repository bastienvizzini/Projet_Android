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
        val layoutManagerOne = LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
        val layoutManagerTwo = LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)

        if (mYourGamesList.isEmpty() && mRecommendedGamesList.isEmpty()) {
            exploreLoader.visibility = View.VISIBLE

            val retrofit = Retrofit.Builder()
                .baseUrl(MainActivity.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            gameService = retrofit.create(GameService::class.java)

            // For "your games", for loop with user games with gameSearchQuery()
            gameService.mostPopularGames().enqueue(object : Callback<GameResult>{
                override fun onFailure(call: Call<GameResult>, t: Throwable) {
                    Log.d("GrosProbleme", t.message)
                    exploreLoader.visibility = View.GONE
                }

                override fun onResponse(call: Call<GameResult>, response: Response<GameResult>) {
                    response.body()?.results?.forEach { game ->
                        mYourGamesList.add(game)
                    }

                    val mAdapterOne = GameAdapter(mContext, mYourGamesList)

                    if (yourGamesRecyclerView != null) { // otherwise it can crash
                        exploreLoader.visibility = View.GONE
                        yourGamesRecyclerView.layoutManager = layoutManagerOne
                        yourGamesRecyclerView.adapter = mAdapterOne
                    }
                }
            })

            mRecommendedGamesArray.map { gameId ->
                gameService.gameDetailsPerId(gameId).enqueue(object: Callback<Game>{
                    override fun onFailure(call: Call<Game>, t: Throwable) {
                        Log.d("GrosProbleme", t.message)
                    }

                    override fun onResponse(
                        call: Call<Game>,
                        response: Response<Game>
                    ) {
                        response.body()?.let { game ->
                            mRecommendedGamesList.add(game)
                        }
                    }
                })
            }
            val mAdapterTwo = GameAdapter(mContext, mRecommendedGamesList)

            if (recommendedGamesRecyclerView != null) { // otherwise it can crash

                recommendedGamesRecyclerView.layoutManager = layoutManagerTwo
                recommendedGamesRecyclerView.adapter = mAdapterTwo
            }

        } else {
            exploreLoader.visibility = View.VISIBLE
            val mAdapterOne = GameAdapter(mContext, mYourGamesList)
            val mAdapterTwo = GameAdapter(mContext, mRecommendedGamesList)
            exploreLoader.visibility = View.GONE
            yourGamesRecyclerView.layoutManager = layoutManagerOne
            yourGamesRecyclerView.adapter = mAdapterOne
            recommendedGamesRecyclerView.layoutManager = layoutManagerTwo
            recommendedGamesRecyclerView.adapter = mAdapterTwo


        }
    }

}
