package com.example.shoplist.presentation

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.shoplist.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainAcztivity : AppCompatActivity(), ShopItemFragment.OnEditingFinishListener {

    private lateinit var viewModel: MainViewModel
    private lateinit var shopListAdapter: ShopListAdapter
    private lateinit var addNewShopItemFAB: FloatingActionButton
    private var shopItemContainer: FragmentContainerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        shopItemContainer = findViewById(R.id.shop_item_container)

        addNewShopItemFAB = findViewById(R.id.addNewItemFAB)
        setUpRecyclerView()

        addNewShopItemFAB.setOnClickListener {
            if (isOnePaneMode()) {
                val intent = ShopItemActivity.newIntentAddItem(this)
                startActivity(intent)
            } else {
                launchFragment(ShopItemFragment.newInstanceAddItem())
            }
        }





        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        viewModel.shopItems.observe(this) {
            shopListAdapter.submitList(it)
        }


    }

    override fun onEditingFinish() {
        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
        supportFragmentManager.popBackStack()
    }

    private fun isOnePaneMode(): Boolean {
        return shopItemContainer == null
    }

    private fun launchFragment(fragment: Fragment) {
        isOnePaneMode()
        supportFragmentManager.popBackStack()
        supportFragmentManager.beginTransaction()
            .replace(R.id.shop_item_container, fragment)
            .addToBackStack(null)
            .commit()
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
            if (isOnePaneMode()) {
                val intent = ShopItemActivity.newIntentEditItem(this, it)
                startActivity(intent)
            } else {
                launchFragment(ShopItemFragment.newInstanceEditItem(it))
            }
        }
    }

    private fun setUpLongClickListener() {
        shopListAdapter.onShopItemLongClick = {
            viewModel.changeEnable(it)
        }
    }


}