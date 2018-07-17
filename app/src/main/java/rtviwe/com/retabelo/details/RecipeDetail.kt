package rtviwe.com.retabelo.details

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import rtviwe.com.retabelo.R

class RecipeDetail : AppCompatActivity() {

    companion object {
        const val EXTRA_NAME = "name"
        const val EXTRA_BODY = "body"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_detail)

        val fragment = RecipeDetailsFragment()
        fragment.apply {
            name = intent.getStringExtra("name")
            body = intent.getStringExtra("body")
        }

        supportFragmentManager
                .beginTransaction()
                .replace(R.id.recipe_container, fragment)
                .commit()
    }
}