package rtviwe.com.retabelo.model.recipe

import android.content.Context
import android.content.Intent
import android.webkit.WebView
import rtviwe.com.retabelo.details.RecipeDetail


fun openActivity(context: Context, item: RecipeEntry) {
    val intent = Intent(context, RecipeDetail::class.java)
    intent.putExtra(RecipeDetail.EXTRA_NAME, item.name)
    intent.putExtra(RecipeDetail.EXTRA_BODY, item.body)
    context.startActivity(intent)
}

fun loadWebView(webView: WebView, text: String) {
    webView.loadData(text, "text/html", "UTF-8")
}