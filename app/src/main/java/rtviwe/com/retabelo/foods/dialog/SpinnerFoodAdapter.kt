package rtviwe.com.retabelo.foods.dialog

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.spinner_food_item.view.*
import rtviwe.com.retabelo.R
import rtviwe.com.retabelo.model.food.FoodTypeConverter


class SpinnerFoodAdapter(
        context: Context,
        textViewResourceId: Int
) : ArrayAdapter<String>(context, textViewResourceId) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getCustomView(position, parent)
    }

    private fun getCustomView(position: Int, parent: ViewGroup): View {
        val row = LayoutInflater.from(context)
                .inflate(R.layout.spinner_food_item, parent, false)

        row.image_view_spinner.setImageDrawable(
                context.getDrawable(FoodTypeConverter.typesDrawable[position]))

        return row
    }
}