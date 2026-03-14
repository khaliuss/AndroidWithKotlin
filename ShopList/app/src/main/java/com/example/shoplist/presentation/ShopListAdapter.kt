package com.example.shoplist.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.shoplist.R
import com.example.shoplist.domain.ShopItem

class ShopListAdapter : RecyclerView.Adapter<ShopListAdapter.ShopItemViewHolder>() {

    var adapterList = listOf<ShopItem>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ShopItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_shop_enabled,
            parent,
            false
        )

        return ShopItemViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ShopItemViewHolder,
        position: Int
    ) {
        val item = adapterList[position]
        holder.titleTv.text = item.title
        holder.countTv.text = item.count.toString()

        holder.itemView.setOnLongClickListener {
            TODO("change enable ")
            true
        }
    }

    override fun getItemCount(): Int {
       return adapterList.size
    }


    class ShopItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val titleTv = itemView.findViewById<TextView>(R.id.tv_name)
        val countTv = itemView.findViewById<TextView>(R.id.tv_count)
    }
}