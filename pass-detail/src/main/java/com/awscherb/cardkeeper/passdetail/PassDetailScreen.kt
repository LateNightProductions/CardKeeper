package com.awscherb.cardkeeper.passdetail

import androidx.compose.foundation.background
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
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
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.awscherb.cardkeeper.compose_common.dialog.DeleteDialog
import com.awscherb.cardkeeper.compose_common.theme.CardKeeperTheme
import com.awscherb.cardkeeper.compose_common.theme.ScaffoldScreen
import com.awscherb.cardkeeper.passUi.PassHeaderModel
import com.awscherb.cardkeeper.passUi.PkPassHeaderView
import com.awscherb.cardkeeper.passdetail.boardingpass.BoardingPass
import com.awscherb.cardkeeper.passdetail.common.BarcodeSection
import com.awscherb.cardkeeper.passdetail.coupon.Coupon
import com.awscherb.cardkeeper.passdetail.dialog.PassInfoDialog
import com.awscherb.cardkeeper.passdetail.dialog.PassUpdateSettingsDialog
import com.awscherb.cardkeeper.passdetail.event.Event
import com.awscherb.cardkeeper.passdetail.generic.Generic
import com.awscherb.cardkeeper.passdetail.model.PassDetailModel
import com.awscherb.cardkeeper.passdetail.storecard.StoreCard
import com.awscherb.cardkeeper.passdetail.model.TransitModel
import com.awscherb.cardkeeper.passdetail.util.SampleEvent
import com.awscherb.cardkeeper.pkpass.model.toBarcodeFormat

