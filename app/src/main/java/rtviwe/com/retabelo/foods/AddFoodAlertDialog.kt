package rtviwe.com.retabelo.foods

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import kotlinx.android.synthetic.main.add_food_dialog.view.*
import rtviwe.com.retabelo.R
import rtviwe.com.retabelo.model.food.FoodEntry
import rtviwe.com.retabelo.model.food.FoodType


class AddFoodAlertDialog : DialogFragment() {

    private lateinit var textInput: EditText
    lateinit var foodsViewModel: FoodsViewModel

    @SuppressLint("InflateParams")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val layoutInflaterAndroid = LayoutInflater.from(context)
        val view = layoutInflaterAndroid.inflate(R.layout.add_food_dialog, null)

        val alertDialogBuilderUserInput = AlertDialog.Builder(context)
        alertDialogBuilderUserInput.setView(view)

        val imageView = view.scrollable_select_icon
        imageView.setImageResource(R.drawable.ic_receipt_black_24dp)

        textInput = view.new_food_edit_text
        showKeyboard()

        return alertDialogBuilderUserInput.apply {
            setCancelable(true)
            setPositiveButton(getString(R.string.button_add)) { _, _ ->
                val name = view.new_food_edit_text.text
                foodsViewModel.insertFood(FoodEntry(0, name.toString(), FoodType.ANY))
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