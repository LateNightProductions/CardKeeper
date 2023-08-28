package com.awscherb.cardkeeper.ui.pkpassDetail

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.awscherb.cardkeeper.R

class BackItemAdapter : RecyclerView.Adapter<BackItemAdapter.ViewHolder>() {

    var items: List<Pair<String, String>> = mutableListOf()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.adapter_backing_field, parent, false)
        )
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val (label, value) = items[position]
        holder.label.text = label
        holder.value.text = value
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val label: TextView = itemView.findViewById(R.id.adapter_backing_field_label)
        val value: TextView = itemView.findViewById(R.id.adapter_backing_field_value)
    }

}