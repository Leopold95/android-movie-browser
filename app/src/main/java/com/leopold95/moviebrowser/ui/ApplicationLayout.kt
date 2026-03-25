package com.leopold95.moviebrowser.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.leopold95.moviebrowser.R
import com.leopold95.moviebrowser.screens.details.DetailsView
import com.leopold95.moviebrowser.screens.favourites.FavouritesView
import com.leopold95.moviebrowser.screens.home.HomeView
import kotlin.reflect.typeOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ApplicationLayout(){
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val headerHome = stringResource(R.string.header_home)
    val headerFavourites = stringResource(R.string.header_favourites)

    val list = remember {
        listOf(
            NavigationItem(headerHome, Icons.Rounded.Home, ApplicationScreen.Home),
            NavigationItem(headerFavourites, Icons.Rounded.FavoriteBorder, ApplicationScreen.Favourites),
        )
    }

    val disciplineDetails = mapOf(typeOf<Long>() to NavType.LongType)

    val header = when {
        currentDestination?.hasRoute(ApplicationScreen.Home::class) == true ->
            stringResource(R.string.header_home)
        currentDestination?.hasRoute(ApplicationScreen.Favourites::class) == true ->
            stringResource(R.string.header_favourites)
        currentDestination?.hasRoute(ApplicationScreen.Details::class) == true ->
            stringResource(R.string.header_details)
        else -> stringResource(R.string.header_home)
    }

    val isDetailsScreen = currentDestination?.hasRoute(ApplicationScreen.Details::class) == true

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 6.dp, end = 6.dp),
        topBar = {
            TopAppBar(
                title = {
                    Text(header)
                },
                navigationIcon = {
                    if (isDetailsScreen) {
                        IconButton(onClick = { navController.navigateUp() }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    }
                }
            )
        },
        bottomBar = {
            if (!isDetailsScreen) {
                NavigationBar {
                    list.forEach { item ->
                        NavigationBarItem(
                            selected = currentDestination?.hasRoute(item.screen::class) == true,
                            onClick = {
                                navController.navigate(item.screen) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            label = { Text(item.label) },
                            icon = { Icon(item.icon, contentDescription = null) }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
        ) {
            NavHost(navController, startDestination = ApplicationScreen.Home){
                composable<ApplicationScreen.Home> {
                    HomeView(navController)
                }

                composable<ApplicationScreen.Details>(
                    typeMap = disciplineDetails
                ) { backStackEntry ->
                    val movieId = backStackEntry.arguments?.getLong("movieId") ?: 0L
                    DetailsView(movieId)
                }

                composable<ApplicationScreen.Favourites> {
                    FavouritesView(navController)
                }
            }
        }
    }
}