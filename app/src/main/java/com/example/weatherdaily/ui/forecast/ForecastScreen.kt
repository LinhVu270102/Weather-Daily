package com.example.weatherdaily.ui.forecast

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Cloud
import androidx.compose.material.icons.outlined.Tune
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherdaily.ui.home.SkeletonBox
import com.example.weatherdaily.ui.map.WeatherMapScreen
import com.example.weatherdaily.ui.theme.SkyBlue
import com.example.weatherdaily.domain.model.DailyWeather
import com.example.weatherdaily.domain.model.WeatherCondition
import com.example.weatherdaily.domain.model.WeatherForecast
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.roundToInt

@Composable
fun ForecastScreen(
    forecast: WeatherForecast? = null,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier.fillMaxSize().padding(bottom = contentPadding.calculateBottomPadding()),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        item { ForecastHeader(locationName = forecast?.location?.name) }
        item { TemperatureOverviewCard(days = forecast?.daily.orEmpty()) }
        if (forecast == null) {
            items(7) { ForecastDayCard() }
        } else {
            items(forecast.daily, key = { it.dateEpochSeconds }) { ForecastDayCard(it) }
        }
    }
}

@Composable
private fun ForecastHeader(locationName: String?) {
    Row(
        Modifier.fillMaxWidth().statusBarsPadding().padding(top = 4.dp, bottom = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(Modifier.weight(1f)) {
            Text("Dự báo", color = Color.White, fontSize = 30.sp, fontWeight = FontWeight.Bold)
            Text(
                locationName?.let { "7 ngày tới tại $it" } ?: "Tổng quan thời tiết 7 ngày",
                color = Color.White.copy(.72f), fontSize = 13.sp
            )
        }
        IconButton(onClick = {}, modifier = Modifier.background(Color.White.copy(.14f), CircleShape)) {
            Icon(Icons.Outlined.Tune, "Bộ lọc", tint = Color.White)
        }
    }
}

@Composable
private fun TemperatureOverviewCard(days: List<DailyWeather>) {
    Card(
        shape = RoundedCornerShape(26.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surface.copy(.94f)),
    ) {
        Column(Modifier.fillMaxWidth().padding(18.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Outlined.CalendarMonth, null, tint = SkyBlue)
                Spacer(Modifier.width(9.dp))
                Text("Biểu đồ nhiệt độ", fontWeight = FontWeight.Bold, fontSize = 17.sp)
            }
            Spacer(Modifier.height(20.dp))
            Box(Modifier.fillMaxWidth().height(130.dp), contentAlignment = Alignment.Center) {
                SkeletonBox(Modifier.fillMaxWidth().height(2.dp))
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround, verticalAlignment = Alignment.Bottom) {
                    if (days.isEmpty()) {
                        repeat(7) { SkeletonBox(Modifier.width(10.dp).height((35 + it % 3 * 18).dp), SkyBlue.copy(.32f)) }
                    } else {
                        days.take(7).forEach { day ->
                            val barHeight = (day.maximumTemperatureCelsius.coerceIn(10.0, 45.0) * 2).dp
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("${day.maximumTemperatureCelsius.roundToInt()}°", fontSize = 10.sp)
                                Spacer(Modifier.height(4.dp))
                                Box(Modifier.width(12.dp).height(barHeight).background(SkyBlue.copy(.55f), RoundedCornerShape(8.dp)))
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ForecastDayCard(day: DailyWeather? = null) {
    Card(
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surface.copy(.94f)),
    ) {
        Row(Modifier.fillMaxWidth().padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Column(Modifier.weight(1f)) {
                if (day == null) {
                    SkeletonBox(Modifier.width(90.dp).height(15.dp))
                    Spacer(Modifier.height(8.dp))
                    SkeletonBox(Modifier.width(125.dp).height(10.dp))
                } else {
                    Text(
                        SimpleDateFormat("EEEE, dd/MM", Locale.forLanguageTag("vi"))
                            .format(Date(day.dateEpochSeconds * 1000)),
                        fontWeight = FontWeight.SemiBold,
                    )
                    Spacer(Modifier.height(5.dp))
                    Text(
                        "${day.condition.toVietnamese()} · Mưa ${day.precipitationProbabilityPercent}%",
                        fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurface.copy(.55f),
                    )
                }
            }
            Box(Modifier.size(45.dp).background(SkyBlue.copy(.12f), CircleShape), contentAlignment = Alignment.Center) {
                Icon(Icons.Outlined.Cloud, null, tint = SkyBlue)
            }
            Spacer(Modifier.width(20.dp))
            if (day == null) SkeletonBox(Modifier.width(58.dp).height(20.dp))
            else Text(
                "${day.maximumTemperatureCelsius.roundToInt()}° / ${day.minimumTemperatureCelsius.roundToInt()}°",
                fontWeight = FontWeight.Bold,
            )
        }
    }
}

private fun WeatherCondition.toVietnamese() = when (this) {
    WeatherCondition.CLEAR -> "Trời quang"
    WeatherCondition.PARTLY_CLOUDY -> "Có mây"
    WeatherCondition.CLOUDY -> "Nhiều mây"
    WeatherCondition.FOG -> "Sương mù"
    WeatherCondition.DRIZZLE -> "Mưa phùn"
    WeatherCondition.RAIN -> "Có mưa"
    WeatherCondition.HEAVY_RAIN -> "Mưa lớn"
    WeatherCondition.SNOW -> "Có tuyết"
    WeatherCondition.THUNDERSTORM -> "Có giông"
    WeatherCondition.UNKNOWN -> "Không xác định"
}
@Preview(
    showBackground = true,
    widthDp = 390,
    heightDp = 844
)

@Composable
private fun ForecastScreenPreview () {
    MaterialTheme{
        ForecastScreen(
            contentPadding = PaddingValues(
                bottom = 80.dp
            )
        )
    }
}
