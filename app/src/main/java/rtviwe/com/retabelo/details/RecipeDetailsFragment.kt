package rtviwe.com.retabelo.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.recipe_detail_fragment.*
import rtviwe.com.retabelo.R
import rtviwe.com.retabelo.model.recipe.RecipePresenter

class RecipeDetailsFragment : Fragment() {

    private val layoutId = R.layout.recipe_detail_fragment

    lateinit var name: String
    lateinit var body: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(layoutId, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        name_of_recipe.text = name
        RecipePresenter.loadWebView(web_view, body)
    }
}