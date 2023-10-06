package com.awscherb.cardkeeper.ui.pkpassDetail

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.awscherb.cardkeeper.pkpass.model.PkPassModel
import com.awscherb.cardkeeper.pkpass.model.parseHexColor
import com.awscherb.cardkeeper.ui.view.PkPassHeaderView
import com.awscherb.cardkeeper.ui.view.ScaffoldScreen

@Composable
fun PassDetailScreen(
    passDetailViewModel: PkPassViewModel = hiltViewModel(),
    navOnClick: () -> Unit
) {
    val pass by passDetailViewModel.pass.collectAsState(initial = null)

    ScaffoldScreen(title = "Pass", navOnClick = navOnClick) {
        pass?.let { pass ->

            Card(
                modifier = Modifier.padding(it)
                    .padding(horizontal = 8.dp, vertical = 8.dp),

                colors = CardDefaults.cardColors(
                    containerColor = Color(pass.backgroundColor.parseHexColor())
                )
            ) {
                PkPassHeaderView(pass)
            }
        }
    }
}