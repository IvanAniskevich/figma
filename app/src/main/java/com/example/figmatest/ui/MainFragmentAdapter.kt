package com.example.figmatest.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.figmatest.R
import com.example.figmatest.databinding.ItemInRvBinding
import com.example.figmatest.domein.ItemModel

class MainFragmentAdapter(val clickListner: ClickListner) :
    RecyclerView.Adapter<MainFragmentAdapter.MainFragmentViewHolder>() {

    var adapterList: List<ItemModel> = listOf()
    var selected : ItemModel? = null

    class MainFragmentViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemInRvBinding.bind(view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainFragmentViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_in_rv, parent, false)
        return MainFragmentViewHolder(view)
    }

    override fun onBindViewHolder(holder: MainFragmentViewHolder, position: Int) {
        val item = adapterList[position]
        with(holder.binding) {
            smallPoster.load(item.poster)
            if (item == selected){
                smallPoster.alpha = 1f
            } else {
                smallPoster.alpha = 0.5f
            }
            smallPoster.setOnClickListener {
                clickListner.onClick(item)
            }
        }
    }

    override fun getItemCount(): Int {
        return adapterList.size
    }

    fun setList(list: List<ItemModel>) {
        adapterList = list
        notifyDataSetChanged()
    }

    fun setSelectedVar(item: ItemModel){
        selected = item
        notifyDataSetChanged()
    }

    interface ClickListner {
        fun onClick(item: ItemModel)
    }


}