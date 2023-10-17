package com.awscherb.cardkeeper.ui.base

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
import androidx.compose.runtime.rememberCoroutineScope
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
import com.awscherb.cardkeeper.pkpass.db.PkPassDao
import com.awscherb.cardkeeper.pkpass.model.PkPassModel
import com.awscherb.cardkeeper.pkpass.work.ImportPassWorker
import com.awscherb.cardkeeper.ui.scannedCode.ScannedCodeScreen
import com.awscherb.cardkeeper.ui.items.ItemsScreen
import com.awscherb.cardkeeper.ui.pkpassDetail.PassDetailScreen
import com.awscherb.cardkeeper.ui.scan.ScanScreen
import com.awscherb.cardkeeper.ui.theme.Typography
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class CardKeeperActivity : ComponentActivity() {


    @Inject
    lateinit var pkPassDao: PkPassDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()
            val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
            val scope = rememberCoroutineScope()

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
                            label = { Text("Items") },
                            selected = false,
                            onClick = {
                                navController.navigate("items") {
                                    popUpTo("items") {
                                        inclusive = true
                                    }
                                }
                                scope.launch {
                                    drawerState.close()
                                }
                            })
                        NavigationDrawerItem(
                            label = { Text("Scan") },
                            selected = false,
                            onClick = {
                                navController.navigate("scan") {
                                    popUpTo("items") {
                                        inclusive = false
                                    }
                                }
                                scope.launch {
                                    drawerState.close()
                                }
                            })
                    }
                }) {
                NavHost(navController = navController, startDestination = "items") {
                    composable("items") {
                        ItemsScreen(
                            scanOnClick = {
                                navController.navigate("scan")
                            },
                            navOnClick = {
                                scope.launch { drawerState.open() }
                            }) {

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
                        PassDetailScreen {
                            scope.launch { drawerState.open() }
                        }
                    }
                    composable(
                        "code/{codeId}", arguments =
                        listOf(navArgument("codeId") {
                            type = NavType.IntType
                        })
                    ) {
                        ScannedCodeScreen(onDelete = {
                            navController.navigate("items") {
                                popUpTo("items") {
                                    inclusive = false
                                }
                            }
                        }) {
                            scope.launch { drawerState.open() }
                        }
                    }
                    composable("scan") {
                        ScanScreen(completion = {
                            navController.navigate("items")
                        }) {
                            scope.launch { drawerState.open() }
                        }
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