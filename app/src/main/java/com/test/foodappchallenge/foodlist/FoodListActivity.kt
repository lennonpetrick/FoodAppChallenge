package com.test.foodappchallenge.foodlist

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.util.Pair
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.test.foodappchallenge.R
import com.test.foodappchallenge.domain.model.Food
import com.test.foodappchallenge.fooddetail.FoodDetailActivity
import com.test.foodappchallenge.foodlist.di.DaggerFoodListComponent
import com.test.foodappchallenge.foodlist.di.FoodListModule
import kotlinx.android.synthetic.main.activity_food_list.*
import javax.inject.Inject

class FoodListActivity : AppCompatActivity(), FoodListContract.View {

    @Inject lateinit var presenter: FoodListContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
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
        refreshLayout.isRefreshing = true
    }

    override fun dismissLoading() {
        refreshLayout.isRefreshing = false
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
                    override fun onItemClick(itemView: View, item: Food, position: Int) {
                        startFoodDetailActivity(itemView, item)
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

    private fun startFoodDetailActivity(itemView: View, food: Food) {
        val options = createTransitionOptions(itemView)
        val intent = Intent(this, FoodDetailActivity::class.java)
        intent.putExtra(Food::class.java.name, food)
        startActivity(intent, options.toBundle())
    }

    private fun createTransitionOptions(itemView: View): ActivityOptionsCompat {
        return ActivityOptionsCompat.makeSceneTransitionAnimation(this,
                Pair(itemView.findViewById(R.id.ivFoodPicture), "food_picture"))
    }
}
