package be.technifutur.projet_android

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.recyclerview.widget.LinearLayoutManager
import be.technifutur.projet_android.adapters.GameListAdapter
import be.technifutur.projet_android.models.User
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import kotlinx.android.synthetic.main.activity_user_profile.*

class UserProfileActivity : AppCompatActivity() {

    companion object {
        lateinit var mUser: User
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        setUser(mUser)
        val mAdapter = GameListAdapter(this, mUser.mGames)
        games_recycler_view.adapter = mAdapter
        games_recycler_view.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        // Custom Action Bar
        /*supportActionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar?.setDisplayShowCustomEnabled(true)
        supportActionBar?.setCustomView(R.layout.custom_actionbar_user)
        actionBar?.setDisplayHomeAsUpEnabled(true)*/
        supportActionBar?.elevation = 0f


    }

    private fun setUser(user: User) {
        //user_profile_picture.setImageResource(user.mProfilePicture)
        Glide.with(this).load(user.mProfilePicture).centerCrop().into(user_profile_picture)
        user_username.text = user.mUserName
        supportActionBar?.setDisplayShowTitleEnabled(false) // no title in actionbar
    }
}
