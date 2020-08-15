package be.technifutur.projet_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import be.technifutur.projet_android.adapters.SearchListAdapter
import be.technifutur.projet_android.mockdata.MockUsers
import be.technifutur.projet_android.models.Game
import be.technifutur.projet_android.models.GameResult
import be.technifutur.projet_android.models.SearchResult
import be.technifutur.projet_android.network.GameService
import kotlinx.android.synthetic.main.activity_search_results.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SearchActivity : AppCompatActivity() {

    private val mUserList = MockUsers.createUsers()
    private var mGameResultList = arrayListOf<Game>()
    private lateinit var mResults : SearchResult

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_results)

        val retrofit = Retrofit.Builder()
            .baseUrl(MainActivity.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val gameService = retrofit.create(GameService::class.java)

        intent.getStringExtra(MainActivity.SEARCH_EXTRA)?.let { query ->

            gameService.gameSearchQuery(query).enqueue(object : Callback<GameResult> {
                override fun onFailure(call: Call<GameResult>, t: Throwable) {
                    Log.d("GrosProbleme", t.message)
                }

                override fun onResponse(call: Call<GameResult>, response: Response<GameResult>) {
                    response.body()?.results?.forEach { game ->
                        mGameResultList.add(game)
                    }

                    resultsRecyclerView.adapter!!.notifyDataSetChanged()
                }

            })

            mResults = SearchResult(mGameResultList, mUserList)

            val adapter = SearchListAdapter(this, mResults)
            //adapter.filter.filter(query)
            resultsRecyclerView.layoutManager = LinearLayoutManager(this)
            resultsRecyclerView.adapter = adapter
        }

        supportActionBar?.title = "Search"
        //supportActionBar?.elevation = 0f
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // back on actionbar
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item);
        }
    }
}
