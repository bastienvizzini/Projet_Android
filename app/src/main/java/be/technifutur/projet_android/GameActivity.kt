package be.technifutur.projet_android

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import be.technifutur.projet_android.adapters.game_detail.RoomAdapter
import be.technifutur.projet_android.firebase_api.RoomsHelper
import be.technifutur.projet_android.firebase_api.UserHelper
import be.technifutur.projet_android.models.Game
import be.technifutur.projet_android.models.Message
import be.technifutur.projet_android.models.Room
import be.technifutur.projet_android.models.User
import com.bumptech.glide.Glide
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.activity_game.*
import kotlinx.android.synthetic.main.activity_room.*

class GameActivity : BaseActivity(), RoomAdapter.Listener {

    @Nullable
    private var modelCurrentUser: User? = null
    private var currentGameId = 0
    private lateinit var roomAdapter: RoomAdapter
    private lateinit var currentGame: Game

    companion object {
        private const val GAME_ADDED = 30
    }

    //private val mRoomList = MockRooms.createRooms()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        intent.getParcelableExtra<Game>("GAME_SELECTED")?.let { currentGame ->
            this.currentGame = currentGame
            this.currentGame.id?.let { gameId ->
                currentGameId = gameId

                this.getCurrentUserFromFirestore()
                this.configureRecyclerView(currentGameId)
                this.updateUIWhenCreating()

                this.addGameButton.setOnClickListener{
                    this.addGame()
                }
                this.createRoomFab.setOnClickListener {
                    this.createRoom(currentGame)
                }
            }
        }

        //val mAdapter = RoomAdapter(this, mRoomList)
        //roomsRecyclerView.adapter = mAdapter
        //roomsRecyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        //roomsRecyclerView.layoutManager = LinearLayoutManager(this)

        supportActionBar?.elevation = 0f
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // back on actionbar
    }

    private fun getCurrentUserFromFirestore() {
        UserHelper.getUser(getCurrentUser()?.uid!!).addOnSuccessListener { documentSnapshot ->
            modelCurrentUser = documentSnapshot.toObject(User::class.java)
        }
    }

    private fun configureRecyclerView(gameId: Int) {
        this.roomAdapter = RoomAdapter(generateOptionsForAdapter(RoomsHelper.getAllRoomsForGame(gameId)), Glide.with(this), this, this)
        roomAdapter.registerAdapterDataObserver(object: RecyclerView.AdapterDataObserver(){
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                roomsRecyclerView.smoothScrollToPosition(roomAdapter.itemCount)
            }
        })
        roomsRecyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        roomsRecyclerView.adapter = roomAdapter
    }

    // Create options for RecyclerView from a Query
    private fun generateOptionsForAdapter(query: Query): FirestoreRecyclerOptions<Room> {
        return FirestoreRecyclerOptions.Builder<Room>()
            .setQuery(query, Room::class.java)
            .setLifecycleOwner(this)
            .build()
    }

    private fun createRoom(game: Game) {
        RoomsHelper.createRoom(game.id!!, 4, "French", "Long sessions", "Tryhard", game.id!!)
            .addOnFailureListener { error -> Log.d("bite", "Creating room error : $error") }
            .addOnSuccessListener{
                Toast.makeText(this, getString(R.string.room_created), Toast.LENGTH_SHORT).show()
            }
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
            getString(R.string.added) -> {
                Toast.makeText(this, getString(R.string.deleting_game), Toast.LENGTH_SHORT).show()
            }
            getString(R.string.add) -> {
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
                            addGameButton.text = getString(R.string.added)
                        } else {
                            addGameButton.text = getString(R.string.add)
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
                    addGameButton.text = getString(R.string.added)
                }
                else -> { }
            }
        }
    }

    override fun onDataChanged() {

    }
}