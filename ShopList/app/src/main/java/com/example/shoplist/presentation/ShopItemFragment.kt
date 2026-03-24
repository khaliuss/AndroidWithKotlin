package com.example.shoplist.presentation

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.shoplist.R
import com.example.shoplist.domain.ShopItem
import com.example.shoplist.presentation.viewmodel.ShopItemViewModel
import com.google.android.material.textfield.TextInputLayout

class ShopItemFragment : Fragment() {

    private lateinit var viewModeL: ShopItemViewModel
    private lateinit var onEditingFinishListener: OnEditingFinishListener
    private lateinit var nameTIL: TextInputLayout
    private lateinit var countTIL: TextInputLayout
    private lateinit var nameEt: EditText
    private lateinit var countEt: EditText
    private lateinit var saveBt: Button

    private var screenMode = UNKNOWN_MODE
    private var shopItemId = ShopItem.UNDEFINED_ID

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnEditingFinishListener){
            onEditingFinishListener = context
        }else{
            throw RuntimeException("Activity must implement  OnEditingFinishListener")
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseParams()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_shop_item, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModeL = ViewModelProvider(this)[ShopItemViewModel::class.java]
        setUpView(view)
        errorReactingRealization()
        launchRightMode()


        viewModeL.closeActivityLD.observe(viewLifecycleOwner) {
            onEditingFinishListener.onEditingFinish()
        }
    }

    private fun launchRightMode() {
        when (screenMode) {
            EDIT_MODE -> launchEditMode()
            ADD_MODE -> launchAddMode()
        }
    }


    private fun setUpView(view: View) {
        nameTIL = view.findViewById<TextInputLayout>(R.id.til_name)
        countTIL = view.findViewById<TextInputLayout>(R.id.til_count)
        nameEt = view.findViewById<EditText>(R.id.et_name)
        countEt = view.findViewById<EditText>(R.id.et_count)
        saveBt = view.findViewById<Button>(R.id.save_button)
    }

    private fun parseParams() {
        val args = requireArguments()
        if (!args.containsKey(SCREEN_MODE)) {
            throw RuntimeException("Param screen mode is absent")
        }
        val mode = args.getString(SCREEN_MODE)
        if (mode != EDIT_MODE && mode != ADD_MODE) {
            throw RuntimeException("Unknown screen mode $mode")
        }
        screenMode = mode
        if (screenMode == EDIT_MODE) {
            if (!args.containsKey(SHOP_ITEM_ID)) {
                throw RuntimeException("Param shop item id is absent")
            }
            shopItemId = args.getInt(SHOP_ITEM_ID, ShopItem.UNDEFINED_ID)
        }

    }

    private fun launchAddMode() {
        saveBt.setOnClickListener {
            val name = nameEt.text?.toString()
            val count = countEt.text?.toString()
            viewModeL.addNewShopItem(name, count)
        }
    }

    private fun launchEditMode() {
        viewModeL.getShopItem(shopItemId)
        viewModeL.getShopItemLD.observe(viewLifecycleOwner) {
            nameEt.setText(it.title)
            countEt.setText(it.count.toString())
        }

        saveBt.setOnClickListener {
            val name = nameEt.text.toString()
            val count = countEt.text.toString()
            viewModeL.editShopItem(name,count)
        }
    }

    private fun errorReactingRealization() {
        viewModeL.errorInputNameLD.observe(viewLifecycleOwner) { isError ->
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

        viewModeL.errorInputCountLD.observe(viewLifecycleOwner) { isError ->
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

    interface  OnEditingFinishListener{

        fun onEditingFinish()

    }

    companion object {
        const val SHOP_ITEM_ID = "ShopItemId"
        const val SCREEN_MODE = "ScreenMode"
        const val ADD_MODE = "AddMode"
        const val EDIT_MODE = "EditMode"
        const val UNKNOWN_MODE = "UnknownMode"

        fun newInstanceAddItem(): ShopItemFragment {
            return ShopItemFragment().apply {
                arguments = Bundle().apply {
                    putString(SCREEN_MODE, ADD_MODE)
                }
            }
        }

        fun newInstanceEditItem(shopId: Int): ShopItemFragment {
            return ShopItemFragment().apply {
                arguments = Bundle().apply {
                    putString(SCREEN_MODE, EDIT_MODE)
                    putInt(SHOP_ITEM_ID, shopId)
                }
            }
        }

    }

}