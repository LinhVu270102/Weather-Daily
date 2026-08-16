package com.example.weatherdaily.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Map
import androidx.compose.material.icons.outlined.MoreHoriz
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.weatherdaily.ui.forecast.ForecastScreen
import com.example.weatherdaily.ui.home.HomeScreen
import com.example.weatherdaily.ui.home.HomeViewModel
import com.example.weatherdaily.ui.map.WeatherMapScreen
import com.example.weatherdaily.ui.map.MapViewModel
import com.example.weatherdaily.ui.search.SearchScreen
import com.example.weatherdaily.ui.search.SearchViewModel
import com.example.weatherdaily.ui.settings.MoreScreen
import com.example.weatherdaily.ui.theme.DeepBlue
import com.example.weatherdaily.ui.theme.LightSkyBlue
import com.example.weatherdaily.ui.theme.SkyBlue

sealed class AppDestination(
    val route: String,
    val label: String? = null,
    val icon: ImageVector? = null,
) {
    data object Home : AppDestination("home", "Hôm nay", Icons.Outlined.Home)
    data object Forecast : AppDestination("forecast", "Dự báo", Icons.Outlined.CalendarMonth)
    data object Map : AppDestination("map", "Bản đồ", Icons.Outlined.Map)
    data object Settings : AppDestination("settings", "Khác", Icons.Outlined.MoreHoriz)
    data object Search : AppDestination("search")
}

private val bottomBarDestinations = listOf(
    AppDestination.Home,
    AppDestination.Forecast,
    AppDestination.Map,
    AppDestination.Settings,
)

@Composable
fun WeatherApp() {
    val navController = rememberNavController()
    val homeViewModel: HomeViewModel = viewModel()
    val homeUiState = homeViewModel.uiState.collectAsStateWithLifecycle().value
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
    val showBottomBar = currentRoute == null || bottomBarDestinations.any { it.route == currentRoute }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(LightSkyBlue, SkyBlue, DeepBlue))),
        containerColor = Color.Transparent,
        bottomBar = { if (showBottomBar) WeatherBottomBar(navController) },
    ) { contentPadding ->
        NavHost(
            navController = navController,
            startDestination = AppDestination.Home.route,
            modifier = Modifier
                .fillMaxSize()
                .background(Brush.verticalGradient(listOf(LightSkyBlue, SkyBlue, DeepBlue))),
        ) {
            composable(AppDestination.Home.route) {
                HomeScreen(
                    uiState = homeUiState,
                    contentPadding = contentPadding,
                    onSearchClick = { navController.navigate(AppDestination.Search.route) },
                    onNotificationClick = {},
                )
            }
            composable(AppDestination.Forecast.route) {
                ForecastScreen(
                    forecast = homeUiState.forecast,
                    contentPadding = contentPadding,
                )
            }
            composable(AppDestination.Map.route) {
                val mapViewModel: MapViewModel = viewModel()
                val mapUiState = mapViewModel.uiState.collectAsStateWithLifecycle().value
                val location = homeUiState.forecast?.location
                LaunchedEffect(location?.latitude, location?.longitude) {
                    if (location != null) mapViewModel.loadWeather(location)
                }
                WeatherMapScreen(
                    forecast = mapUiState.forecast,
                    gridPoints = mapUiState.gridPoints,
                    isLoading = mapUiState.isLoading,
                    errorMessage = mapUiState.errorMessage,
                    contentPadding = contentPadding,
                )
            }
            composable(AppDestination.Settings.route) {
                MoreScreen(contentPadding = contentPadding)
            }
            composable(AppDestination.Search.route) {
                val searchViewModel: SearchViewModel = viewModel()
                val uiState = searchViewModel.uiState.collectAsStateWithLifecycle().value
                SearchScreen(
                    uiState = uiState,
                    onQueryChange = searchViewModel::onQueryChange,
                    onSearch = searchViewModel::startSearching,
                    onLocationClick = { location ->
                        searchViewModel.addRecentLocation(location)
                        homeViewModel.loadWeather(location)
                        navController.popBackStack(AppDestination.Home.route, false)
                    },
                    onBackClick = { navController.popBackStack() },
                )
            }
        }
    }
}

@Composable
private fun WeatherBottomBar(navController: NavHostController) {
    val currentDestination = navController.currentBackStackEntryAsState().value?.destination

    NavigationBar(
        modifier = Modifier.navigationBarsPadding(),
        containerColor = MaterialTheme.colorScheme.surface.copy(alpha = .96f),
    ) {
        bottomBarDestinations.forEach { destination ->
            val selected = currentDestination?.hierarchy?.any { it.route == destination.route } == true
            NavigationBarItem(
                selected = selected,
                onClick = {
                    navController.navigate(destination.route) {
                        popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = { Icon(requireNotNull(destination.icon), destination.label) },
                label = { Text(destination.label.orEmpty(), fontSize = 11.sp) },
            )
        }
    }
}
