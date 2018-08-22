package rtviwe.com.retabelo.model.recipe

import android.content.Context
import android.content.Intent
import android.webkit.WebView
import rtviwe.com.retabelo.details.RecipeDetail


object RecipePresenter {

    fun openDetailActivity(context: Context, item: RecipeEntry) {
        val intent = Intent(context, RecipeDetail::class.java).apply {
            putExtra(RecipeDetail.EXTRA_NAME, item.name)
            putExtra(RecipeDetail.EXTRA_BODY, item.body)
        }
        context.startActivity(intent)
    }

    fun loadWebView(webView: WebView, text: String) {
        val htmlData = "<link rel=\"stylesheet\" type=\"text/css\" href=\"style.css\" />$text"
        webView.loadDataWithBaseURL("file:///android_asset/", htmlData, "text/html", "UTF-8", null)
    }
}