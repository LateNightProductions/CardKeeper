package com.awscherb.cardkeeper.ui.create

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.awscherb.cardkeeper.R

class CodeTypesAdapter(
    private val context: Context,
    private val onClick: (CreateType) -> Unit
) : RecyclerView.Adapter<CodeTypesViewHolder>() {

    override fun getItemCount() = TYPES.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CodeTypesViewHolder =
        CodeTypesViewHolder(
            LayoutInflater.from(context).inflate(R.layout.adapter_code_type, parent, false)
        )


    override fun onBindViewHolder(holder: CodeTypesViewHolder, position: Int) {
        holder.apply {
            codeName.text = TYPES[position].title
            itemView.setOnClickListener { onClick(TYPES[position]) }
        }
    }

    companion object {
        private val TYPES =
            arrayOf<CreateType>(
                CreateType.Aztec,
                CreateType.Code128,
                CreateType.DataMatrix,
                CreateType.PDF417,
                CreateType.QRCode
            )
    }
}

class CodeTypesViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    val codeName: TextView = itemView.findViewById(R.id.adapter_code_type_name)
}