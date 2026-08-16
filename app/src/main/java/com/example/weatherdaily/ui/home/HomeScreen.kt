package com.example.weatherdaily.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherdaily.ui.home.components.CurrentWeatherHeader
import com.example.weatherdaily.ui.home.components.HourlyForecastSection
import com.example.weatherdaily.ui.home.components.DailyForecastSection
import com.example.weatherdaily.ui.home.components.WeatherDetailsSection

@Composable
fun HomeScreen(
    uiState: HomeUiState = HomeUiState(),
    contentPadding: PaddingValues = PaddingValues(0.dp),
    modifier: Modifier = Modifier,
    onSearchClick: () -> Unit = {},
    onNotificationClick: () -> Unit = {},
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(bottom = contentPadding.calculateBottomPadding()),
        contentPadding = PaddingValues(bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            CurrentWeatherHeader(
                forecast = uiState.forecast,
                onSearchClick = onSearchClick,
                onNotificationClick = onNotificationClick
            )
        }
        item { HourlyForecastSection(items = uiState.forecast?.hourly.orEmpty()) }
        item { DailyForecastSection(items = uiState.forecast?.daily.orEmpty()) }
        item { WeatherDetailsSection(weather = uiState.forecast?.current) }
    }
}


@Composable
fun WeatherSectionCard(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(26.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.94f)
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Column(
            modifier = Modifier.padding(18.dp)
        ) {
            content()
        }
    }
}

@Composable
fun SectionTitle(
    title: String
) {
    Text(
        text = title,
        color = MaterialTheme.colorScheme.onSurface,
        fontSize = 17.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(bottom = 16.dp)
    )
}

@Composable
fun SkeletonBox(
    modifier: Modifier,
    color: Color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.09f)
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(50))
            .background(color)
    )
}


@Preview(
    showBackground = true,
    widthDp = 390,
    heightDp = 844
)
@Composable
private fun HomeScreenPreview() {
    MaterialTheme {
        HomeScreen()
    }
}
