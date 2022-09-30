package com.awscherb.cardkeeper.ui.screens

import android.widget.ImageView
import android.widget.ImageView.ScaleType
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Colors
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.awscherb.cardkeeper.R
import com.awscherb.cardkeeper.data.model.ScannedCode
import com.awscherb.cardkeeper.ui.card_detail.CardDetailViewModel
import com.awscherb.cardkeeper.ui.cards.CardsFragmentComposeDirections
import com.awscherb.cardkeeper.ui.cards.CardsViewModel
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder

@Composable
fun CardList(
    navController: NavController, viewModel: CardsViewModel, detailViewModel: CardDetailViewModel
) {
    val cardListState by viewModel.cards.collectAsState(emptyList())
    val queryState by viewModel.searchQuery.collectAsState("")

    var isSearching by remember { mutableStateOf(false) }

    Scaffold(topBar = {
        if (isSearching) {
            TopAppBar(title = {
                TextField(value = queryState, onValueChange = { viewModel.searchQuery.value = it })
            }, actions = {
                IconButton(onClick = {
                    viewModel.searchQuery.value = ""
                    isSearching = false
                }) {
                    Icon(Icons.Default.Close, null)
                }
            })
        } else {
            TopAppBar(title = {
                Text(text = stringResource(id = R.string.app_name))
            }, actions = {
                IconButton(onClick = { isSearching = true }) {
                    Icon(Icons.Default.Search, null)
                }
                IconButton(onClick = {
                    navController.navigate(
                        CardsFragmentComposeDirections.actionCardsFragmentToCreateFragment()
                    )
                }) {
                    Icon(Icons.Default.Edit, null)
                }
            })
        }


    }, floatingActionButton = {
        FloatingActionButton(onClick = {
            navController.navigate(
                CardsFragmentComposeDirections.actionCardsFragmentToScanFragment()
            )
        }) {
            Icon(imageVector = ImageVector.vectorResource(id = R.drawable.ic_camera), null)
        }
    }) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .background(color = Color.DarkGray)
        ) {
            items(cardListState, { card -> card.id }) { card ->
                CardListItem(cardItem = card, onClick = {
                    detailViewModel.cardId.value = it.id
                    navController.navigate(
                        CardsFragmentComposeDirections.actionCardsFragmentToCardDetailFragment(
                            it.id
                        )
                    )
                })
            }
        }
    }


}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CardListItem(cardItem: ScannedCode, onClick: (ScannedCode) -> Unit) {

    Card(
        onClick = { onClick(cardItem) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, start = 16.dp, end = 16.dp, bottom = 8.dp),

        ) {
        Column {
            Text(
                text = cardItem.title, modifier = Modifier.padding(16.dp)
            )
            ScannedCodeImage(cardItem = cardItem)
        }
    }

}

