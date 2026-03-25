package com.leopold95.moviebrowser

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.leopold95.moviebrowser.ui.ApplicationLayout
import com.leopold95.moviebrowser.ui.theme.MovieBrowserTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MovieBrowserTheme {
                ApplicationLayout()
            }
        }
    }
}
