package rtviwe.com.retabelo.main.fragments.favorites

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.DividerItemDecoration.VERTICAL
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.favorites_fragment.*
import rtviwe.com.retabelo.R
import rtviwe.com.retabelo.main.fragments.BaseFragment
import rtviwe.com.retabelo.model.database.recipe.RecipeDatabase


class FavoritesFragment : BaseFragment() {

    override val layoutId = R.layout.favorites_fragment

    private lateinit var recipesDatabase: RecipeDatabase
    private lateinit var favoritesAdapter: FavoritesAdapter
    private lateinit var viewModel: FavoritesViewModel

    private val disposablePaging = CompositeDisposable()

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
        disposablePaging.dispose()
    }

    private fun initRecyclerView() {
        recycler_view_favorites.apply {
            addItemDecoration(DividerItemDecoration(activity?.applicationContext, VERTICAL))
            layoutManager = LinearLayoutManager(context)
            adapter = favoritesAdapter
        }
    }
}