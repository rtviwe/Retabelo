package rtviwe.com.retabelo.main

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.transaction
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.jakewharton.rxbinding2.support.design.widget.RxBottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import rtviwe.com.retabelo.R
import rtviwe.com.retabelo.favorites.FavoritesFragment
import rtviwe.com.retabelo.foods.FoodsFragment
import rtviwe.com.retabelo.recommendations.RecommendationsFragment


class MainActivity : AppCompatActivity() {

    private val CURRENT_FRAGMENT_ID = "Current fragment"

    private var currentFragmentId = R.id.action_recommendations
    private var currentFragment: MainBaseFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        RxBottomNavigationView.itemSelections(bottom_navigation as BottomNavigationView).subscribe {
            val currentFragmentId = it.itemId

            if (currentFragmentId != bottom_navigation.selectedItemId) {
                val fragment = getFragmentFromId(currentFragmentId)
                currentFragment = fragment
                setFragmentToContainer(currentFragment)
            } else if (currentFragmentId == bottom_navigation.selectedItemId) {
                Log.v("HERE", "$currentFragment")
                scrollFragmentToTop(currentFragment)
            }
        }

        currentFragment = getFragmentFromId(currentFragmentId)
        setFragmentToContainer(currentFragment)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(CURRENT_FRAGMENT_ID, bottom_navigation.selectedItemId)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        currentFragmentId = savedInstanceState.getInt(CURRENT_FRAGMENT_ID)
        currentFragment = getFragmentFromId(currentFragmentId)
        setFragmentToContainer(currentFragment)
    }

    private fun getFragmentFromId(id: Int?): MainBaseFragment? {
        return when(id) {
            R.id.action_recommendations -> RecommendationsFragment()
            R.id.action_favorites -> FavoritesFragment()
            R.id.action_foods -> FoodsFragment()
            else -> null
        }
    }

    private fun scrollFragmentToTop(fragment: MainBaseFragment?) {
        fragment?.scrollToTop()
    }

    private fun setFragmentToContainer(fragment: MainBaseFragment?) {
        if (fragment != null) {
            supportFragmentManager.transaction(allowStateLoss = true) {
                replace(R.id.main_container, fragment)
            }
        }
    }
}
