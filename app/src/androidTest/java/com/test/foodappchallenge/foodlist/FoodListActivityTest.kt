package com.test.foodappchallenge.foodlist

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import android.support.test.espresso.intent.Intents.intended
import android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent
import android.support.test.espresso.intent.rule.IntentsTestRule
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.runner.AndroidJUnit4
import com.test.foodappchallenge.R
import com.test.foodappchallenge.fooddetail.FoodDetailActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FoodListActivityTest {

    @Rule
    @JvmField
    val mActivityRule = IntentsTestRule(FoodListActivity::class.java)

    @Test
    fun callFoodDetailScreen() {
        onView(withId(R.id.recyclerView))
                .perform(scrollToPosition<ItemRecyclerAdapter.ViewHolder>(3))
                .perform(RecyclerViewActions
                        .actionOnItemAtPosition<ItemRecyclerAdapter.ViewHolder>(3, click()))

        intended(hasComponent(FoodDetailActivity::class.java.name))
    }

    @Test
    fun favoriteFood() {
        onView(withId(R.id.recyclerView))
                .perform(scrollToPosition<ItemRecyclerAdapter.ViewHolder>(3))
                .perform(RecyclerViewActions
                        .actionOnItemAtPosition<ItemRecyclerAdapter.ViewHolder>(3, click()))

        onView(withId(R.id.fabFavorite))
                .perform(click())
    }
}