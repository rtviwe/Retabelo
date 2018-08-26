package rtviwe.com.retabelo.foods

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.DividerItemDecoration.VERTICAL
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.jakewharton.rxbinding2.support.design.widget.RxFloatingActionButton
import com.jakewharton.rxbinding2.support.v7.widget.RxRecyclerView
import kotlinx.android.synthetic.main.foods_fragment.*
import rtviwe.com.retabelo.R
import rtviwe.com.retabelo.main.MainBaseFragment
import rtviwe.com.retabelo.model.food.FoodDao
import rtviwe.com.retabelo.model.food.FoodDatabase


class FoodsFragment : MainBaseFragment() {

    override val layoutId = R.layout.foods_fragment

    private lateinit var foodDao: FoodDao
    private lateinit var foodsAdapter: FoodsAdapter
    private lateinit var viewModel: FoodsViewModel
    private lateinit var foodsLayoutManager: LinearLayoutManager

    private var deletedFoodName = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        foodDao = FoodDatabase.getInstance(context!!).foodDao()
        foodsAdapter = FoodsAdapter()
        viewModel = ViewModelProviders.of(this).get(FoodsViewModel::class.java)

        return inflater.inflate(layoutId, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fab.startAnimation(AnimationUtils.loadAnimation(activity?.applicationContext, R.anim.scale_fab_in))

        initRecyclerView()
        initSwipes()
        initFab()
    }

    override fun onStart() {
        super.onStart()

        viewModel.subscribeFoodsAdapter(foodsAdapter)
    }

    override fun scrollToTop() {
        foodsLayoutManager.smoothScrollToPosition(recycler_view_foods, RecyclerView.State(), 0)
    }

    private fun initRecyclerView() {
        foodsLayoutManager = LinearLayoutManager(context)

        recycler_view_foods.apply {
            addItemDecoration(DividerItemDecoration(activity?.applicationContext, VERTICAL))
            layoutManager = foodsLayoutManager
            adapter = foodsAdapter
        }

        RxRecyclerView.scrollEvents(recycler_view_foods).subscribe {
            if (it.dy() < 0 && !fab.isShown)
                RxFloatingActionButton.visibility(fab).accept(true)
            else if (it.dy() > 0 && fab.isShown)
                RxFloatingActionButton.visibility(fab).accept(false)
        }
    }

    private fun initFab() {
        fab.setOnClickListener {
            showAddFoodDialog()
        }
    }

    private fun initSwipes() {
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int =
                    makeMovementFlags(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)

            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
                                target: RecyclerView.ViewHolder) = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                val selectedFood = (viewHolder as FoodsAdapter.FoodViewHolder).foodEntry
                deletedFoodName = selectedFood.name
                viewModel.deleteFood(selectedFood)

                showSnackbar("$deletedFoodName ${getString(R.string.undo_snackbar_food)}", Snackbar.LENGTH_LONG)
            }
        }).attachToRecyclerView(recycler_view_foods)
    }

    private fun showSnackbar(message: String, length: Int) {
        Snackbar.make(foods_coordinator_layout, message, length).setAction(R.string.undo_string) {
            viewModel.restoreFood()
        }.show()
    }

    private fun showAddFoodDialog() {
        AddFoodAlertDialog().apply {
            foodsViewModel = viewModel
        }.show(childFragmentManager, "AddFoodDialog")
    }
}
