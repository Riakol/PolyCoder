package com.example.wikianimals.ui_components

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.wikianimals.utils.ListItem

@Composable
fun InfoScreen(item: ListItem) {
    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(5.dp),
        shape = RoundedCornerShape(10.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            AssetImage(
                imageName = item.imageName,
                contentDescription = item.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
            )
            HtmlLoader(htmlName = item.htmlName)
        }
    }
}

@Composable
fun HtmlLoader(htmlName: String) {
    val context = LocalContext.current
    var htmlString: String? = null
    val assetPath = "html/$htmlName"

    context.assets.open(assetPath).use { inputStream ->
        val size = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        htmlString = String(buffer, Charsets.UTF_8)
    }

    if (htmlString != null) {
        AndroidView(
            factory = { ctx ->
                WebView(ctx).apply {
                    webViewClient = WebViewClient()
                    // settings.javaScriptEnabled = true

                    loadDataWithBaseURL(
                        "file:///android_asset/html/",
                        htmlString!!,
                        "text/html",
                        "UTF-8",
                        null
                    )
                }
            }
        )
    }
}