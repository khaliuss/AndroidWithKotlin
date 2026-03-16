package com.example.shoplist.presentation

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.shoplist.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var shopListAdapter: ShopListAdapter
    private lateinit var addNewShopItemFAB: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        addNewShopItemFAB = findViewById(R.id.addNewItemFAB)
        setUpRecyclerView()

        addNewShopItemFAB.setOnClickListener {
            val intent = ShopItemActivity.newIntentAddItem(this)
            startActivity(intent)
        }

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        viewModel.shopItems.observe(this) {
            shopListAdapter.submitList(it)
        }


    }


    private fun setUpRecyclerView() {
        val itemListRv = findViewById<RecyclerView>(R.id.itemListRV)
        with(itemListRv) {
            shopListAdapter = ShopListAdapter()
            adapter = shopListAdapter
            recycledViewPool.setMaxRecycledViews(
                ShopListAdapter.VIEW_TYPE_ENABLE,
                ShopListAdapter.MAX_POOL_SIZE
            )
            recycledViewPool.setMaxRecycledViews(
                ShopListAdapter.VIEW_TYPE_DISABLE,
                ShopListAdapter.MAX_POOL_SIZE
            )
        }
        setUpLongClickListener()

        setUpClickListener()


        setUpSwipeListener(itemListRv)


    }

    private fun setUpSwipeListener(itemListRv: RecyclerView?) {
        val callback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(
                viewHolder: RecyclerView.ViewHolder,
                direction: Int
            ) {
                val itemShop = shopListAdapter.currentList[viewHolder.absoluteAdapterPosition]
                viewModel.removeItem(itemShop)

            }

        }

        ItemTouchHelper(callback).attachToRecyclerView(itemListRv)
    }

    private fun setUpClickListener() {
        shopListAdapter.onShopItemClick = {
            val intent  = ShopItemActivity.newIntentEditItem(this,it)
            startActivity(intent)
        }
    }

    private fun setUpLongClickListener() {
        shopListAdapter.onShopItemLongClick = {
            viewModel.changeEnable(it)
        }
    }


}