package com.awscherb.cardkeeper.ui.pkpassDetail

import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
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
import androidx.core.view.marginEnd
import androidx.core.view.marginStart
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
import com.awscherb.cardkeeper.data.model.canBeUpdated
import com.awscherb.cardkeeper.data.model.findPassInfo
import com.awscherb.cardkeeper.data.model.getTranslatedLabel
import com.awscherb.cardkeeper.data.model.parseHexColor
import com.awscherb.cardkeeper.data.model.toBarcodeFormat
import com.awscherb.cardkeeper.data.work.UpdatePassWorker
import com.awscherb.cardkeeper.ui.base.BaseFragment
import com.awscherb.cardkeeper.ui.view.FieldConfig
import com.awscherb.cardkeeper.ui.view.FieldView
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
import javax.inject.Inject

class PkPassDetailFragment : BaseFragment(), Toolbar.OnMenuItemClickListener {

    private val viewModel by activityViewModels<PkPassViewModel> { factory }

    @Inject
    lateinit var factory: PkPassViewModelFactory

    private val encoder: BarcodeEncoder = BarcodeEncoder()

    private var refreshItem: MenuItem? = null

    private lateinit var toolbar: Toolbar
    private lateinit var card: CardView
    private lateinit var header: PkPassHeaderView
    private lateinit var strip: ImageView
    private lateinit var primaryFieldsView: FlexboxLayout
    private lateinit var auxFieldsView: FlexboxLayout
    private lateinit var secondaryFieldsView: FlexboxLayout
    private lateinit var barcodeImage: ImageView
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
        primaryFieldsView = view.findViewById(R.id.pass_detail_primary)
        auxFieldsView = view.findViewById(R.id.pass_detail_auxiliary)
        secondaryFieldsView = view.findViewById(R.id.pass_detail_secondary)
        barcodeImage = view.findViewById(R.id.pass_detail_barcode)
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

                primaryFieldsView.removeAllViews()
                auxFieldsView.removeAllViews()
                secondaryFieldsView.removeAllViews()

                if (pass.expirationDate?.before(Date()) == true) {
                    expiredText.visibility = View.VISIBLE
                } else {
                    expiredText.visibility = View.GONE
                }

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

                if (pass.footerPath == null) {
                    footerImage.visibility = View.GONE
                } else {
                    footerImage.visibility = View.VISIBLE
                    Glide.with(this)
                        .load(pass.footerPath)
                        .into(footerImage)
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
                                        labelColor = pass.labelColor.parseHexColor(),
                                        valueColor = pass.foregroundColor.parseHexColor()
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

private fun FlexboxLayout.getMaximumWidthForChild(columns: Int): Int {
    val displayMetrics = this.resources.displayMetrics
    val widthInPixels = displayMetrics.widthPixels - marginStart - marginEnd
    val maxWidthForChildInPixels = widthInPixels / columns
    val childWidthInDp =
        (maxWidthForChildInPixels * DisplayMetrics.DENSITY_DEFAULT) / displayMetrics.densityDpi
    return maxWidthForChildInPixels
}