package com.test.foodappchallenge

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.test.foodappchallenge.domain.model.Food
import kotlinx.android.synthetic.main.layout_item_recycler_view.view.*
import kotlinx.android.synthetic.main.layout_item_recycler_view_header.view.*

class ItemRecyclerAdapter(
        private var items: List<Food>
) : RecyclerView.Adapter<ItemRecyclerAdapter.ViewHolder>() {

    var onItemClickListener: OnItemClickListener? = null

    fun setItems(items: List<Food>) {
        this.items = items
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_item_recycler_view, parent, false)
        return createListener(view, ViewHolder(view))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    private fun createListener(view: View, holder: ViewHolder): ViewHolder {
        view.setOnClickListener {
            onItemClickListener?.onItemClick(
                    items[holder.adapterPosition], holder.adapterPosition)
        }
        return holder
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val ivUserPicture: ImageView = view.ivUserPicture
        private val tvUserName: TextView = view.tvUserName
        private val tvDateTime: TextView = view.tvFoodDateTime
        private val tvFavCount: TextView = view.tvFoodFavCount
        private val ivFoodPicture: ImageView = view.ivFoodPicture
        private val tvFoodDescription: TextView = view.tvFoodDescription

        fun bind(food: Food) {
            food.user?.let { user ->
                user.profileImage?.let {
                    ivUserPicture.setImageResource(it)
                }

                tvUserName.text = user.name
            }

            food.image?.let {
                ivFoodPicture.setImageResource(it)
            }

            tvDateTime.text = food.dateTime
            tvFavCount.text = food.favoriteCount.toString()
            tvFoodDescription.text = food.description
        }
    }

    interface OnItemClickListener {
        fun onItemClick(item: Food, position: Int)
    }
}