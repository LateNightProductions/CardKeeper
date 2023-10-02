package com.awscherb.cardkeeper.ui.items

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.compose.ui.platform.ComposeView
import androidx.recyclerview.widget.RecyclerView
import com.awscherb.cardkeeper.R
import com.awscherb.cardkeeper.barcode.model.ScannedCodeModel
import com.awscherb.cardkeeper.core.SavedItem
import com.awscherb.cardkeeper.pkpass.model.PkPassModel
import com.awscherb.cardkeeper.pkpass.model.parseHexColor
import com.awscherb.cardkeeper.ui.view.PkPassHeaderView
import com.google.zxing.BarcodeFormat.AZTEC
import com.google.zxing.BarcodeFormat.DATA_MATRIX
import com.google.zxing.BarcodeFormat.QR_CODE
import com.google.zxing.WriterException
import com.journeyapps.barcodescanner.BarcodeEncoder

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
            else -> throw IllegalArgumentException("Unknown type")
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
            is ScannedCodeViewHolder -> bindCardViewHolder(
                holder,
                items[position] as ScannedCodeModel
            )

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
            itemView.setOnClickListener { onClickListener(pass) }
            itemView.setOnLongClickListener {
                deleteListener(pass)
                true
            }
            headerCard.setCardBackgroundColor(pass.backgroundColor.parseHexColor())
            headerView.apply { 
                setContent { 
                    PkPassHeaderView(pass = pass)
                }
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
    val headerCard: CardView = itemView.findViewById(R.id.adapter_pkpass_card)
    val headerView: ComposeView = itemView.findViewById(R.id.adapter_pkpass_header)
}