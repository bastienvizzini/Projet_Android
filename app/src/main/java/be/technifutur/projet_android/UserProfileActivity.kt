package be.technifutur.projet_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import be.technifutur.projet_android.adapters.UserGameListAdapter
import be.technifutur.projet_android.models.User
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_user_profile.*

class UserProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        // quand ce sera un fragment, faire comme dans le projet Pokédex fragment avec bundle et args

        // Selected user
        intent.getParcelableExtra<User>("USER_SELECTED")?.let { currentUser ->
            this.setUser(currentUser)
            val mAdapter = UserGameListAdapter(this, currentUser.games)
            games_recycler_view.adapter = mAdapter
            games_recycler_view.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        }

        // Main user
        intent.getParcelableExtra<User>("USER_MAIN")?.let { mainUser ->
            this.setUser(mainUser)
            val mAdapter = UserGameListAdapter(this, mainUser.games)
            games_recycler_view.adapter = mAdapter
            games_recycler_view.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            this.setupMainUserViews()
        }

        // Custom Action Bar
        /*supportActionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar?.setDisplayShowCustomEnabled(true)
        supportActionBar?.setCustomView(R.layout.custom_actionbar_user)
        actionBar?.setDisplayHomeAsUpEnabled(true)*/
        supportActionBar?.elevation = 0f
        supportActionBar?.setDisplayShowTitleEnabled(false) // No title in action bar
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // back on actionbar


    }

    private fun setUser(user: User) {
        //user_profile_picture.setImageResource(user.mProfilePicture)
        Glide.with(this).load(user.profilePicture).centerCrop().into(user_profile_picture)
        user_username.text = user.userName
    }

    private fun setupMainUserViews() {
        add_friend_image_button.setImageDrawable(getDrawable(R.drawable.ic_edit))
        sendMessageButton.text = getString(R.string.add_games_button_title)
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
