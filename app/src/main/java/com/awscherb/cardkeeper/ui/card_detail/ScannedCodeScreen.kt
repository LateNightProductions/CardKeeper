package com.awscherb.cardkeeper.ui.card_detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.awscherb.cardkeeper.barcode.model.ScannedCodeModel
import com.awscherb.cardkeeper.pkpass.model.parseHexColor
import com.awscherb.cardkeeper.ui.theme.Typography
import com.awscherb.cardkeeper.ui.view.ScaffoldScreen
import com.awscherb.cardkeeper.util.EncoderHolder

@Composable
fun ScannedCodeScreen(
    scannedCodeViewModel: ScannedCodeViewModel = hiltViewModel(),
    navOnClick: () -> Unit
) {

    val code by scannedCodeViewModel.card.collectAsState(initial = null)

    ScaffoldScreen(title = "Code", navOnClick = navOnClick) {
        code?.let { code ->
            ScannedCodeDetail(
                code = code,
                paddingValues = it
            )
        }
    }
}

@Composable
fun ScannedCodeDetail(
    code: ScannedCodeModel,
    paddingValues: PaddingValues
) {
    Card(
        modifier = Modifier
            .padding(paddingValues)
            .padding(horizontal = 8.dp, vertical = 8.dp),
    ) {
        Text(
            text = code.title,
            style = Typography.bodyLarge,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 8.dp)
        )

        // Load image
        val bitmap =
            EncoderHolder.encoder.encodeBitmap(code.text, code.format, 200, 200)

        Image(
            bitmap = bitmap.asImageBitmap(),
            contentDescription = "",
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(start = 8.dp, end = 8.dp, bottom = 8.dp)
        )

        Text(
            text = code.text,
            style = Typography.bodyMedium,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 8.dp)
        )
    }
}