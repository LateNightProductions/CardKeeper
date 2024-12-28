package com.awscherb.cardkeeper.ui.pkpassDetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.awscherb.cardkeeper.pkpass.model.PassInfoType
import com.awscherb.cardkeeper.pkpass.model.PkPassModel
import com.awscherb.cardkeeper.pkpass.model.canBeUpdated
import com.awscherb.cardkeeper.pkpass.model.findFirstBarcode
import com.awscherb.cardkeeper.pkpass.model.findPassInfo
import com.awscherb.cardkeeper.pkpass.model.parseHexColor
import com.awscherb.cardkeeper.pkpass.model.passInfoType
import com.awscherb.cardkeeper.pkpass.model.toBarcodeFormat
import com.awscherb.cardkeeper.ui.common.BarcodeSection
import com.awscherb.cardkeeper.ui.common.DeleteDialog
import com.awscherb.cardkeeper.ui.common.PkPassHeaderView
import com.awscherb.cardkeeper.compose_common.ScaffoldScreen
import com.awscherb.cardkeeper.ui.theme.CardKeeperTheme
import com.awscherb.cardkeeper.util.SampleCoupon
import com.awscherb.cardkeeper.util.SampleEvent
import com.awscherb.cardkeeper.util.SampleFlight
import com.awscherb.cardkeeper.util.SampleGenericPass2
import com.awscherb.cardkeeper.util.SampleStorePass

@Composable
fun PassDetailScreen(
    passDetailViewModel: PkPassViewModel = hiltViewModel(),
    onDelete: () -> Unit = { },
    navOnClick: () -> Unit
) {
    val pass by passDetailViewModel.pass.collectAsState(initial = null)
    val isAutoUpdateOn by passDetailViewModel.shouldUpdate.collectAsState(initial = false)
    val backItems by passDetailViewModel.backItems.collectAsState(initial = emptyList())

    PassDetailScreenInner(
        backItems = backItems,
        isAutoUpdateOn = isAutoUpdateOn,
        pass = pass,
        navOnClick = navOnClick,
        setAutoUpdate = passDetailViewModel::setAutoUpdate,
        onDelete = {
            passDetailViewModel.deletePass()
            onDelete()
        }
    )
}

@Composable
fun PassDetailScreenInner(
    backItems: List<Pair<String, String>>,
    isAutoUpdateOn: Boolean,
    pass: PkPassModel?,
    startShowingBackInfo: Boolean = false,
    startShowingAutoUpdate: Boolean = false,
    startWithDeleteOpen: Boolean = false,
    navOnClick: () -> Unit,
    onDelete: () -> Unit = {},
    setAutoUpdate: (Boolean) -> Unit
) {
    var showBackInfo by remember {
        mutableStateOf(startShowingBackInfo)
    }
    var showUpdateSettings by remember {
        mutableStateOf(startShowingAutoUpdate)
    }

    var showDeleteMenu by remember {
        mutableStateOf(startWithDeleteOpen)
    }

    ScaffoldScreen(
        title = "Pass",
        navIcon = Icons.AutoMirrored.Default.ArrowBack,
        navOnClick = navOnClick,
        topBarActions = {
            IconButton(onClick = { showDeleteMenu = true }) {
                Icon(Icons.Default.Delete, "Delete")
            }
            if (backItems.isNotEmpty()) {
                IconButton(onClick = {
                    showBackInfo = true
                    showUpdateSettings = false
                }) {
                    Icon(Icons.Default.Info, "Info")
                }
            }

            if (pass?.canBeUpdated() == true) {
                IconButton(onClick = {
                    showBackInfo = false
                    showUpdateSettings = true
                }) {
                    Icon(Icons.Default.Settings, "Settings")
                }
            }
        }) {

        if (showDeleteMenu) {
            DeleteDialog(
                onDelete = onDelete,
                onDismiss = {
                    showDeleteMenu = false
                })
        }

        if (showBackInfo) {
            PassInfoDialog(items = backItems) {
                showBackInfo = false
            }
        }

        if (showUpdateSettings) {
            PassUpdateSettingsDialog(
                isAutoUpdateOn = isAutoUpdateOn,
                onDismissRequest = { showUpdateSettings = false },
                onUpdateSettingsChanged = { update ->
                    setAutoUpdate(update)
                }
            )
        }

        pass?.let { pass ->
            PassDetail(
                padding = it,
                pass = pass,
            )
        }
    }
}

