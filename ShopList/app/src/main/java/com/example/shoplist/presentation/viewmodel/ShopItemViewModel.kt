package com.example.shoplist.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoplist.data.ShopItemRepositoryImpl
import com.example.shoplist.domain.AddShopItemUseCase
import com.example.shoplist.domain.EditShopItemUseCase
import com.example.shoplist.domain.GetShopItemUseCase
import com.example.shoplist.domain.ShopItem
import kotlinx.coroutines.launch

class ShopItemViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = ShopItemRepositoryImpl(application)

    private val getShopItemUseCase = GetShopItemUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)
    private val addShopItemUseCase = AddShopItemUseCase(repository)

    private val _errorInputNameLD = MutableLiveData<Boolean>()
    val errorInputNameLD: LiveData<Boolean>
        get() = _errorInputNameLD

    private val _errorInputCountLD = MutableLiveData<Boolean>()
    val errorInputCountLD: LiveData<Boolean>
        get() = _errorInputCountLD

    private val _getShopItemLD = MutableLiveData<ShopItem>()
    val getShopItemLD: LiveData<ShopItem>
        get() = _getShopItemLD

    private val _closeActivityMLD = MutableLiveData<Unit>()
    val closeActivityLD: LiveData<Unit>
        get() = _closeActivityMLD


    fun getShopItem(shopItemId: Int) {
        viewModelScope.launch {
            val item = getShopItemUseCase.getShopItem(shopItemId)
            _getShopItemLD.value = item
        }

    }


    fun addNewShopItem(inputTitle: String?, inputCount: String?) {
        val title = parseTitle(inputTitle)
        val count = parseCount(inputCount)
        val isValid = validateInput(title, count)
        if (isValid) {
            viewModelScope.launch {
                val newShopItem = ShopItem(title = title, count = count)
                addShopItemUseCase.addShopItem(newShopItem)
                finishWork()
            }
        }
    }

    fun editShopItem(inputTitle: String?, inputCount: String?) {
        val title = parseTitle(inputTitle)
        val count = parseCount(inputCount)
        val isValid = validateInput(title, count)
        if (isValid) {
            viewModelScope.launch {
                _getShopItemLD.value?.let {
                    val item = it.copy(title = title, count = count)
                    editShopItemUseCase.editShopItem(item)
                    finishWork()
                }
            }

        }

    }

    private fun finishWork() {
        _closeActivityMLD.value = Unit
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
            _errorInputNameLD.value = true
            valid = false
        }

        if (count <= 0) {
            _errorInputCountLD.value = true
            valid = false
        }

        return valid
    }

    fun eraseNameInputError() {
        _errorInputNameLD.value = false
    }

    fun eraseCountInputError() {
        _errorInputCountLD.value = false
    }


}