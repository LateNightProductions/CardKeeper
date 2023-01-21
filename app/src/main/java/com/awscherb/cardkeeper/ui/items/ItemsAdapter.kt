package com.awscherb.cardkeeper.ui.items

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.awscherb.cardkeeper.R
import com.awscherb.cardkeeper.data.CardKeeperDatabase
import com.awscherb.cardkeeper.data.model.PkPassModel
import com.awscherb.cardkeeper.data.model.SavedItem
import com.awscherb.cardkeeper.data.model.ScannedCodeModel
import com.awscherb.cardkeeper.data.model.parseHexColor
import com.bumptech.glide.Glide
import com.google.zxing.BarcodeFormat.AZTEC
import com.google.zxing.BarcodeFormat.DATA_MATRIX
import com.google.zxing.BarcodeFormat.QR_CODE
import com.google.zxing.WriterException
import com.journeyapps.barcodescanner.BarcodeEncoder
import java.io.File

class ItemsAdapter constructor(
    private val context: Context,
    private val onClickListener: (SavedItem) -> Unit,
    private val deleteListener: (SavedItem) -> Unit
) : RecyclerView.Adapter<SavedItemViewHolder>() {

    companion object {
        private val TYPE_SCANNED_CODE = 0
        private val TYPE_PKPASS = 1
        private val encoder: BarcodeEncoder = BarcodeEncoder()
    }

    var items: List<SavedItem> = arrayListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = items.size

    //================================================================================
    // Adapter methods
    //================================================================================

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is ScannedCodeModel -> TYPE_SCANNED_CODE
            is PkPassModel -> TYPE_PKPASS
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedItemViewHolder {
        return when (viewType) {
            TYPE_SCANNED_CODE -> return ScannedCodeViewHolder(
                LayoutInflater.from(context).inflate(
                    R.layout.adapter_code,
                    parent,
                    false
                )
            )
            TYPE_PKPASS -> return PkPassViewHolder(
                LayoutInflater.from(context).inflate(
                    R.layout.adapter_pkpass,
                    parent,
                    false
                )
            )
            else -> throw IllegalArgumentException("No such viewtype $viewType")
        }
    }

    override fun onBindViewHolder(holder: SavedItemViewHolder, position: Int) {
        when (holder) {
            is ScannedCodeViewHolder -> bindCardViewHolder(holder, items[position] as ScannedCodeModel)
            is PkPassViewHolder -> bindPkPassViewHolder(holder, items[position] as PkPassModel)
        }
    }

    private fun bindCardViewHolder(holder: ScannedCodeViewHolder, code: ScannedCodeModel) {
        holder.apply {
            // Set title
            codeTitle.text = code.title

            // Set image scaleType according to barcode type
            when (code.format) {
                QR_CODE, AZTEC, DATA_MATRIX -> codeImage.scaleType = ImageView.ScaleType.FIT_CENTER
                else -> codeImage.scaleType = ImageView.ScaleType.FIT_XY
            }

            // Load image
            try {
                codeImage.setImageBitmap(
                    encoder.encodeBitmap(code.text, code.format, 200, 200)
                )
            } catch (e: WriterException) {
                e.printStackTrace()
            }

            itemView.setOnClickListener { onClickListener(code) }

            // Setup delete
            itemView.setOnLongClickListener {
                deleteListener(code)
                true
            }
        }
    }

    private fun bindPkPassViewHolder(holder: PkPassViewHolder, pass: PkPassModel) {
        holder.apply {
            val labelColor = Color.parseColor(pass.labelColor.parseHexColor())
            card.setCardBackgroundColor(Color.parseColor(pass.backgroundColor.parseHexColor()))

            val headers = if (pass.boardingPass?.headerFields?.isNotEmpty() == true)
                pass.boardingPass?.headerFields else pass.boardingPass?.primaryFields

            if (headers?.isNotEmpty() == true) {
                label1.visibility = View.VISIBLE
                label1.setTextColor(labelColor)
                value1.visibility = View.VISIBLE
                value1.setTextColor(labelColor)
                val firstPass = headers[0]
                label1.text = firstPass.label
                value1.text = firstPass.value

                if (headers.size > 1) {
                    label2.visibility = View.VISIBLE
                    label2.setTextColor(labelColor)
                    value2.visibility = View.VISIBLE
                    value2.setTextColor(labelColor)
                    val secondPass = headers[1]
                    label2.text = secondPass.label
                    value2.text = secondPass.value
                } else {
                    label2.visibility = View.GONE
                    value2.visibility = View.GONE
                }
            }

            pass.logoPath?.let {
                Glide.with(this.itemView)
                    .load(File(it))
                    .into(image)
            }
        }
    }
}

sealed class SavedItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

class ScannedCodeViewHolder(itemView: View) : SavedItemViewHolder(itemView) {
    val codeTitle: TextView = itemView.findViewById(R.id.adapter_card_title)
    val codeImage: ImageView = itemView.findViewById(R.id.adapter_card_image)
}

class PkPassViewHolder(itemView: View) : SavedItemViewHolder(itemView) {

    val image: ImageView = itemView.findViewById(R.id.adapter_pkpass_icon)
    val card: CardView = itemView.findViewById(R.id.adapter_pkpass_card)

    val label1: TextView = itemView.findViewById(R.id.adapter_pkpass_header1_label)
    val value1: TextView = itemView.findViewById(R.id.adapter_pkpass_header1_value)


    val label2: TextView = itemView.findViewById(R.id.adapter_pkpass_header2_label)
    val value2: TextView = itemView.findViewById(R.id.adapter_pkpass_header2_value)
}