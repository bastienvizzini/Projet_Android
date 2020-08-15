package be.technifutur.projet_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import be.technifutur.projet_android.adapters.RoomAdapter
import be.technifutur.projet_android.mockdata.MockRooms
import be.technifutur.projet_android.models.Game
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_game.*

class GameActivity : AppCompatActivity() {

    private val mRoomList = MockRooms.createRooms()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        intent.getParcelableExtra<Game>("GAME_SELECTED")?.let { currentGame ->
            this.setGame(currentGame)
            //val mAdapter = UserGameListAdapter(this, currentGame.games)
            //games_recycler_view.adapter = mAdapter
            //games_recycler_view.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        }

        val mAdapter = RoomAdapter(this, mRoomList)
        roomsRecyclerView.adapter = mAdapter
        roomsRecyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        //roomsRecyclerView.layoutManager = LinearLayoutManager(this)

        supportActionBar?.elevation = 0f
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // back on actionbar
    }

    private fun setGame(game: Game) {
        Glide.with(this).load(game.posterPath).centerCrop().into(gameDetailImageView)
        gameDetailNameTextView.text = game.name
    }

    // To go back without recreating the activity
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