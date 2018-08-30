package rtviwe.com.retabelo.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.recipe_detail_fragment.*
import rtviwe.com.retabelo.R
import rtviwe.com.retabelo.model.recipe.RecipeDao
import rtviwe.com.retabelo.model.recipe.RecipeDatabase
import rtviwe.com.retabelo.model.recipe.RecipeEntry
import rtviwe.com.retabelo.model.recipe.RecipePresenter

class RecipeDetailsFragment : Fragment() {

    private val layoutId = R.layout.recipe_detail_fragment

    private lateinit var recipesDao: RecipeDao
    private lateinit var recipeDetailViewModel: RecipeDetailViewModel

    var name = ""
    var body = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        recipesDao = RecipeDatabase.getInstance(activity!!.application).recipeDao()
        recipeDetailViewModel = ViewModelProviders.of(this).get(RecipeDetailViewModel::class.java)

        return inflater.inflate(layoutId, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        name_of_recipe.text = name

        RecipePresenter.loadWebView(web_view, body)
        RecipeEntry.setIconAndItemFavorite(recipesDao, name, favorite_button_in_details)

        favorite_button_in_details.setOnClickListener {
            recipeDetailViewModel.changeFavorite(name, body)
        }
    }


}