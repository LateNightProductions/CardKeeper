package com.awscherb.cardkeeper.app

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import android.service.quicksettings.TileService
import com.awscherb.cardkeeper.ui.base.CardKeeperActivity

class CardKeeperTileService : TileService() {

    @SuppressLint("StartActivityAndCollapseDeprecated")
    override fun onClick() {
        val intent = Intent(this, CardKeeperActivity::class.java)
            .apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
                putExtra(CardKeeperActivity.EXTRA_START_SCAN, true)
            }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            startActivityAndCollapse(
                PendingIntent.getActivity(
                    this,
                    0,
                    intent,
                    PendingIntent.FLAG_IMMUTABLE,
                    null
                )
            )
        } else {
            startActivityAndCollapse(intent)
        }
    }
}