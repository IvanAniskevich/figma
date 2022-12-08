package com.example.figmatest.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.figmatest.domein.GetListOfItemModelUseCase
import com.example.figmatest.domein.ItemModel
import kotlinx.coroutines.launch

class ItemViewModel(
    private val getListOfItemModelUseCase: GetListOfItemModelUseCase
) : ViewModel() {

    private var _listOfItemModel = MutableLiveData<List<ItemModel>>()
    val listOfItemModel: LiveData<List<ItemModel>> get() = _listOfItemModel

    private var _selected = MutableLiveData<ItemModel>()
    val selected: LiveData<ItemModel> get() = _selected

    init {
        getList()
        Log.w("wtf","viewModel list = ${_listOfItemModel.value.toString()}")
    }

    fun getList() {
        viewModelScope.launch {
            try {
                val list =  getListOfItemModelUseCase()
                _listOfItemModel.value = list
                _selected.value = list.first()
            } catch (e: Exception) {
                _listOfItemModel.value = emptyList()
            }
        }
    }

    fun setSelected(item: ItemModel){
        _selected.value = item
    }
}