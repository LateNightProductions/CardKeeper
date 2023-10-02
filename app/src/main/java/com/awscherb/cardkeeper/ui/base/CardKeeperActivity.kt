package com.awscherb.cardkeeper.ui.base

import android.content.ContentResolver
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.fragment.app.activityViewModels
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.awscherb.cardkeeper.R
import com.awscherb.cardkeeper.barcode.model.ScannedCodeModel
import com.awscherb.cardkeeper.pkpass.db.PkPassDao
import com.awscherb.cardkeeper.pkpass.model.PkPassModel
import com.awscherb.cardkeeper.pkpass.work.ImportPassWorker
import com.awscherb.cardkeeper.ui.card_detail.CardDetailViewModel
import com.awscherb.cardkeeper.ui.card_detail.CardDetailViewModelFactory
import com.awscherb.cardkeeper.ui.items.ItemsFragmentDirections
import com.awscherb.cardkeeper.ui.items.ItemsScreen
import com.awscherb.cardkeeper.ui.items.ItemsViewModel
import com.awscherb.cardkeeper.ui.items.ItemsViewModelFactory
import com.awscherb.cardkeeper.ui.pkpassDetail.PkPassViewModel
import com.awscherb.cardkeeper.ui.pkpassDetail.PkPassViewModelFactory
import com.awscherb.cardkeeper.ui.theme.Typography
import dagger.android.AndroidInjection
import kotlinx.coroutines.launch
import javax.inject.Inject

class CardKeeperActivity : AppCompatActivity() {

    private val viewModel by viewModels<ItemsViewModel> { viewModelFactory }
    private val detailViewModel by viewModels<CardDetailViewModel> { detailFactory }
    private val pkPassViewModel by viewModels<PkPassViewModel> { pkPassFactory }

    @Inject
    lateinit var viewModelFactory: ItemsViewModelFactory

    @Inject
    lateinit var detailFactory: CardDetailViewModelFactory

    @Inject
    lateinit var pkPassFactory: PkPassViewModelFactory

    @Inject
    lateinit var pkPassDao: PkPassDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)


        setContent {
            val items by viewModel.items.collectAsState(initial = emptyList())
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
                            icon = {
                                Image(
                                    painter = painterResource(id = R.mipmap.ic_launcher),
                                    contentDescription = "Items"
                                )

                            },
                            onClick = {
                                navController.navigate("Items")
                                scope.launch {
                                    drawerState.close()
                                }
                            })
                    }
                }) {
                NavHost(navController = navController, startDestination = "items") {
                    composable("items") {
                        ItemsScreen(items) {
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