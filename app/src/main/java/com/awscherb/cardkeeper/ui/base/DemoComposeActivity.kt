package com.awscherb.cardkeeper.ui.base

import android.graphics.Color
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.awscherb.cardkeeper.ui.common.TextInfo
import com.awscherb.cardkeeper.ui.pkpassDetail.AirPrimarySection

class DemoComposeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AirPrimarySection(
                "Newark-Liberty Intl",
                "EWR",
                "Ithaca Airport",
                "ITH",
                Color.parseColor("#FFFFFF")
            )
        }
    }
}