@Composable
fun PassDetail(
    modifier: Modifier = Modifier,
    padding: PaddingValues,
    pass: PkPassModel,
) {
    Box(
        modifier = modifier
            .graphicsLayer {
                compositingStrategy = CompositingStrategy.Offscreen
            }
            .drawWithContent {
                drawContent()
                if (pass.passInfoType == PassInfoType.EVENT_TICKET) {
                    drawCircle(
                        color = Color.Red,
                        radius = 32.dp.toPx(),
                        center = Offset(
                            x = this.center.x,
                            y = padding.calculateTopPadding().toPx() - 8.dp.toPx()
                        ),
                        blendMode = BlendMode.DstOut
                    )
                }
            }
            .padding(
                PaddingValues(
                    top = padding.calculateTopPadding() + 8.dp,
                    start = 8.dp,
                    end = 8.dp,
                    bottom = padding.calculateBottomPadding() + 8.dp,
                )
            )
            .clip(RoundedCornerShape(if (pass.eventTicket != null) 0.dp else 8.dp))
            .background(color = Color(pass.backgroundColor.parseHexColor())),
    ) {

        Box {
            pass.backgroundPath?.let { background ->
                AsyncImage(
                    model = background,
                    contentDescription = "Background",
                    modifier = Modifier
                        .fillMaxWidth()
                        .blur(radius = 32.dp)
                )
            }

            val heightModifier = if (pass.backgroundPath != null) {
                val height = LocalDensity.current.run {
                    (LocalConfiguration.current.screenWidthDp - 16.toDp().toPx()) * (220f / 180f)
                }.dp
                Modifier.height(height = height)
            } else {
                Modifier
            }
            Column(
                modifier = heightModifier,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    PkPassHeaderView(
                        pass = pass
                    )

                    pass.findPassInfo()?.let { passInfo ->
                        when (pass.passInfoType) {
                            PassInfoType.BOARDING_PASS -> BoardingPass(pass, passInfo)
                            PassInfoType.STORE_CARD -> StoreCard(pass, passInfo)
                            PassInfoType.COUPON -> Coupon(pass, passInfo)
                            PassInfoType.GENERIC -> Generic(pass, passInfo)
                            PassInfoType.EVENT_TICKET -> Event(pass, passInfo)
                            null -> {}
                        }
                    }
                }

                var hasBarcode = false
                pass.findFirstBarcode()?.let { barcode ->
                    hasBarcode = true
                    BarcodeSection(
                        modifier = Modifier
                            .padding(
                                top = if (pass.footerPath != null) 0.dp else 16.dp,
                            ),
                        message = barcode.message,
                        barcodeFormat = barcode.format.toBarcodeFormat(),
                        altText = barcode.altText,
                        altColor = Color(pass.foregroundColor.parseHexColor()),
                        backgroundColor = Color.White,
                    )
                }

                if (!hasBarcode) {
                    Spacer(modifier = Modifier.height(16.dp))
                }


            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun PassDetailScreenPreview() {
    CardKeeperTheme {
        PassDetailScreenInner(
            backItems = listOf("" to ""),
            isAutoUpdateOn = false,
            pass = SampleEvent,
            navOnClick = { }) {
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun PassDetailScreenBackPreview() {
    CardKeeperTheme {
        PassDetailScreenInner(
            backItems = listOf(
                "Gate" to "A4",
                "Departure" to "JFK",
                "Arrival" to "LAX"
            ),
            isAutoUpdateOn = false,
            startShowingBackInfo = true,
            pass = SampleFlight,
            navOnClick = { }) {
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun PassDetailScreenAutoUpdatePreview() {
    CardKeeperTheme {
        PassDetailScreenInner(
            backItems = listOf(
                "Gate" to "A4",
                "Departure" to "JFK",
                "Arrival" to "LAX"
            ),
            isAutoUpdateOn = true,
            startShowingAutoUpdate = true,
            pass = SampleFlight,
            navOnClick = { }) {
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun PassDetailStorePassPreview() {
    CardKeeperTheme {
        PassDetailScreenInner(
            backItems = listOf("" to ""),
            isAutoUpdateOn = false,
            pass = SampleStorePass,
            navOnClick = { }) {
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun PassDetailCouponScreenPreview() {
    CardKeeperTheme {
        PassDetailScreenInner(
            backItems = listOf("" to ""),
            isAutoUpdateOn = false,
            pass = SampleCoupon,
            navOnClick = { }) {
        }
    }
}


@Preview(showSystemUi = true)
@Composable
fun PassDetailGenericScreenPreview() {
    CardKeeperTheme {
        PassDetailScreenInner(
            backItems = listOf("" to ""),
            isAutoUpdateOn = false,
            pass = SampleGenericPass2,
            navOnClick = { }) {
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun PassDetailEventScreenPreview() {
    CardKeeperTheme {
        PassDetailScreenInner(
            backItems = listOf("" to ""),
            isAutoUpdateOn = false,
            pass = SampleEvent,
            navOnClick = { }) {
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun PassDetailEventScreenDeletePreview() {
    CardKeeperTheme {
        PassDetailScreenInner(
            backItems = listOf("" to ""),
            isAutoUpdateOn = false,
            pass = SampleEvent,
            startWithDeleteOpen = true,
            navOnClick = { }) {
        }
    }
}
