package com.awscherb.cardkeeper.ui.cards

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.awscherb.cardkeeper.R
import com.awscherb.cardkeeper.data.model.ScannedCode
import com.awscherb.cardkeeper.ui.base.BaseAdapter
import com.google.zxing.BarcodeFormat.*
import com.google.zxing.WriterException
import com.journeyapps.barcodescanner.BarcodeEncoder
import kotlinx.android.synthetic.main.adapter_code.view.*


class CardsAdapter constructor(
    private val context: Context,
    private val deleteListener: (ScannedCode) -> Unit
) : BaseAdapter<ScannedCode>() {

    private val encoder: BarcodeEncoder = BarcodeEncoder()

    //================================================================================
    // Adapter methods
    //================================================================================

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return object : RecyclerView.ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.adapter_code,
                parent,
                false
            )
        ) {}
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ScannedCode) {
        holder.itemView.apply {

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

            // Setup delete
            setOnLongClickListener {
                deleteListener(item)
                true
            }
        }


    }


}
