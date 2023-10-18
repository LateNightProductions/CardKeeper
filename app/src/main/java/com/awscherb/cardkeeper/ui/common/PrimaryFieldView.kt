package com.awscherb.cardkeeper.ui.common

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.awscherb.cardkeeper.R

class PrimaryFieldView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    init {
        inflate(context, R.layout.view_primary_field, this)
    }

    private val label = findViewById<TextView>(R.id.view_primary_field_label)
    private val valueText = findViewById<TextView>(R.id.view_primary_field_value)

    var fieldConfig: FieldConfig? = null
        set(value) {
            field = value
            fieldConfig?.let {

                label.text = it.label
                valueText.text = it.value
                label.setTextColor(it.labelColor)
                valueText.setTextColor(it.valueColor)
            }
        }
}