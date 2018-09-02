package rtviwe.com.retabelo.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.rxbinding2.widget.RxSearchView
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.favorites_fragment.*
import rtviwe.com.retabelo.R
import rtviwe.com.retabelo.main.MainBaseFragment
import rtviwe.com.retabelo.model.recipe.RecipeDao
import rtviwe.com.retabelo.model.recipe.RecipeDatabase
import java.util.concurrent.TimeUnit


class FavoritesFragment : MainBaseFragment() {

    override val layoutId = R.layout.favorites_fragment

    private lateinit var recipesDao: RecipeDao
    private lateinit var viewModel: FavoritesViewModel
    private lateinit var favoritesAdapter: FavoritesAdapter
    private lateinit var favoritesLayoutManager: LinearLayoutManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(layoutId, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recipesDao = RecipeDatabase.getInstance(context!!).recipeDao()
        favoritesAdapter = FavoritesAdapter(this)
        viewModel = ViewModelProviders.of(this).get(FavoritesViewModel::class.java)

        initRecyclerView()
        initSearchView()
    }

    override fun onStart() {
        super.onStart()
        viewModel.subscribeFavoritesAdapter(favoritesAdapter)
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

    private fun initSearchView() {
        var isSearchTextEmpty = true

        RxSearchView.queryTextChanges(search_view)
                .debounce(500, TimeUnit.MILLISECONDS)
                .filter {
                    isSearchTextEmpty = it.isEmpty()
                    !isSearchTextEmpty
                }
                .distinctUntilChanged()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe({ searchText ->
                    viewModel.subscribeFavoritesAdapterBySearch(favoritesAdapter, searchText.toString())
                }, {
                    it.printStackTrace()
                })

        search_view.setOnCloseListener {
            if (isSearchTextEmpty) {
                viewModel.subscribeFavoritesAdapter(favoritesAdapter)
                scrollToTop()
            }
            false
        }
    }
}
