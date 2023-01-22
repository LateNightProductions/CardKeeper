package com.awscherb.cardkeeper.ui.view

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.awscherb.cardkeeper.R
import com.awscherb.cardkeeper.data.model.PkPassModel
import com.awscherb.cardkeeper.data.model.parseHexColor
import com.bumptech.glide.Glide
import java.io.File

class PkPassHeaderView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    init {
        inflate(context, R.layout.view_pkpass_header, this)
    }

    val image: ImageView = findViewById(R.id.header_pkpass_icon)
    val logoText: TextView = findViewById(R.id.header_pkpass_logo_text)

    val headerField1: FieldView = findViewById(R.id.header_pkpass_field1)
    val headerField2: FieldView = findViewById(R.id.header_pkpass_field2)

    var pass: PkPassModel? = null
        set(value) {
            field = value
            value?.let {
                val labelColor = Color.parseColor(it.labelColor.parseHexColor())

                if (!it.logoText.isNullOrEmpty()) {
                    logoText.visibility = View.VISIBLE
                    logoText.text = it.logoText
                    logoText.setTextColor(labelColor)
                } else {
                    logoText.visibility = View.GONE
                }

                val headers = it.boardingPass?.headerFields
                if (headers?.isNotEmpty() == true) {
                    headerField1.visibility = View.VISIBLE
                    val firstPass = headers[0]
                    headerField1.fieldConfig = FieldConfig(
                        label = firstPass.label,
                        value = firstPass.value,
                        color = labelColor
                    )

                    if (headers.size > 1) {
                        headerField2.visibility = View.VISIBLE
                        val secondPass = headers[1]
                        headerField2.fieldConfig = FieldConfig(
                            label = secondPass.label,
                            value = secondPass.value,
                            color = labelColor
                        )
                    } else {
                        headerField2.visibility = View.GONE
                    }
                }

                it.logoPath?.let {
                    Glide.with(this)
                        .load(File(it))
                        .into(image)
                }
            }
        }
}