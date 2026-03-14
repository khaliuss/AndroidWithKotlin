package com.example.shoplist.presentation

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.shoplist.R
import com.example.shoplist.domain.ShopItem

class ShopListAdapter : RecyclerView.Adapter<ShopListAdapter.ShopItemViewHolder>() {

    companion object{
        const val RECYCLER_VIEW = "recycler view"
    }

    var adapterList = listOf<ShopItem>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ShopItemViewHolder {
        Log.d(RECYCLER_VIEW,"Here: onCreateViewHolder")
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
        Log.d(RECYCLER_VIEW,"Here: onBindViewHolder")
        val item = adapterList[position]
        val status = if (item.isEnable){
            "Active"
        }else{
            "Not active"
        }

        holder.titleTv.text = "${item.title} $status"
        holder.countTv.text = item.count.toString()


        holder.itemView.setOnLongClickListener {
            TODO("change enable ")
            true
        }
        if (!item.isEnable){
            holder.titleTv.setTextColor(ContextCompat.getColor(holder.itemView.context,android.R.color.holo_red_light))
        }
//        for fixing bug with condition we have to add else branch in condition or use OnViewRecycled
//        else{
//            holder.titleTv.setTextColor(ContextCompat.getColor(holder.itemView.context,android.R.color.white))
//        }

    }

    override fun onViewRecycled(holder: ShopItemViewHolder) {
        holder.titleTv.setTextColor(ContextCompat.getColor(holder.itemView.context,android.R.color.white))
    }

    override fun getItemCount(): Int {
       return adapterList.size
    }


    class ShopItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val titleTv = itemView.findViewById<TextView>(R.id.tv_name)
        val countTv = itemView.findViewById<TextView>(R.id.tv_count)
    }
}