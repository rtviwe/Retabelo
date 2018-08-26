package rtviwe.com.retabelo.details

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.transaction
import rtviwe.com.retabelo.R

class RecipeDetail : AppCompatActivity() {

    companion object {
        const val EXTRA_NAME = "name"
        const val EXTRA_BODY = "body"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_detail)

        val fragment = RecipeDetailsFragment().apply {
            name = intent.getStringExtra(EXTRA_NAME)
            body = intent.getStringExtra(EXTRA_BODY)
        }

        supportFragmentManager.transaction(allowStateLoss = true) {
            replace(R.id.recipe_container, fragment)
        }
    }
}