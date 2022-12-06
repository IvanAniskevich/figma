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
    val i = ItemModel(
        id = "41618957-76c2-4703-a307-4f31adfda40b",
        photo = "https://storage.googleapis.com/assets-stage-bgrem-deelvin-com/bg/videos/posters/41618957-76c2-4703-a307-4f31adfda40b/poster-ny6yekz4.jpg",
        poster = "https://storage.googleapis.com/assets-stage-bgrem-deelvin-com/bg/videos/posters/41618957-76c2-4703-a307-4f31adfda40b/poster-small-g5bzo8qk.jpg",
        video = "https://storage.googleapis.com/assets-stage-bgrem-deelvin-com/bg/videos/pexels-jess-vide-5230241.mp4"
    )
    val l = listOf(i, i, i, i, i, i)


    private var _listOfItemModel = MutableLiveData<List<ItemModel>>()
    val listOfItemModel: LiveData<List<ItemModel>> get() = _listOfItemModel

    init {
        getList()
        Log.w("wtf","viewModel list = ${_listOfItemModel.value.toString()}")
    }

    fun getList() {
        viewModelScope.launch {
            try {
                _listOfItemModel.value = getListOfItemModelUseCase.invoke()
            } catch (e: Exception) {
                _listOfItemModel.value = l
            }
        }
    }
}