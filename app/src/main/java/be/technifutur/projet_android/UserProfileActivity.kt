package be.technifutur.projet_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

        val currentUser = intent.getParcelableExtra<User>("USER_SELECTED")
        setUser(currentUser)
        val mAdapter = UserGameListAdapter(this, currentUser.games)
        games_recycler_view.adapter = mAdapter
        games_recycler_view.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        // Custom Action Bar
        /*supportActionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar?.setDisplayShowCustomEnabled(true)
        supportActionBar?.setCustomView(R.layout.custom_actionbar_user)
        actionBar?.setDisplayHomeAsUpEnabled(true)*/
        supportActionBar?.elevation = 0f


    }

    private fun setUser(user: User?) {
        //user_profile_picture.setImageResource(user.mProfilePicture)
        user?.let {
            Glide.with(this).load(user.profilePicture).centerCrop().into(user_profile_picture)
            user_username.text = user.userName
            supportActionBar?.setDisplayShowTitleEnabled(false) // no title in actionbar
        }
    }
}
