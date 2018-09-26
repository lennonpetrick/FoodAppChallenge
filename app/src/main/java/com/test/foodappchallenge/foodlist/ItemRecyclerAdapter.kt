package com.test.foodappchallenge.foodlist

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import com.test.foodappchallenge.CircleTransformation
import com.test.foodappchallenge.R
import com.test.foodappchallenge.domain.model.Food
import kotlinx.android.synthetic.main.layout_content_user.view.*
import kotlinx.android.synthetic.main.layout_item_recycler_view.view.*

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
                    view,
                    items[holder.adapterPosition],
                    holder.adapterPosition)
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
                    Picasso.get()
                            .load(getDrawableId(itemView.context, it))
                            .transform(CircleTransformation())
                            .into(ivUserPicture)
                }

                tvUserName.text = user.name
            }

            food.image?.let {
                Picasso.get()
                        .load(getDrawableId(itemView.context, it))
                        .into(ivFoodPicture)
            }

            tvDateTime.text = food.dateTime
            tvFavCount.text = food.favoriteCount.toString()
            tvFoodDescription.text = food.description
        }

        private fun getDrawableId(context: Context, key: String): Int {
            return context.resources
                    .getIdentifier(key.toLowerCase(), "drawable", context.packageName)
        }
    }

    interface OnItemClickListener {
        fun onItemClick(itemView: View, item: Food, position: Int)
    }
}