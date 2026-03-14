package com.example.shoplist.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.shoplist.R
import com.example.shoplist.domain.ShopItem

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var llShopList: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        llShopList = findViewById(R.id.llShopItems)

        viewModel.shopItems.observe(this) {
            showList(it)
        }


    }

    private fun showList(items: List<ShopItem>) {
        llShopList.removeAllViews()
        for (item in items) {

            val layoutRes = if(item.isEnable){
                R.layout.item_shop_enabled
            }else{
                R.layout.item_shop_disabled
            }

            val view = LayoutInflater.from(this).inflate(
                layoutRes,
                llShopList,
                false
            )
            val name = view.findViewById<TextView>(R.id.tv_name)
            val count = view.findViewById<TextView>(R.id.tv_count)

            name.text = item.title
            count.text = item.count.toString()

            view.setOnLongClickListener {
                viewModel.changeEnable(item)
                true
            }

            llShopList.addView(view)
        }

    }
}