package be.technifutur.projet_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var bottomNavigation: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigation = findViewById(R.id.bottom_nav)

        bottomNavigation.setOnNavigationItemSelectedListener(bottomNavMethod)
        supportFragmentManager.beginTransaction().replace(R.id.tab_container, FriendsFragment()).commit()
    }

    private val bottomNavMethod = BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->

        var fragment: Fragment = Fragment()
        when (menuItem.itemId) {
            R.id.friends -> fragment = FriendsFragment()
            R.id.explore -> fragment = ExploreFragment()
            R.id.messages -> fragment = MessagesFragment()
        }
        supportFragmentManager.beginTransaction().replace(R.id.tab_container, fragment).commit()

        true
    }
}
