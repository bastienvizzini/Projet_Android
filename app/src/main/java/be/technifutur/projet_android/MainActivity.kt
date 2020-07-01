package be.technifutur.projet_android

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import be.technifutur.projet_android.fragments.ExploreFragment
import be.technifutur.projet_android.fragments.FriendsFragment
import be.technifutur.projet_android.fragments.MessagesFragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.custom_actionbar_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var bottomNavigation: BottomNavigationView
    private lateinit var searchView: SearchView

    companion object {
        const val SEARCH_EXTRA = "search_extra"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigation = findViewById(R.id.bottom_nav)

        bottomNavigation.setOnNavigationItemSelectedListener(bottomNavMethod)
        supportFragmentManager.beginTransaction().replace(R.id.tab_container, FriendsFragment()).commit()
        screen_title.text = getString(R.string.friend_list_title)

        // Custom Action Bar
        supportActionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar?.setDisplayShowCustomEnabled(true)
        supportActionBar?.setCustomView(R.layout.custom_actionbar_main)

        searchView = supportActionBar?.customView!!.findViewById(R.id.searchView)

        Glide.with(this).load(R.drawable.main_user).circleCrop().into(user_picture)

    }

    private val bottomNavMethod =
        BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->

            var fragment: Fragment = Fragment()
            when (menuItem.itemId) {
                R.id.friends -> {
                    fragment =
                        FriendsFragment()
                    screen_title.text = getString(R.string.friend_list_title)
                }
                R.id.explore -> {
                    fragment =
                        ExploreFragment()
                    screen_title.text = getString(R.string.explore_title)
                }
                R.id.messages -> {
                    fragment =
                        MessagesFragment()
                    screen_title.text = getString(R.string.messages_title)
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
    }

}
