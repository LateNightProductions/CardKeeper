@file:OptIn(ExperimentalPermissionsApi::class, ExperimentalPermissionsApi::class)

package com.awscherb.cardkeeper.ui.base

import android.Manifest
import android.content.ContentResolver
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.awscherb.cardkeeper.barcode.model.ScannedCodeModel
import com.awscherb.cardkeeper.pkpass.model.PkPassModel
import com.awscherb.cardkeeper.pkpass.work.ImportPassWorker
import com.awscherb.cardkeeper.ui.about.AboutScreen
import com.awscherb.cardkeeper.ui.create.CreateScreen
import com.awscherb.cardkeeper.ui.items.ItemsScreen
import com.awscherb.cardkeeper.ui.pkpassDetail.PassDetailScreen
import com.awscherb.cardkeeper.ui.scan.PermissionsScreen
import com.awscherb.cardkeeper.ui.scan.ScanScreen
import com.awscherb.cardkeeper.ui.scannedCode.ScannedCodeScreen
import com.awscherb.cardkeeper.ui.theme.CardKeeperTheme
import com.awscherb.cardkeeper.util.getAppVersion
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CardKeeperActivity : ComponentActivity() {

    companion object {
        const val EXTRA_START_SCAN = "extra_start_scan"
    }

    private lateinit var navController: NavHostController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val startScan = intent.getBooleanExtra(EXTRA_START_SCAN, false)
        val startDest = if (startScan) Destination.Scan else Destination.Items

        setContent {
            CardKeeperTheme {

                val cameraPermissionState = rememberPermissionState(Manifest.permission.CAMERA)
                navController = rememberNavController()
                var selectedItem by remember {
                    mutableStateOf(startDest)
                }
                val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
                val scope = rememberCoroutineScope()

                val openDrawer: () -> Unit = { scope.launch { drawerState.open() } }
                val popBack: () -> Unit = { navController.popBackStack() }
                val topLevelNav: (Destination) -> Unit = { dest ->
                    selectedItem = dest
                    navController.popBackStack()
                    navController.navigate(dest.dest)
                    scope.launch { drawerState.close() }
                }

                ModalNavigationDrawer(
                    drawerState = drawerState,
                    drawerContent = {
                        NavDrawerContent(
                            selectedItem = selectedItem,
                            topLevelNav = topLevelNav
                        )
                    }) {
                    NavHost(
                        navController = navController, startDestination = startDest.dest
                    ) {
                        composable(Destination.Items.dest) {
                            ItemsScreen(
                                scanOnClick = {
                                    navController.navigate(Destination.Scan.dest)
                                },
                                navOnClick = openDrawer
                            ) {

                                when (it) {
                                    is PkPassModel ->
                                        navController.navigate("pass/${it.id}")

                                    is ScannedCodeModel ->
                                        navController.navigate("code/${it.id}")
                                }

                            }
                        }
                        composable(
                            Destination.Pass.dest, arguments =
                            listOf(navArgument("passId") {
                                type = NavType.StringType
                            })
                        ) {
                            PassDetailScreen(navOnClick = popBack, onDelete = popBack)
                        }
                        composable(
                            Destination.Code.dest, arguments =
                            listOf(navArgument("codeId") {
                                type = NavType.IntType
                            })
                        ) {
                            ScannedCodeScreen(onDelete = {
                                topLevelNav(Destination.Items)
                            }, navOnClick = popBack)
                        }
                        composable(Destination.Scan.dest) {
                            if (cameraPermissionState.status.isGranted) {
                                ScanScreen(completion = {
                                    topLevelNav(Destination.Items)
                                }, navOnClick = openDrawer)
                            } else {
                                PermissionsScreen(navOnClick = openDrawer)
                            }
                        }
                        composable(Destination.Create.dest) {
                            CreateScreen(
                                completion = { topLevelNav(Destination.Items) },
                                navOnClick = openDrawer
                            )
                        }
                        composable(Destination.About.dest) {
                            AboutScreen(
                                appVersion = getAppVersion(this@CardKeeperActivity),
                                navOnClick = openDrawer
                            )
                        }
                    }
                }
            }

            val uri = intent.data
            val scheme = uri?.scheme

            if (scheme != null) {
                val type: String
                val passUri: String
                when (ContentResolver.SCHEME_CONTENT == scheme) {
                    true -> {
                        type = ImportPassWorker.TYPE_URI
                        passUri = uri.toString()
                    }

                    false -> {
                        type = ImportPassWorker.TYPE_FILE
                        passUri = uri.encodedPath ?: ""
                    }

                }

                val req = OneTimeWorkRequestBuilder<ImportPassWorker>()
                    .setInputData(
                        workDataOf(
                            ImportPassWorker.INPUT_TYPE to type,
                            ImportPassWorker.URI to passUri
                        )
                    )
                    .build()

                with(WorkManager.getInstance(this)) {
                    enqueue(req)

                    getWorkInfoByIdFlow(req.id)
                        .onEach {
                            when (it.state) {
                                WorkInfo.State.SUCCEEDED -> {
                                    it.outputData.getString(ImportPassWorker.KEY_PASS_ID)
                                        ?.let { passId ->
                                            navController.navigate("pass/$passId")
                                        }
                                }

                                WorkInfo.State.FAILED -> {
                                    Snackbar.make(
                                        findViewById(android.R.id.content),
                                        "There was an error opening your pass",
                                        Snackbar.LENGTH_LONG
                                    ).show()
                                }

                                else -> {
                                }
                            }
                        }.launchIn(lifecycleScope)
                }
            }
        }
    }
}