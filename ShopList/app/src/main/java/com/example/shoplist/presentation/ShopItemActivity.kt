package com.example.shoplist.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import com.example.shoplist.R
import com.google.android.material.textfield.TextInputLayout

class ShopItemActivity : AppCompatActivity() {

    private lateinit var viewModeL: ShopItemViewModel
    private lateinit var nameTIL: TextInputLayout
    private lateinit var countTIL: TextInputLayout
    private lateinit var nameEt: EditText
    private lateinit var countEt: EditText
    private lateinit var saveBt: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_item)
        setUpView()
        viewModeL = ViewModelProvider(this)[ShopItemViewModel::class.java]

        saveBt.setOnClickListener {
            //if(addMode){addShopItem}
            //else{editShopItem}
        }

        errorReactingRealization()
    }

    private fun errorReactingRealization() {
        viewModeL.errorInputNameLD.observe(this) { isError ->
            if (isError) {
                nameTIL.error = "Invalid data"
            } else {
                nameTIL.error = null
            }
        }

        nameEt.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
                viewModeL.eraseNameInputError()
            }

        })

        viewModeL.errorInputCountLD.observe(this) { isError ->
            if (isError) {
                countTIL.error = "Invalid data"
            } else {
                countTIL.error = null
            }
        }

        countEt.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
                viewModeL.eraseCountInputError()
            }

        })
    }

    private fun addShopItem(){
        val name = nameEt.text.toString()
        val count = countEt.text.toString()
        viewModeL.addNewShopItem(name,count)
    }

    private fun editShopItem(){
        val name = nameEt.text.toString()
        val count = countEt.text.toString()
        viewModeL.addNewShopItem(name,count)
    }
    private fun setUpView() {
        nameTIL = findViewById<TextInputLayout>(R.id.til_name)
        countTIL = findViewById<TextInputLayout>(R.id.til_count)
        nameEt = findViewById<EditText>(R.id.et_name)
        countEt = findViewById<EditText>(R.id.et_count)
        saveBt = findViewById<Button>(R.id.save_button)
    }

    companion object {
        const val SHOP_ITEM_ID = "ShopItemId"

        fun addShopItemMode(context: Context): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            return intent
        }

        fun editShopItemMode(context: Context, shopItemId: Int): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(SHOP_ITEM_ID, shopItemId)
            return intent
        }
    }
}