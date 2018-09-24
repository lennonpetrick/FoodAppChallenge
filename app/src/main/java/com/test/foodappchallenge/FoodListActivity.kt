package com.test.foodappchallenge

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import com.test.foodappchallenge.di.DaggerFoodListComponent
import com.test.foodappchallenge.di.FoodListModule
import com.test.foodappchallenge.domain.model.Food
import kotlinx.android.synthetic.main.activity_food_list.*
import javax.inject.Inject

class FoodListActivity : AppCompatActivity(), FoodListContract.View {

    @Inject lateinit var presenter: FoodListContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_list)
        injectDependencies()
        setUpViews()
    }

    override fun onResume() {
        super.onResume()
        presenter.load()
    }

    override fun onDestroy() {
        presenter.destroy()
        super.onDestroy()
    }

    override fun setFoodList(foods: List<Food>) {
        val adapter = recyclerView.adapter as ItemRecyclerAdapter
        adapter.setItems(foods)
    }

    override fun showError(error: String?) {
        Snackbar.make(refreshLayout,
                error ?: getText(R.string.unknown_error),
                Snackbar.LENGTH_LONG).show()
    }

    override fun displayLoading() {
        //TODO("not implemented")
    }

    override fun dismissLoading() {
        //TODO("not implemented")
    }

    private fun injectDependencies() {
        DaggerFoodListComponent
                .builder()
                .foodListModule(FoodListModule(this))
                .build()
                .inject(this)
    }

    private fun setUpViews() {
        setSupportActionBar(toolbar)

        refreshLayout.apply {
            setColorSchemeResources(R.color.colorPrimary)
            setOnRefreshListener {
                presenter.load()
            }
        }

        recyclerView.apply {
            adapter = ItemRecyclerAdapter(arrayListOf()).apply {
                onItemClickListener = object: ItemRecyclerAdapter.OnItemClickListener {
                    override fun onItemClick(item: Food, position: Int) {
                        startFoodDetailActivity(item)
                    }
                }
            }

            val decoration = DividerItemDecoration(this@FoodListActivity,
                    DividerItemDecoration.VERTICAL).apply {
                setDrawable(getDrawable(R.drawable.recycler_transparent_divider)!!)
            }

            addItemDecoration(decoration)
            setHasFixedSize(false)
            layoutManager = LinearLayoutManager(this@FoodListActivity,
                    LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun startFoodDetailActivity(food: Food) {
        //TODO("not implemented")
    }
}
