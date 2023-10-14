package com.awscherb.cardkeeper.ui.pkpassDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.awscherb.cardkeeper.R
import com.awscherb.cardkeeper.core.Barcode
import com.awscherb.cardkeeper.pkpass.model.PassInfo
import com.awscherb.cardkeeper.pkpass.model.PkPassModel
import com.awscherb.cardkeeper.pkpass.model.TransitType
import com.awscherb.cardkeeper.pkpass.model.canBeUpdated
import com.awscherb.cardkeeper.pkpass.model.findOriginDestination
import com.awscherb.cardkeeper.pkpass.model.findPassInfo
import com.awscherb.cardkeeper.pkpass.model.getTransitType
import com.awscherb.cardkeeper.pkpass.model.getTranslatedLabel
import com.awscherb.cardkeeper.pkpass.model.getTranslatedValue
import com.awscherb.cardkeeper.pkpass.model.isTransit
import com.awscherb.cardkeeper.pkpass.model.parseHexColor
import com.awscherb.cardkeeper.pkpass.model.toBarcodeFormat
import com.awscherb.cardkeeper.pkpass.work.UpdatePassWorker
import com.awscherb.cardkeeper.ui.base.BaseFragment
import com.awscherb.cardkeeper.ui.common.getAlignmentForFieldText
import com.awscherb.cardkeeper.ui.view.FieldConfig
import com.awscherb.cardkeeper.ui.view.PkPassHeaderView
import com.awscherb.cardkeeper.ui.view.PrimaryFieldView
import com.awscherb.cardkeeper.util.WebServiceUrlBuilder
import com.awscherb.cardkeeper.util.extensions.expand
import com.bumptech.glide.Glide
import com.google.android.flexbox.FlexboxLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.journeyapps.barcodescanner.BarcodeEncoder
import dagger.android.support.AndroidSupportInjection
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.concurrent.atomic.AtomicBoolean

class PkPassDetailFragment : BaseFragment(), Toolbar.OnMenuItemClickListener {

    private val viewModel by activityViewModels<PkPassViewModel>()


    private val encoder: BarcodeEncoder = BarcodeEncoder()

    private var refreshItem: MenuItem? = null

    private lateinit var toolbar: Toolbar
    private lateinit var card: CardView
    private lateinit var header: ComposeView
    private lateinit var strip: ImageView
    private lateinit var primaryComposeHolder: ComposeView
    private lateinit var primaryFieldsView: FlexboxLayout
    private lateinit var auxFieldsView: ComposeView
    private lateinit var secondaryFieldsView: ComposeView
    private lateinit var barcodeImage: ImageView
    private lateinit var altText: TextView
    private lateinit var footerImage: ImageView
    private lateinit var infoButton: ImageView
    private lateinit var expiredText: TextView
    private lateinit var lastUpdated: TextView

    private lateinit var bottomSheet: LinearLayout
    private lateinit var bottomRecycler: RecyclerView
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<LinearLayout>

