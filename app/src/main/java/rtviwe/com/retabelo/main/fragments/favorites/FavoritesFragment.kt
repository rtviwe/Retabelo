package rtviwe.com.retabelo.main.fragments.favorites

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.DividerItemDecoration.VERTICAL
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.favorites_fragment.*
import rtviwe.com.retabelo.R
import rtviwe.com.retabelo.database.recipe.RecipeDatabase
import rtviwe.com.retabelo.main.fragments.BaseFragment


class FavoritesFragment : BaseFragment() {

    override val layoutId = R.layout.favorites_fragment

    private lateinit var recipeDatabase: RecipeDatabase
    private lateinit var favoritesAdapter: FavoritesAdapter

    private var subscribe: Disposable? = null
    private var subscribeOnTrash: Disposable? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recipeDatabase = RecipeDatabase.getInstance(activity!!.applicationContext)
        favoritesAdapter = FavoritesAdapter(activity!!.applicationContext)

        setupRecyclerView()
        setupItemClicks()

        val viewModel = ViewModelProviders.of(this).get(FavoritesViewModel::class.java)
        viewModel.recipeDao.getFavoriteRecipes()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { favoritesAdapter.favorites = it }
    }

    override fun onDestroy() {
        super.onDestroy()
        subscribe?.dispose()
        subscribeOnTrash?.dispose()
    }

    private fun setupRecyclerView() {
        recycler_view_favorites.apply {
            addItemDecoration(DividerItemDecoration(activity?.applicationContext, VERTICAL))
            layoutManager = LinearLayoutManager(context)
            adapter = favoritesAdapter
        }
    }

    private fun setupItemClicks() {
        subscribe = favoritesAdapter.clickEvent
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { Log.v("ITEM", "$it") }

        subscribeOnTrash = favoritesAdapter.clickEventOnTrash
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { Log.v("TRASH", "$it") }
    }
}