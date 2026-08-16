package com.example.weatherdaily.ui.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import com.example.weatherdaily.domain.model.HourlyWeather
import com.example.weatherdaily.ui.home.SectionTitle
import com.example.weatherdaily.ui.home.SkeletonBox
import com.example.weatherdaily.ui.home.WeatherSectionCard
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.roundToInt

@Composable
fun HourlyForecastSection(
    items: List<HourlyWeather>,
    modifier: Modifier = Modifier
) {
    WeatherSectionCard(modifier = modifier) {
        SectionTitle(title = "Dự báo theo giờ")

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(vertical = 4.dp)
        ) {
            if (items.isEmpty()) {
                items(count = 5) { HourlyForecastPlaceholder() }
            } else {
                items(items.take(24), key = { it.timeEpochSeconds }) { HourlyForecastItem(it) }
            }
        }
    }
}

@Composable
private fun HourlyForecastPlaceholder() {
    Column(
        modifier = Modifier
            .width(68.dp)
            .clip(RoundedCornerShape(18.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(vertical = 14.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SkeletonBox(
            modifier = Modifier
                .width(38.dp)
                .height(10.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        Box(
            modifier = Modifier
                .size(34.dp)
                .background(
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f),
                    shape = CircleShape
                )
        )

        Spacer(modifier = Modifier.height(12.dp))

        SkeletonBox(
            modifier = Modifier
                .width(30.dp)
                .height(16.dp)
        )
    }
}

@Composable
private fun HourlyForecastItem(weather: HourlyWeather) {
    Column(
        Modifier.width(68.dp).clip(RoundedCornerShape(18.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant).padding(vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date(weather.timeEpochSeconds * 1000)),
            fontSize = 11.sp,
        )
        Spacer(Modifier.height(10.dp))
        Box(Modifier.size(30.dp).background(MaterialTheme.colorScheme.primary.copy(.16f), CircleShape))
        Spacer(Modifier.height(10.dp))
        Text("${weather.temperatureCelsius.roundToInt()}°", fontWeight = FontWeight.Bold)
    }
}
