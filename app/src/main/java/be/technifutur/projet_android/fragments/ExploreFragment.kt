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

        mContext = requireContext()

        val retrofit = Retrofit.Builder()
            .baseUrl(MainActivity.BASE_URL)
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
            //adapterOne.notifyDataSetChanged()
            this.getRecommendedGames(gameService, mContext)
            //adapterTwo.notifyDataSetChanged()

        } else {
            this.getYourGames(gameService, mContext)
            //adapterOne.notifyDataSetChanged()
            this.getRecommendedGames(gameService, mContext)
            //adapterTwo.notifyDataSetChanged()
        }

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
        val layoutManagerOne = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        //val mAdapterOne = GameAdapter(context, mYourGamesList)

        if (mYourGamesList.isEmpty()) {

            gameService.mostPopularGames().enqueue(object : Callback<GameResult>{
                override fun onFailure(call: Call<GameResult>, t: Throwable) {
                    Log.d("GrosProbleme", t.message)
                }

                override fun onResponse(call: Call<GameResult>, response: Response<GameResult>) {
                    response.body()?.results?.forEach { game ->
                        mYourGamesList.add(game)
                    }

                    yourGamesRecyclerView.adapter!!.notifyDataSetChanged()
                }
            })
        } else {
            val mAdapterOne = GameAdapter(mContext, mYourGamesList)
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
                    }

                    override fun onResponse(call: Call<Game>, response: Response<Game>) {
                        response.body()?.let { game ->
                            mRecommendedGamesList.add(game)
                        }

                        recommendedGamesRecyclerView.adapter!!.notifyDataSetChanged()
                    }
                })

            }
        } else {
            val mAdapterTwo = GameAdapter(mContext, mRecommendedGamesList)
            recommendedGamesRecyclerView.layoutManager = layoutManagerTwo
            recommendedGamesRecyclerView.adapter = mAdapterTwo
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList("YOUR_GAMES", mYourGamesList)
        outState.putParcelableArrayList("RECOMMENDED_GAMES", mRecommendedGamesList)
    }

}
