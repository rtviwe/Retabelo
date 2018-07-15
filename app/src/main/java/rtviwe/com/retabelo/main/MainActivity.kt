package rtviwe.com.retabelo.main

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.places.Places
import com.jakewharton.rxbinding2.support.design.widget.RxBottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import rtviwe.com.retabelo.R
import rtviwe.com.retabelo.database.food.FoodDatabase
import rtviwe.com.retabelo.geolocation.ConnectionListener
import rtviwe.com.retabelo.main.fragments.favorites.FavoritesFragment
import rtviwe.com.retabelo.main.fragments.foods.FoodsFragment
import rtviwe.com.retabelo.main.fragments.recommendations.RecommendationsFragment


class MainActivity : AppCompatActivity() {

    private val CURRENT_FRAGMENT_ID = "Current fragment"

    private lateinit var googleApiClient: GoogleApiClient
    private lateinit var foodDatabase: FoodDatabase
    private var currentFragmentId = R.id.action_recommendations

    private val connectionListener = ConnectionListener()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        foodDatabase = FoodDatabase.getInstance(applicationContext)

        RxBottomNavigationView.itemSelections(bottom_navigation as BottomNavigationView).subscribe {
            val currentFragmentId = it.itemId
            if (currentFragmentId != bottom_navigation.selectedItemId) {
                val fragment = getFragmentFromId(currentFragmentId)
                setFragmentToContainer(fragment)
            }
        }

        val currentFragment = getFragmentFromId(currentFragmentId)
        setFragmentToContainer(currentFragment)

        googleApiClient = GoogleApiClient.Builder(this)
                .addConnectionCallbacks(connectionListener)
                .addOnConnectionFailedListener(connectionListener)
                .addApi(LocationServices.API)
                .addApi(Places.GEO_DATA_API)
                .enableAutoManage(this, connectionListener)
                .build()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(CURRENT_FRAGMENT_ID, bottom_navigation.selectedItemId)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        currentFragmentId = savedInstanceState.getInt(CURRENT_FRAGMENT_ID)
        val currentFragment = getFragmentFromId(currentFragmentId)
        setFragmentToContainer(currentFragment)
    }

    private fun getFragmentFromId(id: Int?): Fragment? {
        return when(id) {
            R.id.action_recommendations -> RecommendationsFragment()
            R.id.action_favorites -> FavoritesFragment()
            R.id.action_foods -> FoodsFragment()
            else -> null
        }
    }

    private fun setFragmentToContainer(fragment: Fragment?) {
        if (fragment != null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.main_container, fragment)
                    .commit()
        }
    }
}
