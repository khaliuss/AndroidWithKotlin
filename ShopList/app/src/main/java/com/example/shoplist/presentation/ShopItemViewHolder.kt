package com.example.shoplist.presentation

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.shoplist.R

class ShopItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val titleTv = itemView.findViewById<TextView>(R.id.tv_name)
    val countTv = itemView.findViewById<TextView>(R.id.tv_count)
}