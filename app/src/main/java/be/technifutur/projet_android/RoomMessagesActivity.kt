package be.technifutur.projet_android

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.annotation.Nullable
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import be.technifutur.projet_android.adapters.game_detail.RoomMessageAdapter
import be.technifutur.projet_android.firebase_api.MessagesHelper
import be.technifutur.projet_android.firebase_api.UserHelper
import be.technifutur.projet_android.models.Message
import be.technifutur.projet_android.models.User
import com.bumptech.glide.Glide
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.activity_room.*

class RoomMessagesActivity : BaseActivity(), RoomMessageAdapter.Listener {

    @Nullable private var modelCurrentUser: User? = null
    private lateinit var currentChatUid: String
    private lateinit var currentGameId: String
    private lateinit var roomMessageAdapter: RoomMessageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room)

        val uid = "Gv2ETLZ4ypakxDotsTIO"
        val gameId = "290856"

        this.configureRecyclerView(gameId, uid)
        this.getCurrentUserFromFirestore()

        supportActionBar?.elevation = 0f
        supportActionBar?.setDisplayShowTitleEnabled(false) // No title in action bar
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // back on actionbar

        roomSendMessageButton.setOnClickListener {
            // 1 - Check if text field is not empty and current user properly downloaded from Firestore
            if (!TextUtils.isEmpty(roomEditTextMessage.text) && modelCurrentUser != null) {
                // 2 - Create a new Message to Firestore
                MessagesHelper.createMessageForRoom(
                    roomEditTextMessage.text.toString(),
                    this.currentGameId,
                    this.currentChatUid,
                    modelCurrentUser
                ).addOnFailureListener(this.onFailureListener())
                // 3 - Reset text field
                this.roomEditTextMessage.setText("")
            }
        }
    }

    // --------------------
    // REST REQUESTS
    // --------------------
    // Get Current User from Firestore
    private fun getCurrentUserFromFirestore() {
        UserHelper.getUser(getCurrentUser()?.uid!!).addOnSuccessListener { documentSnapshot ->
            modelCurrentUser = documentSnapshot.toObject(User::class.java)
        }
    }

    // --------------------
    // UI
    // --------------------
    // Configure RecyclerView with a Query
    private fun configureRecyclerView(gameId: String, roomUid: String) {
        //track game
        this.currentGameId = gameId
        // track current chat
        this.currentChatUid = roomUid

        //Configure adapter & RecyclerView
        this.roomMessageAdapter = RoomMessageAdapter(
            generateOptionsForAdapter(MessagesHelper.getAllMessageForRoom(currentGameId,
                this.currentChatUid)), Glide.with(this), this, this.getCurrentUser()?.uid!!
        )
        roomMessageAdapter.registerAdapterDataObserver(object: RecyclerView.AdapterDataObserver(){
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                roomMessagesRecyclerView.smoothScrollToPosition(roomMessageAdapter.itemCount)
            }
        })
        roomMessagesRecyclerView.layoutManager = LinearLayoutManager(this)
        roomMessagesRecyclerView.adapter = roomMessageAdapter
    }

    // 6 - Create options for RecyclerView from a Query
    private fun generateOptionsForAdapter(query: Query): FirestoreRecyclerOptions<Message> {
        return FirestoreRecyclerOptions.Builder<Message>()
            .setQuery(query, Message::class.java)
            .setLifecycleOwner(this)
            .build()
    }

    override fun onDataChanged() {
        if (this.roomMessageAdapter.itemCount == 0) {
            roomEmptyRecyclerView.visibility = View.VISIBLE
        } else {
            roomEmptyRecyclerView.visibility = View.GONE
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}