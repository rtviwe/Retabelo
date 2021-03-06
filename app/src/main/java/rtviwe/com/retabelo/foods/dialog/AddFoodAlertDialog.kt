package rtviwe.com.retabelo.foods.dialog

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.add_food_dialog.view.*
import rtviwe.com.retabelo.R
import rtviwe.com.retabelo.foods.FoodsViewModel
import rtviwe.com.retabelo.model.food.FoodEntry
import rtviwe.com.retabelo.model.food.FoodTypeConverter


class AddFoodAlertDialog : DialogFragment() {

    private lateinit var textInput: EditText

    lateinit var foodsViewModel: FoodsViewModel

    @SuppressLint("InflateParams")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val layoutInflaterAndroid = LayoutInflater.from(context)
        val view = layoutInflaterAndroid.inflate(R.layout.add_food_dialog, null)

        SpinnerFoodAdapter(context!!, R.layout.spinner_food_item).also {
            it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

            view.spinner_food_type.apply {
                // Memory leak
                adapter = it
                setSelection(0)
            }
        }

        textInput = view.new_food_edit_text
        showKeyboard()

        return AlertDialog.Builder(context).apply {
            setView(view)
            setCancelable(true)
            setPositiveButton(getString(R.string.button_add)) { _, _ ->
                val name = view.new_food_edit_text.text
                foodsViewModel.insertFood(FoodEntry(0, name.toString(),
                        FoodTypeConverter().toFoodType(view.spinner_food_type.selectedItemPosition)))
            }
            setNegativeButton(getString(R.string.button_cancel), null)
        }.create()
    }

    private fun showKeyboard() {
        textInput.post {
            val imm = activity!!.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(textInput, 0)
        }
    }
}