    private val refreshing = AtomicBoolean(false)
    private val updateDateFormat = SimpleDateFormat("h:mm a")
    private val backItemAdapter = BackItemAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(R.layout.fragment_pkpass, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        AndroidSupportInjection.inject(this)

        toolbar = view.findViewById(R.id.pass_detail_toolbar)
        card = view.findViewById(R.id.pass_detail_card)
        header = view.findViewById(R.id.pass_detail_header)
        strip = view.findViewById(R.id.pass_detail_strip)
        primaryComposeHolder = view.findViewById(R.id.pass_compose_primary)
        primaryFieldsView = view.findViewById(R.id.pass_detail_primary)
        auxFieldsView = view.findViewById(R.id.pass_detail_auxiliary)
        secondaryFieldsView = view.findViewById(R.id.pass_detail_secondary)
        barcodeImage = view.findViewById(R.id.pass_detail_barcode)
        altText = view.findViewById(R.id.pass_detail_barcode_alt)
        footerImage = view.findViewById(R.id.pass_detail_footer)
        infoButton = view.findViewById(R.id.pass_detail_info)
        lastUpdated = view.findViewById(R.id.pass_last_updated)
        expiredText = view.findViewById(R.id.pass_detail_expired)

        bottomSheet = view.findViewById(R.id.pass_detail_bottom_sheet)
        bottomRecycler = view.findViewById(R.id.pass_detail_back_recycler)
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomRecycler.layoutManager = LinearLayoutManager(requireContext())
        bottomRecycler.adapter = backItemAdapter
        bottomRecycler.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                RecyclerView.VERTICAL
            )
        )

        toolbar.inflateMenu(R.menu.menu_pass_detail)
        toolbar.setOnMenuItemClickListener(this)
        refreshItem = toolbar.menu.findItem(R.id.pass_refresh)

        viewModel.pass
            .onEach { pass ->
                setupMainPassInfo(pass)
                pass.barcode?.let { barcode ->
                    setupBarcode(barcode)
                }
                pass.findPassInfo()?.let { passInfo ->
                    setupPrimaryFields(pass, passInfo)
                    setupAuxiliaryFields(pass, passInfo)
                    setupSecondaryFields(pass, passInfo)

                }
            }.launchIn(lifecycleScope)

        viewModel.backItems
            .onEach {
                backItemAdapter.items = it
                if (it.isEmpty()) {
                    infoButton.visibility = View.GONE
                } else {
                    infoButton.visibility = View.VISIBLE

                    infoButton.setOnClickListener {
                        bottomSheetBehavior.expand()
                    }
                }
            }.launchIn(lifecycleScope)

        viewModel.pass
            .take(1)
            .onEach {
                if (it.canBeUpdated()) {
                    refreshItem?.isVisible = true
                    refreshPass()
                }
            }.launchIn(lifecycleScope)

    }

    private fun setupMainPassInfo(pass: PkPassModel) {
        if (pass.expirationDate?.before(Date()) == true) {
            expiredText.visibility = View.VISIBLE
        } else {
            expiredText.visibility = View.GONE
        }

        card.setCardBackgroundColor(pass.backgroundColor.parseHexColor())
        header.apply {
            setContent {
                PkPassHeaderView(pass = pass)
            }
        }

        if (pass.stripPath == null) {
            strip.visibility = View.GONE
        } else {
            strip.visibility = View.VISIBLE
            Glide.with(this)
                .load(pass.stripPath)
                .into(strip)
        }

        if (pass.footerPath == null) {
            footerImage.visibility = View.GONE
        } else {
            footerImage.visibility = View.VISIBLE
            Glide.with(this)
                .load(pass.footerPath)
                .into(footerImage)
        }
    }

    private fun setupPrimaryFields(pass: PkPassModel, passInfo: PassInfo) {
        primaryFieldsView.removeAllViews()

        if (passInfo.isTransit() && passInfo.transitType.getTransitType() == TransitType.AIR) {
            primaryFieldsView.visibility = View.GONE
            primaryComposeHolder.visibility = View.VISIBLE

            val (origin, destination) = passInfo.findOriginDestination()

            primaryComposeHolder.apply {
                setContent {
                    TransitPrimarySection(
                        fromAirport = origin.label ?: "",
                        fromCode = origin.value,
                        toAirport = destination.label ?: "",
                        toCode = destination.value,
                        tint = pass.foregroundColor.parseHexColor()
                    )
                }
            }
        } else {
            primaryFieldsView.visibility = View.VISIBLE
            primaryComposeHolder.visibility = View.GONE
            passInfo.primaryFields?.forEach { field ->
                context?.let { ctx ->
                    primaryFieldsView.addView(
                        PrimaryFieldView(ctx, null)
                            .apply {
                                fieldConfig = FieldConfig(
                                    label = pass.getTranslatedLabel(field.label),
                                    value = pass.getTranslatedValue(field.value),
                                    labelColor = pass.labelColor.parseHexColor(),
                                    valueColor = pass.foregroundColor.parseHexColor()
                                )
                            }
                    )
                }
            }
        }
    }

    private fun setupAuxiliaryFields(pass: PkPassModel, passInfo: PassInfo) {
        auxFieldsView.apply {
            setContent {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    passInfo.auxiliaryFields?.forEachIndexed { index, field ->
                        val align =
                            getAlignmentForFieldText(index, passInfo.auxiliaryFields?.size ?: 0)
                        FieldTextView(
                            alignment = align,
                            fieldConfig = FieldConfig(
                                label = pass.getTranslatedLabel(field.label),
                                value = pass.getTranslatedValue(field.value),
                                labelColor = pass.labelColor.parseHexColor(),
                                valueColor = pass.foregroundColor.parseHexColor()
                            )
                        )
                    }
                }
            }
        }
    }

    private fun setupSecondaryFields(pass: PkPassModel, passInfo: PassInfo) {
        secondaryFieldsView.apply {
            setContent {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    passInfo.secondaryFields?.forEachIndexed { index, field ->
                        val align =
                            getAlignmentForFieldText(index, passInfo.secondaryFields?.size ?: 0)
                        FieldTextView(
                            alignment = align,
                            fieldConfig = FieldConfig(
                                label = pass.getTranslatedLabel(field.label),
                                value = pass.getTranslatedValue(field.value),
                                labelColor = pass.labelColor.parseHexColor(),
                                valueColor = pass.foregroundColor.parseHexColor()
                            )
                        )
                    }
                }
            }
        }
    }

    private fun setupBarcode(barcode: Barcode) {
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

        if (barcode.altText?.isNotEmpty() == true) {
            altText.visibility = View.VISIBLE
            altText.text = barcode.altText
        }
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.pass_refresh -> {
                lifecycleScope.launch { refreshPass() }
                true
            }

            R.id.pass_delete -> {
                promptDelete()
                true
            }

            else -> false
        }
    }

    private fun promptDelete() {
        AlertDialog.Builder(requireContext())
            .setTitle(R.string.fragment_pass_detail_delete_title)
            .setMessage(R.string.fragment_pass_detail_delete_prompt)
            .setPositiveButton(R.string.action_delete) { _, _ ->
                viewModel.deletePass()
                findNavController().popBackStack()
            }
            .setNegativeButton(R.string.action_cancel, null)
            .show()
    }

    private suspend fun refreshPass() {
        val pass = viewModel.pass.firstOrNull() ?: return
        if (pass.canBeUpdated() && refreshing.compareAndSet(false, true)) {
            refreshItem?.setIcon(R.drawable.ic_downloading)
            refreshItem?.isEnabled = false
            val req = OneTimeWorkRequestBuilder<UpdatePassWorker>()
                .setInputData(
                    workDataOf(
                        UpdatePassWorker.KEY_URL to WebServiceUrlBuilder.buildUrl(pass),
                        UpdatePassWorker.KEY_TOKEN to pass.authenticationToken
                    )
                )
                .build()

            WorkManager.getInstance(requireContext())
                .enqueue(req)
            WorkManager.getInstance(requireContext())
                .getWorkInfoByIdLiveData(req.id)
                .observe(viewLifecycleOwner) {
                    if (it.state == WorkInfo.State.SUCCEEDED) {
                        refreshing.set(false)
                        refreshItem?.isEnabled = true
                        refreshItem?.setIcon(R.drawable.ic_refresh)
                        refreshItem?.isVisible = true
                        lastUpdated.visibility = View.VISIBLE
                        lastUpdated.text = getString(
                            R.string.fragment_pass_detail_last_updated,
                            updateDateFormat.format(Date(System.currentTimeMillis()))
                        )

                    }
                }
        }
    }
}