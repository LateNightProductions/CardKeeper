@file:OptIn(ExperimentalPermissionsApi::class)

package com.awscherb.cardkeeper.ui.base

import android.Manifest
import android.content.ContentResolver
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.awscherb.cardkeeper.barcode.model.ScannedCodeModel
import com.awscherb.cardkeeper.pkpass.model.PkPassModel
import com.awscherb.cardkeeper.pkpass.work.ImportPassWorker
import com.awscherb.cardkeeper.ui.create.CreateScreen
import com.awscherb.cardkeeper.ui.items.ItemsScreen
import com.awscherb.cardkeeper.ui.pkpassDetail.PassDetailScreen
import com.awscherb.cardkeeper.ui.scan.PermissionsScreen
import com.awscherb.cardkeeper.ui.scan.ScanScreen
import com.awscherb.cardkeeper.ui.scannedCode.ScannedCodeScreen
import com.awscherb.cardkeeper.ui.theme.Typography
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CardKeeperActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val cameraPermissionState = rememberPermissionState(Manifest.permission.CAMERA)
            val navController = rememberNavController()
            var selectedItem by remember {
                mutableStateOf<Destination>(Destination.Items)
            }
            val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
            val scope = rememberCoroutineScope()

            val openDrawer: () -> Unit = { scope.launch { drawerState.open() } }
            val topLevelNav: (Destination, Boolean) -> Unit = { dest, pop ->
                selectedItem = dest
                navController.navigate(dest.dest) {
                    popUpTo(Destination.Items.dest) {
                        inclusive = pop
                    }
                }
                scope.launch { drawerState.close() }
            }

            ModalNavigationDrawer(
                drawerState = drawerState,
                drawerContent = {
                    ModalDrawerSheet {
                        Text(
                            text = "CardKeeper",
                            modifier = Modifier.padding(
                                top = 36.dp,
                                start = 16.dp,
                                end = 16.dp,
                                bottom = 24.dp
                            ),
                            style = Typography.headlineLarge
                        )
                        Divider()
                        NavigationDrawerItem(
                            label = { Text(Destination.Items.label) },
                            selected = selectedItem == Destination.Items,
                            onClick = {
                                topLevelNav(Destination.Items, true)
                            })
                        NavigationDrawerItem(
                            label = { Text(Destination.Scan.label) },
                            selected = selectedItem == Destination.Scan,
                            onClick = {
                                topLevelNav(Destination.Scan, false)
                            })
                        NavigationDrawerItem(
                            label = { Text(Destination.Create.label) },
                            selected = selectedItem == Destination.Create,
                            onClick = {
                                topLevelNav(Destination.Create, false)
                            })
                    }
                }) {
                NavHost(navController = navController, startDestination = "items") {
                    composable("items") {
                        ItemsScreen(
                            scanOnClick = {
                                navController.navigate("scan")
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
                        "pass/{passId}", arguments =
                        listOf(navArgument("passId") {
                            type = NavType.StringType
                        })
                    ) {
                        PassDetailScreen(navOnClick = openDrawer)
                    }
                    composable(
                        "code/{codeId}", arguments =
                        listOf(navArgument("codeId") {
                            type = NavType.IntType
                        })
                    ) {
                        ScannedCodeScreen(onDelete = {
                            topLevelNav(Destination.Items, false)
                        }, navOnClick = openDrawer)
                    }
                    composable("scan") {
                        if (cameraPermissionState.status.isGranted) {
                            ScanScreen(completion = {
                                topLevelNav(Destination.Items, true)
                            }, navOnClick = openDrawer)
                        } else {
                            PermissionsScreen(navOnClick = openDrawer)
                        }
                    }
                    composable("create") {
                        CreateScreen(
                            completion = { topLevelNav(Destination.Items, true) },
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

            WorkManager.getInstance(this)
                .enqueue(req)
        }
    }
}