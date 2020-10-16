package com.simplx.apps.ysolutiontask.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.simplx.apps.ysolutiontask.R
import com.simplx.apps.ysolutiontask.pojo.Food
import com.squareup.picasso.Picasso
import kotlinx.coroutines.supervisorScope

class FoodAdapter(private val onFoodClickListener: OnFoodClickListener) :
    RecyclerView.Adapter<FoodAdapter.FoodViewHolder>() {

    private var foodList: ArrayList<Food> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {

        return FoodViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.food_list_item, parent, false), onFoodClickListener
        )
    }

    override fun getItemCount(): Int {
        return foodList.size
    }

    fun setList(list: ArrayList<Food>) {
        this.foodList = list
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {

        Picasso.get()
            .load(foodList[position].thumb)
            .into(holder.image)

        holder.name?.text = foodList[position].name
        holder.content?.text = foodList[position].headline
    }

    class FoodViewHolder(itemView: View, listener: OnFoodClickListener) :
        RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        private var onFoodClickListener: OnFoodClickListener? = null

        var image: ImageView? = null
        var name: TextView? = null
        var content: TextView? = null

        init {

            image = itemView.findViewById(R.id.foodImgId)
            name = itemView.findViewById(R.id.foodNameId)
            content = itemView.findViewById(R.id.foodContentId)

            this.onFoodClickListener = listener
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            onFoodClickListener?.onFoodClick(adapterPosition)
        }

    }

    interface OnFoodClickListener {
        fun onFoodClick(position: Int)
    }

}