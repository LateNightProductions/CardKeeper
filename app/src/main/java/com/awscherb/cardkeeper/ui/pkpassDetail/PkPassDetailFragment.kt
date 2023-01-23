package com.awscherb.cardkeeper.ui.pkpassDetail

import android.graphics.Color
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.core.view.marginEnd
import androidx.core.view.marginStart
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.awscherb.cardkeeper.R
import com.awscherb.cardkeeper.data.model.findPassInfo
import com.awscherb.cardkeeper.data.model.getTranslatedLabel
import com.awscherb.cardkeeper.data.model.parseHexColor
import com.awscherb.cardkeeper.data.model.toBarcodeFormat
import com.awscherb.cardkeeper.ui.base.BaseFragment
import com.awscherb.cardkeeper.ui.view.FieldConfig
import com.awscherb.cardkeeper.ui.view.FieldView
import com.awscherb.cardkeeper.ui.view.PkPassHeaderView
import com.awscherb.cardkeeper.ui.view.PrimaryFieldView
import com.bumptech.glide.Glide
import com.google.android.flexbox.FlexboxLayout
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.journeyapps.barcodescanner.BarcodeEncoder
import dagger.android.support.AndroidSupportInjection
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class PkPassDetailFragment : BaseFragment() {

    private val viewModel by activityViewModels<PkPassViewModel> { factory }

    @Inject
    lateinit var factory: PkPassViewModelFactory

    private val encoder: BarcodeEncoder = BarcodeEncoder()

    lateinit var card: CardView
    lateinit var header: PkPassHeaderView
    lateinit var strip: ImageView
    lateinit var primaryFieldsView: FlexboxLayout
    lateinit var auxFieldsView: FlexboxLayout
    lateinit var secondaryFieldsView: FlexboxLayout
    lateinit var barcodeImage: ImageView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(R.layout.fragment_pkpass, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        AndroidSupportInjection.inject(this)

        card = view.findViewById(R.id.pass_detail_card)
        header = view.findViewById(R.id.pass_detail_header)
        strip = view.findViewById(R.id.pass_detail_strip)
        primaryFieldsView = view.findViewById(R.id.pass_detail_primary)
        auxFieldsView = view.findViewById(R.id.pass_detail_auxiliary)
        secondaryFieldsView = view.findViewById(R.id.pass_detail_secondary)
        barcodeImage = view.findViewById(R.id.pass_detail_barcode)

        viewModel.pass
            .filterNotNull()
            .onEach { pass ->

                card.setCardBackgroundColor(pass.backgroundColor.parseHexColor())
                header.pass = pass

                if (pass.stripPath == null) {
                    strip.visibility = View.GONE
                } else {
                    strip.visibility = View.VISIBLE
                    Glide.with(this)
                        .load(pass.stripPath)
                        .into(strip)
                }

                pass.barcode?.let { barcode ->
                    val format = barcode.format.toBarcodeFormat()
                    // Set image scaleType according to barcode type
                    val scaleType = when (format) {
                        BarcodeFormat.QR_CODE, BarcodeFormat.AZTEC, BarcodeFormat.DATA_MATRIX -> ImageView.ScaleType.FIT_CENTER
                        else -> ImageView.ScaleType.FIT_XY
                    }
                    barcodeImage.scaleType = scaleType

                    // Load image
                    try {
                        barcodeImage.setImageBitmap(
                            encoder.encodeBitmap(barcode.message, format, 400, 400)
                        )
                    } catch (e: WriterException) {
                        e.printStackTrace()
                    }
                }

                pass.findPassInfo()?.let { passInfo ->
                    passInfo.primaryFields?.forEach { field ->
                        context?.let { ctx ->
                            primaryFieldsView.addView(
                                PrimaryFieldView(ctx, null)
                                    .apply {
                                        fieldConfig = FieldConfig(
                                            label = pass.getTranslatedLabel(field.label),
                                            value = field.value,
                                            labelColor = pass.labelColor.parseHexColor(),
                                            valueColor = pass.foregroundColor.parseHexColor()
                                        )
                                    }
                            )
                        }
                    }

                    passInfo.auxiliaryFields?.forEach { field ->
                        context?.let { ctx ->
                            auxFieldsView.addView(
                                FieldView(ctx, null).apply {
                                    fieldConfig = FieldConfig(
                                        label = pass.getTranslatedLabel(field.label),
                                        value = field.value,
                                        labelColor = pass.labelColor.parseHexColor()
                                    )
                                }
                            )
                        }
                    }

                    passInfo.secondaryFields?.forEach { field ->
                        context?.let { ctx ->
                            secondaryFieldsView.addView(
                                FieldView(ctx, null).apply {
                                    fieldConfig = FieldConfig(
                                        label = pass.getTranslatedLabel(field.label),
                                        value = field.value,
                                        labelColor = pass.labelColor.parseHexColor()
                                    )
                                }
                            )
                        }
                    }

                }
            }.launchIn(lifecycleScope)
    }
}

private fun FlexboxLayout.getMaximumWidthForChild(columns: Int): Int {
    val displayMetrics = this.resources.displayMetrics
    val widthInPixels = displayMetrics.widthPixels - marginStart - marginEnd
    val maxWidthForChildInPixels = widthInPixels / columns
    val childWidthInDp = (maxWidthForChildInPixels * DisplayMetrics.DENSITY_DEFAULT) / displayMetrics.densityDpi
    return maxWidthForChildInPixels
}