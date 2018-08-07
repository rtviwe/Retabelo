package rtviwe.com.retabelo.model.recipe

import android.content.Context
import android.content.Intent
import android.text.method.LinkMovementMethod
import rtviwe.com.retabelo.details.RecipeDetail
import ru.noties.markwon.Markwon
import ru.noties.markwon.SpannableConfiguration
import ru.noties.markwon.renderer.SpannableRenderer
import ru.noties.markwon.view.MarkwonView


object RecipePresenter {

    fun openActivity(context: Context, item: RecipeEntry) {
        val intent = Intent(context, RecipeDetail::class.java)
        intent.putExtra(RecipeDetail.EXTRA_NAME, item.name)
        intent.putExtra(RecipeDetail.EXTRA_BODY, item.body)
        context.startActivity(intent)
    }

    fun loadMarkdown(context: Context, markDownView: MarkwonView, text: String) {
        val parser = Markwon.createParser()

        val configuration = SpannableConfiguration.create(context)

        val renderer = SpannableRenderer()

        val node = parser.parse(text)
        val markdown = renderer.render(configuration, node)

        markDownView.movementMethod = LinkMovementMethod.getInstance()

        markDownView.text = markdown

        Markwon.scheduleDrawables(markDownView)
        Markwon.scheduleTableRows(markDownView)
    }
}