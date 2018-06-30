package rtviwe.com.retabelo.main.fragments.foods

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import kotlinx.android.synthetic.main.add_food_dialog.view.*
import rtviwe.com.retabelo.R


class AddFoodAlertDialog : DialogFragment() {

    private var isKeyboardShown = false

    @SuppressLint("InflateParams")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val layoutInflaterAndroid = LayoutInflater.from(context)
        val view = layoutInflaterAndroid.inflate(R.layout.add_food_dialog, null)

        val alertDialogBuilderUserInput = AlertDialog.Builder(this.context!!)
        alertDialogBuilderUserInput.setView(view)

        val imageView = view.scrollable_select_icon
        imageView.setImageResource(R.drawable.ic_receipt_black_24dp)

        val textInput = view.new_food_edit_text
        showKeyboard(textInput)

        val alertDialog = alertDialogBuilderUserInput
                .setCancelable(true)
                .setPositiveButton(getString(R.string.button_add)) { _, _ ->
                    // val name = view.new_food_edit_text.text
                    // viewModel.insertFood(FoodEntry(0, name.toString(), FoodType.ANY))
                    hideKeyboard()
                }
                .setNegativeButton(getString(R.string.button_delete)) { _, _ ->
                    hideKeyboard()
                }
                .create()

        return alertDialog
    }

    override fun onDismiss(dialog: DialogInterface?) {
        hideKeyboard()
        super.onDismiss(dialog)
    }

    private fun hideKeyboard() {
        /*val imm = activity!!.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        var view = activity!!.currentFocus

        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)*/
        val imm = activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm!!.hideSoftInputFromWindow(activity!!.window.decorView.rootView.windowToken, 0)
    }

    private fun showKeyboard(editText: EditText) {
        /*val imm = activity!!.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        var view = activity!!.currentFocus

        if (view == null) {
            view = View(activity)
        }
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0)*/
        val imm = activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm!!.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
    }
}