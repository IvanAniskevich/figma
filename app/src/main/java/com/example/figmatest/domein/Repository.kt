package com.example.figmatest.domein

import android.util.Log
import com.example.figmatest.data.ItemJson
import com.example.figmatest.data.RetrofitInstance

class Repository{
    suspend fun getListItemModel(): List<ItemModel>{
//        Log.w("wtf","list start")
        try{
            val list = RetrofitInstance.API_SERVICES.getItems()
//            Log.w("wtf","list = ${list.toString()}")
            return list.map { mapToItemModel(it) }
        } catch (e:Exception){
            Log.w("wtf","list exception = $e")
            return emptyList()
        }


//        return RetrofitInstance.API_SERVICES.getItems().map { mapToItemModel(itemJson = it) }
    }
    private fun mapToItemModel(itemJson: ItemJson): ItemModel{
        return ItemModel(
            id = itemJson.id,
            photo = itemJson.poster_url,
            poster = itemJson.small_poster_url,
            video = itemJson.file_url
        )
    }

}