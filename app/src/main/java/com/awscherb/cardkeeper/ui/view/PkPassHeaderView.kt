package com.awscherb.cardkeeper.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import com.awscherb.cardkeeper.R
import com.awscherb.cardkeeper.pkpass.model.PkPassModel
import com.awscherb.cardkeeper.pkpass.model.findPassInfo
import com.awscherb.cardkeeper.pkpass.model.getTranslatedLabel
import com.awscherb.cardkeeper.pkpass.model.getTranslatedValue
import com.awscherb.cardkeeper.pkpass.model.parseHexColor
import com.awscherb.cardkeeper.ui.pkpassDetail.FieldTextView
import com.bumptech.glide.Glide
import java.io.File

class PkPassHeaderView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    init {
        inflate(context, R.layout.view_pkpass_header, this)
    }

    private val image: ImageView = findViewById(R.id.header_pkpass_icon)
    private val logoText: TextView = findViewById(R.id.header_pkpass_logo_text)

    private val composeHeader: ComposeView = findViewById(R.id.header_compose_view)

    var pass: PkPassModel? = null
        set(value) {
            field = value
            value?.let { pass ->
                val labelColor = pass.labelColor.parseHexColor()
                val valueColor = pass.foregroundColor.parseHexColor()

                if (!pass.logoText.isNullOrEmpty()) {
                    logoText.visibility = View.VISIBLE
                    logoText.text = pass.logoText
                    logoText.setTextColor(pass.foregroundColor.parseHexColor())
                } else {
                    logoText.visibility = View.GONE
                }

                val headers = pass.findPassInfo()?.headerFields
                if (headers?.isNotEmpty() == true) {
                    composeHeader.apply {
                        setContent {
                            Row {
                                val firstPass = headers[0]
                                FieldTextView(
                                    fieldConfig = FieldConfig(
                                        label = pass.getTranslatedLabel(firstPass.label),
                                        value = pass.getTranslatedValue(firstPass.value),
                                        labelColor = labelColor,
                                        valueColor = valueColor
                                    )
                                )
                                if (headers.size > 1) {
                                    val secondPass = headers[1]
                                    FieldTextView(
                                        modifier = Modifier.padding(start = 8.dp),
                                        fieldConfig = FieldConfig(
                                            label = pass.getTranslatedLabel(secondPass.label),
                                            value = pass.getTranslatedValue(secondPass.value),
                                            labelColor = labelColor,
                                            valueColor = valueColor
                                        )
                                    )
                                }
                            }
                        }
                    }
                } else {
                    composeHeader.removeAllViews()
                }

                pass.logoPath?.let {
                    Glide.with(this)
                        .load(File(it))
                        .into(image)
                }
            }
        }
}