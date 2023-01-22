package com.awscherb.cardkeeper.ui.view

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.awscherb.cardkeeper.R

class FieldView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    init {
        inflate(context, R.layout.view_field, this)
    }

    private val label = findViewById<TextView>(R.id.view_field_header)
    private val valueText = findViewById<TextView>(R.id.view_field_value)

    var fieldConfig: FieldConfig? = null
        set(value) {
            field = value
            fieldConfig?.let {
                if (it.label == null) {
                    label.visibility = GONE
                    valueText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f)
                } else {
                    valueText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f)
                    label.text = it.label
                    label.visibility = VISIBLE
                }
                valueText.text = it.value
                label.setTextColor(it.labelColor)
                valueText.setTextColor(it.labelColor)
            }
        }
}