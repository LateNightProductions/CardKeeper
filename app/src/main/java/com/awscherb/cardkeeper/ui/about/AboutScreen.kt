package com.awscherb.cardkeeper.ui.about

import android.content.Intent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.awscherb.cardkeeper.compose_common.theme.CardKeeperTheme
import com.awscherb.cardkeeper.compose_common.theme.ScaffoldScreen
import com.awscherb.cardkeeper.compose_common.theme.Typography
import com.awscherb.cardkeeper.util.AppVersion
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity

@Composable
fun AboutScreen(
    appVersion: AppVersion?,
    navOnClick: () -> Unit
) {
    val context = LocalContext.current
    ScaffoldScreen(title = "About", navOnClick = navOnClick) {
        LazyColumn(
            modifier = Modifier.padding(it),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            item {
                TextButton(
                    modifier = Modifier.padding(top = 24.dp),
                    onClick = {
                        context.startActivity(Intent(context, OssLicensesMenuActivity::class.java))
                    }) {
                    Text(
                        text = "Open Source Licenses",
                        style = Typography.titleLarge
                    )
                }
            }

            item {
                TextButton(
                    modifier = Modifier.padding(bottom = 24.dp),
                    onClick = {
                        context.startActivity(
                            Intent(
                                Intent.ACTION_VIEW,
                                "https://github.com/LateNightProductions/CardKeeper".toUri()
                            )
                        )
                    }) {
                    Text(
                        text = "GitHub Repository",
                        style = Typography.titleLarge
                    )
                }
            }

            item {
                HorizontalDivider()
            }

            item {
                Text(
                    text = "App version ${appVersion?.versionName} (${appVersion?.versionNumber})",
                    modifier = Modifier.padding(top = 24.dp),
                    style = Typography.bodyMedium
                )
            }
        }
    }
}

@Preview
@Composable
fun AboutPreview() {
    CardKeeperTheme {
        AboutScreen(
            appVersion =
                AppVersion("1.0", 100)
        ) {
        }
    }
}