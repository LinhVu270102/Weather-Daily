package com.example.weatherdaily.ui.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import com.example.weatherdaily.domain.model.DailyWeather
import com.example.weatherdaily.ui.home.SectionTitle
import com.example.weatherdaily.ui.home.SkeletonBox
import com.example.weatherdaily.ui.home.WeatherSectionCard
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.roundToInt

@Composable
fun DailyForecastSection(
    items: List<DailyWeather>,
    modifier: Modifier = Modifier
) {
    WeatherSectionCard(modifier = modifier) {
        SectionTitle(title = "Dự báo 7 ngày")

        if (items.isEmpty()) {
            repeat(5) { DailyForecastPlaceholder() }
        } else {
            items.take(7).forEach { DailyForecastItem(it) }
        }
    }
}

@Composable
private fun DailyForecastPlaceholder() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        SkeletonBox(
            modifier = Modifier
                .width(85.dp)
                .height(14.dp)
        )

        Spacer(modifier = Modifier.weight(1f))

        Box(
            modifier = Modifier
                .size(32.dp)
                .background(
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f),
                    shape = CircleShape
                )
        )

        Spacer(modifier = Modifier.width(24.dp))

        SkeletonBox(
            modifier = Modifier
                .width(30.dp)
                .height(15.dp)
        )

        Spacer(modifier = Modifier.width(14.dp))

        SkeletonBox(
            modifier = Modifier
                .width(30.dp)
                .height(15.dp)
        )
    }
}

@Composable
private fun DailyForecastItem(weather: DailyWeather) {
    Row(
        Modifier.fillMaxWidth().padding(vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            SimpleDateFormat("EEE, dd/MM", Locale.forLanguageTag("vi")).format(Date(weather.dateEpochSeconds * 1000)),
            Modifier.weight(1f), fontSize = 13.sp,
        )
        Box(Modifier.size(32.dp).background(MaterialTheme.colorScheme.primary.copy(.14f), CircleShape))
        Spacer(Modifier.width(20.dp))
        Text("${weather.precipitationProbabilityPercent}%", color = MaterialTheme.colorScheme.primary, fontSize = 11.sp)
        Spacer(Modifier.width(16.dp))
        Text("${weather.maximumTemperatureCelsius.roundToInt()}°", fontWeight = FontWeight.Bold)
        Spacer(Modifier.width(12.dp))
        Text("${weather.minimumTemperatureCelsius.roundToInt()}°", color = MaterialTheme.colorScheme.onSurface.copy(.5f))
    }
}
