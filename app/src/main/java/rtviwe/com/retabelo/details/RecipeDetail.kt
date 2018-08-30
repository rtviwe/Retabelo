package rtviwe.com.retabelo.details

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.transaction
import rtviwe.com.retabelo.R

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class RecipeDetail : AppCompatActivity() {

    companion object {
        const val EXTRA_NAME = "name"
        const val EXTRA_BODY = "body"
    }

    private lateinit var fragment: RecipeDetailsFragment

    private var recipeName = ""
    private var recipeBody = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_detail)

        recipeName = intent.getStringExtra(EXTRA_NAME)
        recipeBody = intent.getStringExtra(EXTRA_BODY)

        fragment = RecipeDetailsFragment().apply {
            name = recipeName
            body = recipeBody
        }

        supportFragmentManager.transaction(allowStateLoss = true) {
            replace(R.id.recipe_container, fragment)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(EXTRA_NAME, recipeName)
        outState.putString(EXTRA_BODY, recipeBody)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        if (savedInstanceState.getString(EXTRA_NAME) != null && savedInstanceState.getString(EXTRA_BODY) != null) {
            recipeName = savedInstanceState.getString(EXTRA_NAME)
            recipeBody = savedInstanceState.getString(EXTRA_BODY)
        }
    }
}