package rtviwe.com.retabelo.foods

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.add_food_dialog.view.*
import rtviwe.com.retabelo.R
import rtviwe.com.retabelo.model.food.FoodEntry
import rtviwe.com.retabelo.model.food.FoodType


class AddFoodAlertDialog : DialogFragment() {

    private lateinit var textInput: EditText
    private val disposableDatabase = CompositeDisposable()

    lateinit var foodsViewModel: FoodsViewModel

    @SuppressLint("InflateParams")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val layoutInflaterAndroid = LayoutInflater.from(context)
        val view = layoutInflaterAndroid.inflate(R.layout.add_food_dialog, null)

        view.scrollable_select_icon.apply {
            setImageResource(R.drawable.ic_receipt_black_24dp)
        }

        textInput = view.new_food_edit_text
        showKeyboard()

        return AlertDialog.Builder(context).apply {
            setView(view)
            setCancelable(true)
            setPositiveButton(getString(R.string.button_add)) { _, _ ->
                val name = view.new_food_edit_text.text
                foodsViewModel.insertFood(FoodEntry(0, name.toString(), FoodType.ANY))

                disposableDatabase.add(foodsViewModel.insertFood(FoodEntry(0, name.toString(), FoodType.ANY))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe())
            }
            setNegativeButton(getString(R.string.button_cancel), null)
        }.create()
    }

    override fun onDestroy() {
        super.onDestroy()
        disposableDatabase.clear()
    }

    private fun showKeyboard() {
        textInput.post {
            val imm = activity!!.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(textInput, 0)
        }
    }
}