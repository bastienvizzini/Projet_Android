package be.technifutur.projet_android

import android.app.SearchManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import be.technifutur.projet_android.adapters.SearchListAdapter
import be.technifutur.projet_android.mockdata.MockUsers
import kotlinx.android.synthetic.main.activity_search_results.*
import kotlinx.android.synthetic.main.fragment_friends.*

class SearchActivity : AppCompatActivity() {

    private val mUserList = MockUsers.createUsers()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_results)

        val extraQuerySearch = intent.getStringExtra(MainActivity.SEARCH_EXTRA)

        val mAdapter = SearchListAdapter(this, mUserList)
        mAdapter.filter.filter(extraQuerySearch)
        resultsRecyclerView.adapter = mAdapter
        resultsRecyclerView.layoutManager = LinearLayoutManager(this)
    }
}
