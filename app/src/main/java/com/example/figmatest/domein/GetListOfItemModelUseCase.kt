package com.example.figmatest.domein

import android.util.Log

class GetListOfItemModelUseCase(private val repository: Repository) {
   suspend  fun invoke():List<ItemModel>{
       val r= repository.getListItemModel()
       Log.w("wtf","list in useCase = $r")
        return r
    }
}