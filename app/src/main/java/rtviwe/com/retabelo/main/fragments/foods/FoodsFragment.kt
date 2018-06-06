package rtviwe.com.retabelo.main.fragments.foods

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.DividerItemDecoration.VERTICAL
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import com.jakewharton.rxbinding2.support.design.widget.RxFloatingActionButton
import com.jakewharton.rxbinding2.support.v7.widget.RxRecyclerView
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.foods_fragment.*
import rtviwe.com.retabelo.R
import rtviwe.com.retabelo.database.food.FoodDatabase
import rtviwe.com.retabelo.main.fragments.BaseFragment


class FoodsFragment : BaseFragment() {

    override val layoutId = R.layout.foods_fragment

    private lateinit var foodsDatabase: FoodDatabase
    private lateinit var foodsAdapter: FoodsAdapter

    private val disposablePaging = CompositeDisposable()
    private lateinit var viewModel: FoodsViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fab.startAnimation(AnimationUtils.loadAnimation(activity?.applicationContext, R.anim.scale_fab_in))

        foodsDatabase = FoodDatabase.getInstance(activity!!.applicationContext)
        foodsAdapter = FoodsAdapter(activity!!.applicationContext)
        viewModel = ViewModelProviders.of(this).get(FoodsViewModel::class.java)

        initRecyclerView()
        initSwipeToDelete()
        initFabClicked()

        /*Flowable.just(FoodEntry(0, "Milk", FoodType.WATER),
                      FoodEntry(0, "Bread", FoodType.BREAD),
                      FoodEntry(0, "Butter", FoodType.GROCERY))
                .observeOn(Schedulers.io())
                .subscribe {
                    viewModel.insertFood(it)
                }*/
    }

    override fun onStart() {
        super.onStart()
        disposablePaging.add(viewModel.foodsList.subscribe(foodsAdapter::submitList))
    }

    override fun onStop() {
        super.onStop()
        disposablePaging.dispose()
    }

    private fun initRecyclerView() {
        recycler_view_foods.apply {
            addItemDecoration(DividerItemDecoration(activity?.applicationContext, VERTICAL))
            layoutManager = LinearLayoutManager(context)
            adapter = foodsAdapter
        }

        RxRecyclerView.scrollEvents(recycler_view_foods).subscribe {
            if (it.dy() < 0 && !fab.isShown)
                RxFloatingActionButton.visibility(fab).accept(true)
            else if (it.dy() > 0 && fab.isShown)
                RxFloatingActionButton.visibility(fab).accept(false)
        }
    }

    private fun initFabClicked() {
        RxView.clicks(fab)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    Log.v("FAB", "Clicked")
                }
    }

    private fun initSwipeToDelete() {
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun getMovementFlags(recyclerView: RecyclerView,
                                          viewHolder: RecyclerView.ViewHolder): Int =
                    makeMovementFlags(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)

            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
                                target: RecyclerView.ViewHolder) = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                (viewHolder as FoodsAdapter.FoodViewHolder).food.let {
                    viewModel.deleteFood(it)
                }
            }
        }).attachToRecyclerView(recycler_view_foods)
    }
}