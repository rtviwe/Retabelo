package rtviwe.com.retabelo.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.favorites_fragment.*
import rtviwe.com.retabelo.R
import rtviwe.com.retabelo.main.MainBaseFragment
import rtviwe.com.retabelo.model.recipe.RecipeDatabase


class FavoritesFragment : MainBaseFragment() {

    override val layoutId = R.layout.favorites_fragment

    private lateinit var recipesDatabase: RecipeDatabase
    private lateinit var favoritesAdapter: FavoritesAdapter
    private lateinit var viewModel: FavoritesViewModel
    private lateinit var favoritesLayoutManager: LinearLayoutManager

    private val disposablePaging = CompositeDisposable()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(layoutId, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recipesDatabase = RecipeDatabase.getInstance(activity!!.applicationContext)
        favoritesAdapter = FavoritesAdapter(activity!!.application)
        viewModel = ViewModelProviders.of(this).get(FavoritesViewModel::class.java)

        initRecyclerView()
    }

    override fun onStart() {
        super.onStart()
        disposablePaging.add(
                viewModel.recipesList.subscribe(favoritesAdapter::submitList)
        )
    }

    override fun onStop() {
        super.onStop()
        disposablePaging.clear()
    }

    override fun scrollToTop() {
        favoritesLayoutManager.smoothScrollToPosition(recycler_view_favorites, RecyclerView.State(), 0)
    }

    private fun initRecyclerView() {
        favoritesLayoutManager = LinearLayoutManager(context)

        recycler_view_favorites.apply {
            layoutManager = favoritesLayoutManager
            adapter = favoritesAdapter
        }
    }
}