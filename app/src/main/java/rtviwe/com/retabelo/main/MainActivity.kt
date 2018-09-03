package rtviwe.com.retabelo.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.firebase.FirebaseApp
import com.jakewharton.rxbinding2.support.design.widget.RxBottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import rtviwe.com.retabelo.R


class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private var currentFragmentId = -1

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        FirebaseApp.initializeApp(this)

        navController = Navigation.findNavController(this, R.id.nav_host_fragment)

        RxBottomNavigationView.itemSelections(bottom_navigation).subscribe {
            val selectedItemId = getFragmentIdFromActionId(it.itemId)
            Log.v("HELP", "SelectedItemId: $selectedItemId cuurentItemId: $currentFragmentId")
            if (selectedItemId == currentFragmentId) {
                // scrollFragmentToTop()
            } else {
                navController.navigate(selectedItemId)
                currentFragmentId = selectedItemId
            }
        }
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
