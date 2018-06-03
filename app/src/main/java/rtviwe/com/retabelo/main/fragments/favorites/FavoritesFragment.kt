package rtviwe.com.retabelo.main.fragments.favorites

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.DividerItemDecoration.VERTICAL
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import kotlinx.android.synthetic.main.favorites_fragment.*
import rtviwe.com.retabelo.R
import rtviwe.com.retabelo.database.favorite.FavoriteDatabase
import rtviwe.com.retabelo.main.fragments.BaseFragment


class FavoritesFragment : BaseFragment(), FavoritesAdapter.FavoriteClickListener{

    override val layoutId = R.layout.favorites_fragment

    private lateinit var database: FavoriteDatabase

    private lateinit var favoritesAdapter: FavoritesAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        database = FavoriteDatabase.getInstance(activity!!.applicationContext)
        favoritesAdapter = FavoritesAdapter(activity!!.applicationContext, this)

        recycler_view_favorites.apply {
            addItemDecoration(DividerItemDecoration(activity?.applicationContext, VERTICAL))
            layoutManager = LinearLayoutManager(context)
            adapter = favoritesAdapter
        }

        val viewModel = ViewModelProviders.of(this).get(FavoritesViewModel::class.java)
        viewModel.favorites.observe(this, Observer {
            favoritesAdapter.favorites = it
        })

        // Это временно для генерации песен
        /*AppExecutors.instance.diskIO().execute({
                database.favoriteDao().insertFavorite(FavoriteEntry(0, Random().nextInt(10).toString(), "fav"))
        })*/
    }

    override fun onFavoriteClickListener(itemId: Int) {

    }

    override fun onTrashClickListener(itemId: Int) {

    }
}