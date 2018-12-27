package com.awscherb.cardkeeper.ui.base

import androidx.recyclerview.widget.RecyclerView

import com.awscherb.cardkeeper.data.model.BaseModel

abstract class BaseAdapter<T, VH> constructor(
    private var objects: List<T> = ArrayList()
) : RecyclerView.Adapter<VH>() where T : BaseModel, VH : RecyclerView.ViewHolder {

    override fun onBindViewHolder(holder: VH, position: Int) {
        onBindViewHolder(holder, getItem(position))
    }

    abstract fun onBindViewHolder(holder: VH, item: T)

    fun getItem(position: Int): T = objects[position]

    override fun getItemCount(): Int = objects.size

    fun swapObjects(newObjects: List<T>) {
        objects = newObjects
        notifyDataSetChanged()
    }

}
