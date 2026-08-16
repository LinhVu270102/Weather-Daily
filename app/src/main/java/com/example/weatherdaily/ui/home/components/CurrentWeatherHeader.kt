package com.example.weatherdaily.ui.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.WaterDrop
import androidx.compose.material.icons.outlined.WbSunny
import androidx.compose.material.icons.outlined.WindPower
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherdaily.ui.home.SkeletonBox
import com.example.weatherdaily.domain.model.WeatherCondition
import com.example.weatherdaily.domain.model.WeatherForecast
import kotlin.math.roundToInt

@Composable
fun CurrentWeatherHeader(
    forecast: WeatherForecast?,
    onSearchClick: () -> Unit,
    onNotificationClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(
                horizontal = 20.dp,
                vertical = 16.dp
            )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                if (forecast == null) {
                    SkeletonBox(Modifier.width(130.dp).height(22.dp), Color.White.copy(.35f))
                    Spacer(Modifier.height(8.dp))
                    SkeletonBox(Modifier.width(175.dp).height(13.dp), Color.White.copy(.25f))
                } else {
                    Text(forecast.location.name, color = Color.White, fontSize = 22.sp)
                    Spacer(Modifier.height(4.dp))
                    Text(
                        listOfNotNull(forecast.location.administrativeArea, forecast.location.country)
                            .distinct().joinToString(", "),
                        color = Color.White.copy(.72f), fontSize = 12.sp
                    )
                }
            }

            HeaderIconButton(
                icon = Icons.Outlined.Search,
                contentDescription = "Tìm kiếm",
                onClick = onSearchClick
            )

            Spacer(modifier = Modifier.width(8.dp))

            HeaderIconButton(
                icon = Icons.Outlined.Notifications,
                contentDescription = "Thông báo",
                onClick = onNotificationClick
            )
        }

        Spacer(modifier = Modifier.height(36.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                if (forecast == null) {
                    SkeletonBox(Modifier.width(120.dp).height(70.dp), Color.White.copy(.35f))
                    Spacer(Modifier.height(12.dp))
                    SkeletonBox(Modifier.width(100.dp).height(20.dp), Color.White.copy(.3f))
                    Spacer(Modifier.height(8.dp))
                    SkeletonBox(Modifier.width(145.dp).height(13.dp), Color.White.copy(.22f))
                } else {
                    Text("${forecast.current.temperatureCelsius.roundToInt()}°", color = Color.White, fontSize = 70.sp)
                    Text(forecast.current.condition.toVietnamese(), color = Color.White, fontSize = 19.sp)
                    Spacer(Modifier.height(5.dp))
                    Text(
                        "Cảm giác như ${forecast.current.apparentTemperatureCelsius.roundToInt()}°",
                        color = Color.White.copy(.72f), fontSize = 12.sp
                    )
                }
            }

            Box(
                modifier = Modifier
                    .size(120.dp)
                    .background(
                        color = Color.White.copy(alpha = 0.16f),
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.WbSunny,
                    contentDescription = null,
                    tint = Color.White.copy(alpha = 0.45f),
                    modifier = Modifier.size(62.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(28.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .background(Color.White.copy(alpha = 0.14f))
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            WeatherMetricPlaceholder(
                icon = Icons.Outlined.WaterDrop,
                label = "Độ ẩm",
                value = forecast?.current?.let { "${it.humidityPercent}%" }
            )

            WeatherMetricPlaceholder(
                icon = Icons.Outlined.WindPower,
                label = "Gió",
                value = forecast?.current?.let { "${it.windSpeedKmh.roundToInt()} km/h" }
            )

            WeatherMetricPlaceholder(
                icon = Icons.Outlined.WaterDrop,
                label = "Khả năng mưa",
                value = forecast?.current?.let { "${it.precipitationProbabilityPercent}%" }
            )
        }
    }
}

@Composable
private fun HeaderIconButton(
    icon: ImageVector,
    contentDescription: String,
    onClick: () -> Unit
) {
    IconButton(
        onClick = onClick,
        modifier = Modifier
            .size(42.dp)
            .background(
                color = Color.White.copy(alpha = 0.14f),
                shape = CircleShape
            )
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            tint = Color.White
        )
    }
}

@Composable
private fun WeatherMetricPlaceholder(
    icon: ImageVector,
    label: String,
    value: String?,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color.White.copy(alpha = 0.75f),
            modifier = Modifier.size(22.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        if (value == null) {
            SkeletonBox(Modifier.width(42.dp).height(14.dp), Color.White.copy(.35f))
        } else {
            Text(value, color = Color.White, fontSize = 13.sp)
        }

        Spacer(modifier = Modifier.height(5.dp))

        Text(
            text = label,
            color = Color.White.copy(alpha = 0.65f),
            fontSize = 10.sp
        )
    }
}

private fun WeatherCondition.toVietnamese(): String = when (this) {
    WeatherCondition.CLEAR -> "Trời quang"
    WeatherCondition.PARTLY_CLOUDY -> "Có mây"
    WeatherCondition.CLOUDY -> "Nhiều mây"
    WeatherCondition.FOG -> "Có sương mù"
    WeatherCondition.DRIZZLE -> "Mưa phùn"
    WeatherCondition.RAIN -> "Có mưa"
    WeatherCondition.HEAVY_RAIN -> "Mưa lớn"
    WeatherCondition.SNOW -> "Có tuyết"
    WeatherCondition.THUNDERSTORM -> "Có giông"
    WeatherCondition.UNKNOWN -> "Không xác định"
}
