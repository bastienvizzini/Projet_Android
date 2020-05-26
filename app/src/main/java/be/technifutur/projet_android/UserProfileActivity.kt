package be.technifutur.projet_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import be.technifutur.projet_android.adapters.GameListAdapter
import be.technifutur.projet_android.models.User
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
    }

    private fun setUser(user: User) {
        user_profile_picture.setImageResource(user.mProfilePicture)
        user_username.text = user.mUserName
    }
}
