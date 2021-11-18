package com.awscherb.cardkeeper.ui.cards

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.awscherb.cardkeeper.R
import com.awscherb.cardkeeper.data.model.ScannedCode
import com.google.zxing.BarcodeFormat.AZTEC
import com.google.zxing.BarcodeFormat.DATA_MATRIX
import com.google.zxing.BarcodeFormat.QR_CODE
import com.google.zxing.WriterException
import com.journeyapps.barcodescanner.BarcodeEncoder


class CardsAdapter constructor(
    private val context: Context,
    private val onClickListener: (ScannedCode) -> Unit,
    private val deleteListener: (ScannedCode) -> Unit
) : RecyclerView.Adapter<CardViewHolder>() {

    private val encoder: BarcodeEncoder = BarcodeEncoder()

    var items: List<ScannedCode> = arrayListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = items.size

    //================================================================================
    // Adapter methods
    //================================================================================

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CardViewHolder(
        LayoutInflater.from(context).inflate(
            R.layout.adapter_code,
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val item = items[position]
        holder.apply {

            // Set title
            codeTitle.text = item.title

            // Set image scaleType according to barcode type
            when (item.format) {
                QR_CODE, AZTEC, DATA_MATRIX -> codeImage.scaleType = ImageView.ScaleType.FIT_CENTER
                else -> codeImage.scaleType = ImageView.ScaleType.FIT_XY
            }

            // Load image
            try {
                codeImage.setImageBitmap(
                    encoder.encodeBitmap(item.text, item.format, 200, 200)
                )
            } catch (e: WriterException) {
                e.printStackTrace()
            }

            itemView.setOnClickListener { onClickListener(item) }

            // Setup delete
            itemView.setOnLongClickListener {
                deleteListener(item)
                true
            }
        }


    }

}

class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val codeTitle: TextView = itemView.findViewById(R.id.adapter_card_title)
    val codeImage: ImageView = itemView.findViewById(R.id.adapter_card_image)

}