package be.technifutur.projet_android

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import be.technifutur.projet_android.firebaseapi.UserHelper
import be.technifutur.projet_android.fragments.ExploreFragment
import be.technifutur.projet_android.fragments.FriendsFragment
import be.technifutur.projet_android.fragments.MessagesFragment
import be.technifutur.projet_android.models.Game
import be.technifutur.projet_android.models.MyGame
import be.technifutur.projet_android.models.MyUser
import be.technifutur.projet_android.models.Platform
import be.technifutur.projet_android.network.GameService
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.custom_actionbar_main.*


class MainActivity : BaseActivity() {

    private lateinit var bottomNavigation: BottomNavigationView
    private lateinit var searchView: SearchView

    // Fragments
    private val mFriendsFragment = FriendsFragment()
    private val mExploreFragment = ExploreFragment()
    private val mMessagesFragment = MessagesFragment()

    private var mActiveTabString = FRIENDS

    private val mFirebaseUser = getCurrentUser()
    private val mUser = createUser()
    private var mAllGamesList = arrayListOf<Game>()

    private lateinit var gameService: GameService

    companion object {
        const val SEARCH_EXTRA = "search_extra"
        const val GAME_LIST_EXTRA = "game_list_extra"
        const val BASE_URL_RAWG = "https://api.rawg.io/api/"
        const val BASE_URL_GIANTBOMB = "https://www.giantbomb.com/api/"
        private const val ACTIVE_TAB = "active_tab"
        private const val FRIENDS = "friends"
        private const val EXPLORE = "explore"
        private const val MESSAGES = "messages"
    }

    private fun createUser(): MyUser {
        val mGame = MyGame("On m'appelle l'Ovni", Platform.PS4, R.drawable.apex_legends)
        val mGameList : ArrayList<MyGame> = arrayListOf(mGame, mGame, mGame)
        return MyUser(
            mFirebaseUser?.displayName.toString(),
            38,
            "Marseille",
            R.drawable.main_user,
            mGameList,
            true
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!isCurrentUserLogged()) {
            finish()
        }
        setContentView(R.layout.activity_main)

        bottomNavigation = findViewById(R.id.bottom_nav)

        bottomNavigation.setOnNavigationItemSelectedListener(bottomNavMethod)

        if (savedInstanceState != null) {
            mActiveTabString = when (savedInstanceState.get(ACTIVE_TAB)) {
                EXPLORE -> {
                    supportFragmentManager.beginTransaction().replace(
                        R.id.tab_container,
                        mExploreFragment
                    ).commit()
                    EXPLORE // Sais pas pourquoi, mais si plusieurs rotation, mActiveTabString revient à "friends"
                }
                MESSAGES -> {
                    supportFragmentManager.beginTransaction().replace(
                        R.id.tab_container,
                        mMessagesFragment
                    ).commit()
                    MESSAGES // when lifted out, so this line == mActiveTabString = MESSAGES
                }
                else -> {
                    supportFragmentManager.beginTransaction().replace(
                        R.id.tab_container,
                        mFriendsFragment
                    ).commit()
                    FRIENDS
                }
            }
            Log.d("bite", savedInstanceState.get(ACTIVE_TAB).toString())
        } else {
            supportFragmentManager.beginTransaction().replace(R.id.tab_container, mFriendsFragment).commit()
        }

        // Custom Action Bar
        supportActionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar?.setDisplayShowCustomEnabled(true)
        supportActionBar?.setCustomView(R.layout.custom_actionbar_main)
        //supportActionBar?.elevation = 0f

        searchView = supportActionBar?.customView!!.findViewById(R.id.searchView)

        Glide.with(this).load(R.drawable.main_user).circleCrop().into(user_picture)
    }

    private val bottomNavMethod =
        BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->

            var fragment: Fragment = Fragment()
            when (menuItem.itemId) {
                R.id.friends -> {
                    fragment =
                        mFriendsFragment
                    mActiveTabString = FRIENDS
                }
                R.id.explore -> {
                    fragment =
                        mExploreFragment
                    mActiveTabString = EXPLORE
                }
                R.id.messages -> {
                    fragment =
                        mMessagesFragment
                    mActiveTabString = MESSAGES
                    //tab_container.setBackgroundColor(Color.TRANSPARENT) // lol vire ça quand tu peux
                }
            }
            supportFragmentManager.beginTransaction().replace(R.id.tab_container, fragment).commit()

            true
        }


    fun onSearch(view: View) {
        // Set the whole area of searchView clickable
        searchView.isIconified = false

        // Search process
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                val searchIntent = Intent(applicationContext, SearchActivity::class.java)
                searchIntent.putExtra(SEARCH_EXTRA, query)
                startActivity(searchIntent)
                searchView.clearFocus()
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })
    }

    override fun onResume() {
        super.onResume()

        // Clear searchView when we go back to MainActivity
        searchView.setQuery("", false)

        if (!isCurrentUserLogged()) {
            finish()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putString(ACTIVE_TAB, mActiveTabString)
    }

    fun onUserProfile(view: View) {
        val userProfileIntent = Intent(view.context, UserProfileActivity::class.java)
        userProfileIntent.putExtra("USER_MAIN", mUser) // changer pour "MAIN_USER" et changer dans le code (faire un switch et définir layout dans UserProfileActivity
        startActivity(userProfileIntent)
    }

}
