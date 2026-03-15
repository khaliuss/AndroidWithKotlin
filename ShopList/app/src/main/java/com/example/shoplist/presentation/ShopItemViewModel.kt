package com.example.shoplist.presentation

import androidx.lifecycle.ViewModel
import com.example.shoplist.data.ShopItemRepositoryImpl
import com.example.shoplist.domain.AddShopItemUseCase
import com.example.shoplist.domain.EditShopItemUseCase
import com.example.shoplist.domain.GetShopItemUseCase
import com.example.shoplist.domain.ShopItem

class ShopItemViewModel : ViewModel() {

    private val repository = ShopItemRepositoryImpl

    private val getShopItemUseCase = GetShopItemUseCase(repository)

    private val editShopItemUseCase = EditShopItemUseCase(repository)
    private val addShopItemUseCase = AddShopItemUseCase(repository)


    fun getShopItem(shopItemId: Int): ShopItem {
        return getShopItemUseCase.getShopItem(shopItemId)
    }

    fun addNewShopItem(inputTitle: String?, inputCount: String?) {
        val title = parseTitle(inputTitle)
        val count = parseCount(inputCount)
        val isValid = validateInput(title, count)
        if (isValid) {
            val newShopItem = ShopItem(title = title, count = count)
            addShopItemUseCase.addShopItem(newShopItem)
        }
    }

    fun changeShopItem(id: Int, inputTitle: String?, inputCount: String?) {
        val title = parseTitle(inputTitle)
        val count = parseCount(inputCount)
        val isValid = validateInput(title, count)
        if (isValid) {
            val newItem = getShopItemUseCase.getShopItem(id).copy(title = title, count = count)
            editShopItemUseCase.editShopItem(newItem)
        }

    }

    private fun parseTitle(title: String?): String {
        return title?.trim() ?: ""
    }

    private fun parseCount(count: String?): Int {
        return try {
            count?.trim()?.toInt() ?: 0
        } catch (e: Exception) {
            0
        }
    }

    private fun validateInput(title: String, count: Int): Boolean {
        var valid = true

        if (title.isBlank()) {
            valid = false
        } else if (count <= 0) {
            valid = false
        }

        return valid
    }

}