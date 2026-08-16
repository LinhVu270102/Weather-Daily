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

@Composable
fun CurrentWeatherHeader(
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
                SkeletonBox(
                    modifier = Modifier
                        .width(130.dp)
                        .height(22.dp),
                    color = Color.White.copy(alpha = 0.35f)
                )

                Spacer(modifier = Modifier.height(8.dp))

                SkeletonBox(
                    modifier = Modifier
                        .width(175.dp)
                        .height(13.dp),
                    color = Color.White.copy(alpha = 0.25f)
                )
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
                SkeletonBox(
                    modifier = Modifier
                        .width(120.dp)
                        .height(70.dp),
                    color = Color.White.copy(alpha = 0.35f)
                )

                Spacer(modifier = Modifier.height(12.dp))

                SkeletonBox(
                    modifier = Modifier
                        .width(100.dp)
                        .height(20.dp),
                    color = Color.White.copy(alpha = 0.3f)
                )

                Spacer(modifier = Modifier.height(8.dp))

                SkeletonBox(
                    modifier = Modifier
                        .width(145.dp)
                        .height(13.dp),
                    color = Color.White.copy(alpha = 0.22f)
                )
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
                label = "Độ ẩm"
            )

            WeatherMetricPlaceholder(
                icon = Icons.Outlined.WindPower,
                label = "Gió"
            )

            WeatherMetricPlaceholder(
                icon = Icons.Outlined.WaterDrop,
                label = "Khả năng mưa"
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
    label: String
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

        SkeletonBox(
            modifier = Modifier
                .width(42.dp)
                .height(14.dp),
            color = Color.White.copy(alpha = 0.35f)
        )

        Spacer(modifier = Modifier.height(5.dp))

        Text(
            text = label,
            color = Color.White.copy(alpha = 0.65f),
            fontSize = 10.sp
        )
    }
}
