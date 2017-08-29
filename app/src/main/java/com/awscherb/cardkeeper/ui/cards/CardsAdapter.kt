package com.awscherb.cardkeeper.ui.cards

import android.content.Context
import android.support.v7.app.AlertDialog
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import com.awscherb.cardkeeper.R
import com.awscherb.cardkeeper.data.model.ScannedCode
import com.awscherb.cardkeeper.ui.base.BaseAdapter
import com.google.zxing.WriterException
import com.journeyapps.barcodescanner.BarcodeEncoder

import java.util.ArrayList

import butterknife.BindView
import butterknife.ButterKnife

import com.google.zxing.BarcodeFormat.AZTEC
import com.google.zxing.BarcodeFormat.DATA_MATRIX
import com.google.zxing.BarcodeFormat.QR_CODE


class CardsAdapter constructor(
        private val context: Context,
        private val presenter: CardsContract.Presenter)
    : BaseAdapter<ScannedCode, CardsAdapter.ViewHolder>(ArrayList()) {

    private val encoder: BarcodeEncoder = BarcodeEncoder()

    //================================================================================
    // Adapter methods
    //================================================================================

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(context).inflate(R.layout.adapter_code, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, code: ScannedCode) {
        with(holder) {

            // Set title
            title.text = code.title

            // Set image scaleType according to barcode type
            when (code.format) {
                QR_CODE, AZTEC, DATA_MATRIX -> imageView.scaleType = ImageView.ScaleType.FIT_CENTER
                else -> imageView.scaleType = ImageView.ScaleType.FIT_XY
            }

            // Load image
            try {
                imageView.setImageBitmap(
                        encoder.encodeBitmap(code.text, code.format, 200, 200))
            } catch (e: WriterException) {
                e.printStackTrace()
            }

            // Setup delete
            itemView.setOnLongClickListener {
                AlertDialog.Builder(context)
                        .setTitle(R.string.adapter_scanned_code_delete_message)
                        .setPositiveButton(R.string.action_delete) { _, _ ->
                            presenter.deleteCard(code)
                        }
                        .setNegativeButton(R.string.action_cancel, null)
                        .show()

                true
            }
        }


    }

    //================================================================================
    // ViewHolder
    //================================================================================

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @BindView(R.id.adapter_code_card_view) internal lateinit var cardView: CardView
        @BindView(R.id.adapter_code_title) internal lateinit var title: TextView
        @BindView(R.id.adapter_code_image) internal lateinit var imageView: ImageView

        init {
            ButterKnife.bind(this, itemView)
        }
    }
}
