package com.example.shoplist.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.shoplist.R
import com.example.shoplist.domain.ShopItem
import com.google.android.material.textfield.TextInputLayout

class ShopItemActivity : AppCompatActivity() {

    private lateinit var viewModeL: ShopItemViewModel
    private lateinit var nameTIL: TextInputLayout
    private lateinit var countTIL: TextInputLayout
    private lateinit var nameEt: EditText
    private lateinit var countEt: EditText
    private lateinit var saveBt: Button

    private var screenMode = UNKNOWN_MODE
    private var shopItemId = ShopItem.UNDEFINED_ID


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_item)
        parseIntent()
        setUpView()
        viewModeL = ViewModelProvider(this)[ShopItemViewModel::class.java]

        when(screenMode){
            EDIT_MODE -> launchEditMode()
            ADD_MODE -> launchAddMode()
        }

        errorReactingRealization()

        viewModeL.closeActivityLD.observe(this) {
            finish()
        }


    }

    private fun setUpView() {
        nameTIL = findViewById<TextInputLayout>(R.id.til_name)
        countTIL = findViewById<TextInputLayout>(R.id.til_count)
        nameEt = findViewById<EditText>(R.id.et_name)
        countEt = findViewById<EditText>(R.id.et_count)
        saveBt = findViewById<Button>(R.id.save_button)
    }
    private fun parseIntent() {
        if (!intent.hasExtra(SCREEN_MODE_EXTRA)) {
            throw RuntimeException("Param screen mode is absent")
        }
        val mode = intent.getStringExtra(SCREEN_MODE_EXTRA)
        if (mode != EDIT_MODE && mode != ADD_MODE) {
            throw RuntimeException("Unknown screen mode $mode")
        }
        screenMode = mode
        if (screenMode == EDIT_MODE) {
            if (!intent.hasExtra(SHOP_ITEM_ID_EXTRA)) {
                throw RuntimeException("Param shop item id is absent")
            }
            shopItemId = intent.getIntExtra(SHOP_ITEM_ID_EXTRA, ShopItem.UNDEFINED_ID)
        }

    }
    private fun launchAddMode() {
        saveBt.setOnClickListener { addShopItem() }
    }
    private fun addShopItem() {
        val name = nameEt.text?.toString()
        val count = countEt.text?.toString()
        viewModeL.addNewShopItem(name, count)
    }
    private fun launchEditMode() {
        viewModeL.getShopItem(shopItemId)
        viewModeL.getShopItemLD.observe(this) {
            nameEt.setText(it.title)
            countEt.setText(it.count.toString())
        }
        saveBt.setOnClickListener { editShopItem() }
    }
    private fun editShopItem() {
        val name = nameEt.text?.toString()
        val count = countEt.text?.toString()
        viewModeL.editShopItem(name, count)
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

    companion object {
        const val SHOP_ITEM_ID_EXTRA = "ShopItemId"
        const val SCREEN_MODE_EXTRA = "ScreenMode"
        const val ADD_MODE = "AddMode"
        const val EDIT_MODE = "EditMode"
        const val UNKNOWN_MODE = "UnknownMode"

        fun addShopItemMode(context: Context): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(SCREEN_MODE_EXTRA, ADD_MODE)
            return intent
        }

        fun editShopItemMode(context: Context, shopItemId: Int): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(SCREEN_MODE_EXTRA, EDIT_MODE)
            intent.putExtra(SHOP_ITEM_ID_EXTRA, shopItemId)
            return intent
        }
    }







}