package rtviwe.com.retabelo.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.recipe_detail_fragment.*
import rtviwe.com.retabelo.R
import rtviwe.com.retabelo.model.recipe.RecipeDao
import rtviwe.com.retabelo.model.recipe.RecipeDatabase
import rtviwe.com.retabelo.model.recipe.RecipeEntry
import rtviwe.com.retabelo.model.recipe.RecipePresenter

class RecipeDetailsFragment : Fragment() {

    private val layoutId = R.layout.recipe_detail_fragment
    private val disposableFavorite = CompositeDisposable()
    private lateinit var recipesDatabase: RecipeDatabase
    private lateinit var recipesDao: RecipeDao

    var name: String = ""
    var body: String = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        recipesDatabase = RecipeDatabase.getInstance(activity!!.application)
        recipesDao = recipesDatabase.recipeDao()

        return inflater.inflate(layoutId, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        name_of_recipe.text = name
        RecipePresenter.loadWebView(web_view, body)

        RecipeEntry.setIsFavorite(recipesDao, name, favorite_button_in_details)

        RxView.clicks(favorite_button_in_details)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    disposableFavorite.add(
                            Completable.fromAction {
                                        val item = recipesDao.findRecipeByName(name)
                                        if (item != null) {
                                            RecipeEntry.changeFavorite(recipesDao, item)
                                        }
                                    }
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe()
                    )
                }
    }
}