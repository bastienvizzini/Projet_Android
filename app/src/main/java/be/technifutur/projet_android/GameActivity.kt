package be.technifutur.projet_android

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import be.technifutur.projet_android.adapters.game_detail.RoomAdapter
import be.technifutur.projet_android.firebase_api.RoomsHelper
import be.technifutur.projet_android.firebase_api.UserHelper
import be.technifutur.projet_android.mockdata.MockRooms
import be.technifutur.projet_android.models.Game
import com.bumptech.glide.Glide
import com.google.android.gms.tasks.OnSuccessListener
import kotlinx.android.synthetic.main.activity_game.*

class GameActivity : BaseActivity() {

    companion object {
        private const val GAME_ADDED = 30
    }

    private val mRoomList = MockRooms.createRooms()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        this.updateUIWhenCreating()
        this.addGameButton.setOnClickListener{
            this.addGame()
        }
        this.createRoomFab.setOnClickListener {
            intent.getParcelableExtra<Game>("GAME_SELECTED")?.let { currentGame ->
                RoomsHelper.createRoom(currentGame.id!!, 4, "French", "Long sessions", "Tryhard")
                    .addOnFailureListener { error -> Log.d("bite", "Creating room error : $error") }
                    .addOnSuccessListener{
                        Toast.makeText(this, "Room created", Toast.LENGTH_SHORT).show()
                    }

            }
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
        /*if (game.image?.screen_large_url != "https://giantbomb1.cbsistatic.com/uploads/screen_kubrick/11/110673/3026329-gb_default-16_9.jpg") {
            Glide.with(this).load(game.image?.screen_large_url).centerCrop()
                .into(gameDetailImageView)
        }*/
        Glide.with(this).load(game.posterPath).centerCrop()
            .into(gameDetailImageView)
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

    private fun addGame() {
        when (addGameButton.text) {
            "Added" -> {
                Toast.makeText(this, "Deleting game... To be implemented", Toast.LENGTH_SHORT).show()
            }
            "Add" -> {
                intent.getParcelableExtra<Game>("GAME_SELECTED")?.let { currentGame ->
                    getCurrentUser()?.let { user ->
                        UserHelper.addGame(currentGame, user.uid)
                            .addOnFailureListener { error -> Log.d("bite", "Adding game error : $error") }
                            .addOnSuccessListener(this.updateUIAfterRESTRequestsCompleted(
                                GAME_ADDED))
                    }
                }
            }
            else -> {
                Toast.makeText(this, "What the hell", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateUIWhenCreating() {

        intent.getParcelableExtra<Game>("GAME_SELECTED")?.let { currentGame ->

            this.setGame(currentGame)

            getCurrentUser()?.let { user ->
                UserHelper.getGame(user.uid, currentGame.id.toString())
                    .addOnSuccessListener { documentSnapshot ->
                        if (documentSnapshot.data != null) {
                            addGameButton.text = "Added"
                        } else {
                            addGameButton.text = "Add"
                        }
                    }
                    .addOnFailureListener { err ->
                        Toast.makeText(this, "Game not found : $err", Toast.LENGTH_SHORT).show()
                    }

            }
        }

    }

    private fun updateUIAfterRESTRequestsCompleted(origin: Int): OnSuccessListener<Void> {
        return OnSuccessListener<Void> {
            when (origin) {
                GAME_ADDED -> {
                    Toast.makeText(
                        this,
                        "Game added",
                        Toast.LENGTH_SHORT
                    ).show()
                    addGameButton.text = "Added"
                }
                else -> { }
            }
        }
    }
}