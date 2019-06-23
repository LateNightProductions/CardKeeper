package com.awscherb.cardkeeper.ui.create

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.awscherb.cardkeeper.R
import kotlinx.android.synthetic.main.adapter_code_type.view.*

class CodeTypesAdapter(
    private val context: Context,
    private val onClick: (CreateType) -> Unit
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        object : RecyclerView.ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.adapter_code_type, parent, false)
        ) {

        }

    override fun getItemCount() = TYPES.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.apply {
            codeName.text = TYPES[position].title
            setOnClickListener { onClick(TYPES[position]) }
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