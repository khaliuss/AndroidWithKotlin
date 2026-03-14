package com.example.shoplist.presentation

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.shoplist.R
import com.example.shoplist.domain.ShopItem

class ShopListAdapter : RecyclerView.Adapter<ShopListAdapter.ShopItemViewHolder>() {

    private var count = 0

    companion object {
        const val RECYCLER_VIEW = "recycler view"
        const val VIEW_TYPE_ENABLE = 1
        const val VIEW_TYPE_DISABLE = -1

        const val MAX_POOL_SIZE = 13
    }

    var adapterList = listOf<ShopItem>()
        set(value) {
            val diffCallback = ShopListDiffCallback(adapterList,value)
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            diffResult.dispatchUpdatesTo(this)
            field = value
        }

    var onShopItemLongClick:((ShopItem)-> Unit)? = null
    var onShopItemClick:((id:Int)-> Unit)? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ShopItemViewHolder {
        Log.d(RECYCLER_VIEW, "onCreateViewHolder, count: ${++count}")
        val resLayout = when (viewType) {
            VIEW_TYPE_ENABLE -> R.layout.item_shop_enabled
            VIEW_TYPE_DISABLE -> R.layout.item_shop_disabled
            else -> throw RuntimeException("Unknown view type: $viewType")
        }


        val view = LayoutInflater.from(parent.context).inflate(
            resLayout,
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
            onShopItemLongClick?.invoke(item)
            true
        }

        holder.itemView.setOnClickListener {
            onShopItemClick?.invoke(item.id)
        }

    }



    override fun getItemViewType(position: Int): Int {
        val itemEnable = adapterList[position].isEnable
        return if (itemEnable) {
            VIEW_TYPE_ENABLE
        } else {
            VIEW_TYPE_DISABLE
        }

    }

    override fun getItemCount(): Int {
        return adapterList.size
    }


    class ShopItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTv = itemView.findViewById<TextView>(R.id.tv_name)
        val countTv = itemView.findViewById<TextView>(R.id.tv_count)
    }
}