@Composable
fun PassDetailScreen(
    passDetailViewModel: PassViewModel = hiltViewModel(),
    onDelete: () -> Unit = { },
    navOnClick: () -> Unit
) {
    val passes by passDetailViewModel.passes.collectAsState()
    val isAutoUpdateOn by passDetailViewModel.shouldUpdate.collectAsState(initial = false)
    val backItems by passDetailViewModel.backItems.collectAsState(initial = emptyList())

    LaunchedEffect(passes) {
        if (passes.isNotEmpty() && passDetailViewModel.passes.value.isEmpty()) onDelete()
    }

    PassDetailScreenInner(
        backItems = backItems,
        isAutoUpdateOn = isAutoUpdateOn,
        passes = passes,
        navOnClick = navOnClick,
        setAutoUpdate = passDetailViewModel::setAutoUpdate,
        onCurrentPageChanged = { passDetailViewModel.currentPassIndex.value = it },
        onDelete = {
            passDetailViewModel.deletePass()
            if (passes.size <= 1) onDelete()
        }
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PassDetailScreenInner(
    backItems: List<Pair<String, String>>,
    isAutoUpdateOn: Boolean,
    passes: List<PassDetailModel?>,
    startShowingBackInfo: Boolean = false,
    startShowingAutoUpdate: Boolean = false,
    startWithDeleteOpen: Boolean = false,
    navOnClick: () -> Unit,
    onDelete: () -> Unit = {},
    onCurrentPageChanged: (Int) -> Unit = {},
    setAutoUpdate: (Boolean) -> Unit
) {
    val pass = passes.firstOrNull()
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

            if (pass?.canBeUpdated == true) {
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

        if (passes.isNotEmpty()) {
            val pagerState = rememberPagerState(pageCount = { passes.size })

            LaunchedEffect(pagerState) {
                snapshotFlow { pagerState.currentPage }.collect { onCurrentPageChanged(it) }
            }

            Column {
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.weight(1f)
                ) { page ->
                    passes.getOrNull(page)?.let { pass ->
                        PassDetail(padding = it, pass = pass)
                    }
                }

                if (passes.size > 1) {
                    Row(
                        modifier = Modifier
                            .wrapContentHeight()
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        repeat(pagerState.pageCount) { i ->
                            val color = if (pagerState.currentPage == i)
                                Color.DarkGray
                            else
                                Color.LightGray
                            Box(
                                modifier = Modifier
                                    .padding(2.dp)
                                    .clip(CircleShape)
                                    .background(color)
                                    .size(8.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PassDetail(
    modifier: Modifier = Modifier,
    padding: PaddingValues,
    pass: PassDetailModel,
) {
    Box(
        modifier = modifier
            .graphicsLayer {
                compositingStrategy = CompositingStrategy.Offscreen
            }
            .drawWithContent {
                drawContent()
                if (pass.type == PassDetailModel.Type.EVENT_TICKET) {
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
            .clip(RoundedCornerShape(if (pass.type == PassDetailModel.Type.EVENT_TICKET) 0.dp else 8.dp))
            .background(color = pass.backgroundColor),
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
                        pass = PassHeaderModel(
                            logo = pass.logoPath,
                            description = pass.description,
                            foregroundColor = pass.foregroundColor,
                            labelColor = pass.labelColor,
                            logoText = pass.logoText,
                            headerConfig = pass.headerItems
                        )
                    )

                    when (pass.type) {
                        PassDetailModel.Type.BOARDING_PASS -> BoardingPass(pass)
                        PassDetailModel.Type.STORE_CARD -> StoreCard(pass)
                        PassDetailModel.Type.COUPON -> Coupon(pass)
                        PassDetailModel.Type.GENERIC -> Generic(pass)
                        PassDetailModel.Type.EVENT_TICKET -> Event(pass)
                    }
                }

                var hasBarcode = false
                pass.barcodes.firstOrNull()?.let { barcode ->
                    hasBarcode = true
                    BarcodeSection(
                        modifier = Modifier
                            .padding(
                                top = if (pass.footerPath != null) 0.dp else 16.dp,
                            ),
                        message = barcode.message,
                        barcodeFormat = barcode.format.toBarcodeFormat(),
                        altText = barcode.altText,
                        altColor = pass.foregroundColor,
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

@PreviewParameter(ModelProvider::class)
@Preview()
@Composable
fun PassDetailScreenPreview(model: PassDetailModel? = null) {
    CardKeeperTheme {
        PassDetailScreenInner(
            backItems = listOf("" to ""),
            isAutoUpdateOn = false,
            passes = listOfNotNull(model),
            navOnClick = { }) {
        }
    }
}

private class ModelProvider : PreviewParameterProvider<PassDetailModel> {
    override val values: Sequence<PassDetailModel>
        get() = listOf(SampleEvent).asSequence()
}

private fun sampleBoardingPass(id: String, passengerName: String, seat: String) = PassDetailModel(
    id = id,
    canBeUpdated = false,
    type = PassDetailModel.Type.BOARDING_PASS,
    backgroundColor = androidx.compose.ui.graphics.Color(0xFF1A3A5C),
    backgroundPath = null,
    logoPath = null,
    description = "SFO → JFK",
    foregroundColor = androidx.compose.ui.graphics.Color.White,
    labelColor = androidx.compose.ui.graphics.Color(0xFFAABBCC),
    logoText = "SkyWave Air",
    barcodes = emptyList(),
    isBarcodeSquare = false,
    footerPath = null,
    headerItems = listOf(
        com.awscherb.cardkeeper.passUi.FieldConfig("Flight", "SW 412", androidx.compose.ui.graphics.Color(0xFFAABBCC), androidx.compose.ui.graphics.Color.White),
        com.awscherb.cardkeeper.passUi.FieldConfig("Date", "15 JUN", androidx.compose.ui.graphics.Color(0xFFAABBCC), androidx.compose.ui.graphics.Color.White),
    ),
    primaryFields = listOf(
        com.awscherb.cardkeeper.passUi.FieldConfig("FROM", "SFO", androidx.compose.ui.graphics.Color(0xFFAABBCC), androidx.compose.ui.graphics.Color.White),
        com.awscherb.cardkeeper.passUi.FieldConfig("TO", "JFK", androidx.compose.ui.graphics.Color(0xFFAABBCC), androidx.compose.ui.graphics.Color.White),
    ),
    secondaryFields = listOf(
        com.awscherb.cardkeeper.passUi.FieldConfig("PASSENGER", passengerName, androidx.compose.ui.graphics.Color(0xFFAABBCC), androidx.compose.ui.graphics.Color.White),
        com.awscherb.cardkeeper.passUi.FieldConfig("SEAT", seat, androidx.compose.ui.graphics.Color(0xFFAABBCC), androidx.compose.ui.graphics.Color.White),
    ),
    auxiliaryFields = listOf(
        com.awscherb.cardkeeper.passUi.FieldConfig("BOARDING", "08:10", androidx.compose.ui.graphics.Color(0xFFAABBCC), androidx.compose.ui.graphics.Color.White),
        com.awscherb.cardkeeper.passUi.FieldConfig("CLASS", "Economy", androidx.compose.ui.graphics.Color(0xFFAABBCC), androidx.compose.ui.graphics.Color.White),
    ),
    transit = TransitModel(
        originLabel = "SAN FRANCISCO",
        originValue = "SFO",
        destinationLabel = "NEW YORK",
        destinationValue = "JFK",
        type = TransitModel.Type.AIR
    ),
    stripPath = null,
    thumbnailPath = null,
    backItems = emptyList(),
    webServiceUrl = null,
    identifier = "pass.com.skywave.boarding",
    authenticationToken = null,
    groupingIdentifier = null,
    groupId = "pass.com.skywave.boarding|2026-06-15T08:30-07:00"
)

@Preview
@Composable
fun GroupedBoardingPassPreview() {
    CardKeeperTheme {
        PassDetailScreenInner(
            passes = listOf(
                sampleBoardingPass("sw-412-1", "John Doe", "12A"),
                sampleBoardingPass("sw-412-2", "Jane Doe", "12B"),
            ),
            backItems = emptyList(),
            isAutoUpdateOn = false,
            navOnClick = {},
            setAutoUpdate = {}
        )
    }
}

