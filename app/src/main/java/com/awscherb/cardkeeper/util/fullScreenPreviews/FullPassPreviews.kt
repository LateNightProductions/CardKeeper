package com.awscherb.cardkeeper.util.fullScreenPreviews

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.awscherb.cardkeeper.ui.pkpassDetail.PassDetail
import com.awscherb.cardkeeper.compose_common.theme.CardKeeperTheme
import com.awscherb.cardkeeper.util.GlobalPreviewNightMode
import com.awscherb.cardkeeper.util.SampleEvent
import com.awscherb.cardkeeper.util.SampleFlight
import com.awscherb.cardkeeper.util.SampleGenericNoBarcodePass
import com.awscherb.cardkeeper.util.SampleGenericPass
import com.awscherb.cardkeeper.util.SampleGenericTravel
import com.awscherb.cardkeeper.util.SamplePdfPass
import com.awscherb.cardkeeper.util.SampleStorePass

@Composable
@Preview(apiLevel = 33, uiMode = GlobalPreviewNightMode)
fun AirlinePreview() {
    CardKeeperTheme {
        PassDetail(
            padding = PaddingValues(),
            pass = SampleFlight
        )
    }
}

@Composable
@Preview(apiLevel = 33)
fun GenericPreview() {
    CardKeeperTheme {
        PassDetail(
            padding = PaddingValues(),
            pass = SampleGenericTravel
        )
    }
}

@Composable
@Preview(apiLevel = 33)
fun StorePassQrPreview() {
    CardKeeperTheme {
        PassDetail(
            padding = PaddingValues(),
            pass = SampleStorePass
        )
    }
}

@Composable
@Preview(apiLevel = 33)
fun StorePassPdfPreview() {
    CardKeeperTheme {
        PassDetail(
            padding = PaddingValues(),
            pass = SamplePdfPass
        )
    }
}

@Composable
@Preview(apiLevel = 33, uiMode = GlobalPreviewNightMode)
fun GenericPassPreview() {
    CardKeeperTheme {
        PassDetail(
            padding = PaddingValues(),
            pass = SampleGenericPass
        )
    }
}

@Composable
@Preview(apiLevel = 33, uiMode = GlobalPreviewNightMode)
fun GenericPassNoBarcodePreview() {
    CardKeeperTheme {
        PassDetail(
            padding = PaddingValues(),
            pass = SampleGenericNoBarcodePass
        )
    }
}

@Composable
@Preview(apiLevel = 33, uiMode = GlobalPreviewNightMode)
fun EventPreview() {
    CardKeeperTheme {
        PassDetail(
            padding = PaddingValues(),
            pass = SampleEvent
        )
    }
}