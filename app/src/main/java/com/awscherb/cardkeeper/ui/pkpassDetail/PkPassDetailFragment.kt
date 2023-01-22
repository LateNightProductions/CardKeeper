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
import com.awscherb.cardkeeper.data.model.parseHexColor
import com.awscherb.cardkeeper.ui.base.BaseFragment
import com.awscherb.cardkeeper.ui.view.FieldConfig
import com.awscherb.cardkeeper.ui.view.FieldView
import com.awscherb.cardkeeper.ui.view.PkPassHeaderView
import com.awscherb.cardkeeper.ui.view.PrimaryFieldView
import com.bumptech.glide.Glide
import com.google.android.flexbox.FlexboxLayout
import dagger.android.support.AndroidSupportInjection
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class PkPassDetailFragment : BaseFragment() {

    private val viewModel by activityViewModels<PkPassViewModel> { factory }

    @Inject
    lateinit var factory: PkPassViewModelFactory

    lateinit var card: CardView
    lateinit var header: PkPassHeaderView
    lateinit var strip: ImageView
    lateinit var primaryFieldsView: FlexboxLayout
    lateinit var auxFieldsView: FlexboxLayout
    lateinit var secondaryFieldsView: FlexboxLayout

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


        viewModel.pass
            .filterNotNull()
            .onEach { pass ->

                card.setCardBackgroundColor(Color.parseColor(pass.backgroundColor.parseHexColor()))
                header.pass = pass

                if (pass.stripPath == null) {
                    strip.visibility = View.GONE
                } else {
                    strip.visibility = View.VISIBLE
                    Glide.with(this)
                        .load(pass.stripPath)
                        .into(strip)
                }

                pass.findPassInfo()?.let { passInfo ->

                    passInfo.primaryFields?.forEach { field ->
                        context?.let { ctx ->
                            primaryFieldsView.addView(
                                PrimaryFieldView(ctx, null)
                                    .apply {
                                        fieldConfig = FieldConfig(
                                            label = field.label,
                                            value = field.value,
                                            labelColor = Color.parseColor(pass.labelColor.parseHexColor()),
                                            valueColor = Color.parseColor(pass.foregroundColor.parseHexColor())
                                        )
                                    }
                            )
                        }
                    }

                    passInfo.auxiliaryFields?.forEach { field ->
                        context?.let { ctx ->
                            // val maxWidth = auxSecondaryFieldsView.getMaximumWidthForChild(4)
                            auxFieldsView.addView(
                                FieldView(ctx, null).apply {
                                    fieldConfig = FieldConfig(
                                        label = field.label,
                                        value = field.value,
                                        labelColor = Color.parseColor((pass.labelColor.parseHexColor()))
                                    )
                                    // layoutParams = FlexboxLayout.LayoutParams(
                                    //     maxWidth,
                                    //     FlexboxLayout.LayoutParams.WRAP_CONTENT
                                    // )
                                }
                            )
                        }
                    }

                    passInfo.secondaryFields?.forEach { field ->
                        context?.let { ctx ->
                            // val maxWidth = auxSecondaryFieldsView.getMaximumWidthForChild(4)
                            secondaryFieldsView.addView(
                                FieldView(ctx, null).apply {
                                    fieldConfig = FieldConfig(
                                        label = field.label,
                                        value = field.value,
                                        labelColor = Color.parseColor((pass.labelColor.parseHexColor()))
                                    )
                                    // layoutParams = FlexboxLayout.LayoutParams(
                                    //     maxWidth,
                                    //     FlexboxLayout.LayoutParams.WRAP_CONTENT
                                    // )
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