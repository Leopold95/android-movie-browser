package com.leopold95.moviebrowser.ui

import androidx.compose.ui.graphics.vector.ImageVector

data class NavigationItem(
    val label: String,
    val icon: ImageVector,
    val screen: ApplicationScreen
)