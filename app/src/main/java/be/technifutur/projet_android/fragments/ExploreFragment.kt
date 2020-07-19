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
    private var mGameList = mutableListOf<Game>()

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
        val layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
        exploreRecyclerView.layoutManager = layoutManager

        if (mGameList.isEmpty()) {
            val retrofit = Retrofit.Builder()
                .baseUrl(MainActivity.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            gameService = retrofit.create(GameService::class.java)

            gameService.mostPopularGames().enqueue(object : Callback<GameResult>{
                override fun onFailure(call: Call<GameResult>, t: Throwable) {
                    Log.d("GrosProbleme", t.message)
                }

                override fun onResponse(call: Call<GameResult>, response: Response<GameResult>) {
                    response.body()?.results?.forEach { game ->
                        mGameList.add(game)
                    }

                    val mAdapter = GameAdapter(mContext, mGameList)
                    exploreRecyclerView.adapter = mAdapter

                }

            })
        } else {
            val mAdapter = GameAdapter(mContext, mGameList)
            exploreRecyclerView.adapter = mAdapter
        }
    }

}
