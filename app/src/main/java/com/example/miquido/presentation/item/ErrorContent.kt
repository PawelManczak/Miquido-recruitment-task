package com.example.miquido.presentation.item

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.miquido.presentation.theme.MiquidoTheme

@Composable
fun ErrorContent(onRetry: () -> Unit) {
    Box(
        Modifier
            .fillMaxSize()
            .clickable { onRetry() },
        contentAlignment = Alignment.Center
    ) {
        Icon(imageVector = Icons.Default.Refresh, contentDescription = null)
    }
}

@Composable
@Preview(showBackground = true)
fun ErrorContentPreview() {
    MiquidoTheme {
        Box(modifier = Modifier.size(64.dp)){
            ErrorContent(onRetry = {})
        }
    }
}