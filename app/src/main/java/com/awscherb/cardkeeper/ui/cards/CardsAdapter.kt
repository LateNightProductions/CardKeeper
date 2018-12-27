package com.awscherb.cardkeeper.ui.cards

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import com.awscherb.cardkeeper.R
import com.awscherb.cardkeeper.data.model.ScannedCode
import com.awscherb.cardkeeper.ui.base.BaseAdapter
import com.google.zxing.BarcodeFormat.*
import com.google.zxing.WriterException
import com.journeyapps.barcodescanner.BarcodeEncoder
import kotlinx.android.synthetic.main.adapter_code.view.*
import java.util.*


class CardsAdapter constructor(
    private val context: Context,
    private val presenter: CardsContract.Presenter
) : BaseAdapter<ScannedCode, CardsAdapter.ViewHolder>(ArrayList()) {

    private val encoder: BarcodeEncoder = BarcodeEncoder()

    //================================================================================
    // Adapter methods
    //================================================================================

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(context).inflate(R.layout.adapter_code, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, code: ScannedCode) {
        holder.itemView.apply {

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

            // Setup delete
            setOnLongClickListener {
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

    class ViewHolder(itemView: View) :
        androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView)

}
