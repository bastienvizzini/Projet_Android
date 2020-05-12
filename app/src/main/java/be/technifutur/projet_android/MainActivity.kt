package be.technifutur.projet_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var bottomNavigation: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigation = findViewById(R.id.bottom_nav)

        bottomNavigation.setOnNavigationItemSelectedListener(bottomNavMethod)
        supportFragmentManager.beginTransaction().replace(R.id.tab_container, FriendsFragment())
            .commit()

    }

    private val bottomNavMethod =
        BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->

            var fragment: Fragment = Fragment()
            when (menuItem.itemId) {
                R.id.friends -> {
                    fragment = FriendsFragment()
                    screen_title.text = getString(R.string.friend_list_title)
                }
                R.id.explore -> {
                    fragment = ExploreFragment()
                    screen_title.text = getString(R.string.explore_title)
                }
                R.id.messages -> {
                    fragment = MessagesFragment()
                    screen_title.text = getString(R.string.messages_title)
                }
            }
            supportFragmentManager.beginTransaction().replace(R.id.tab_container, fragment).commit()

            true
        }
}
