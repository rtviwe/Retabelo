package rtviwe.com.retabelo.main

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.google.firebase.FirebaseApp
import kotlinx.android.synthetic.main.activity_main.*
import rtviwe.com.retabelo.R


class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        FirebaseApp.initializeApp(this)

        navController = Navigation.findNavController(this, R.id.nav_host_fragment)

        NavigationUI.setupWithNavController(bottom_navigation, navController)

        bottom_navigation.setOnNavigationItemSelectedListener {
            navController.navigate(getFragmentIdFromActionId(it.itemId))
            true
        }

        bottom_navigation.setOnNavigationItemReselectedListener {
            // scrollFragmentToTop()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                navController.popBackStack()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun scrollFragmentToTop(fragment: MainBaseFragment?) {
        fragment?.scrollToTop()
    }

    private fun getFragmentIdFromActionId(id: Int?)
        = when(id) {
            R.id.action_recommendations -> R.id.recommendationsFragment
            R.id.action_favorites -> R.id.favoritesFragment
            R.id.action_foods -> R.id.foodsFragment
            else -> -1
        }
}
