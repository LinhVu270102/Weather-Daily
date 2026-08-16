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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherdaily.ui.home.SkeletonBox
import com.example.weatherdaily.ui.theme.SkyBlue

@Composable
fun WeatherDetailsSection(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(horizontal = 16.dp)
    ) {
        Text(
            text = "Chi tiết hôm nay",
            color = androidx.compose.ui.graphics.Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            WeatherDetailPlaceholder(
                modifier = Modifier.weight(1f)
            )

            WeatherDetailPlaceholder(
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            WeatherDetailPlaceholder(
                modifier = Modifier.weight(1f)
            )

            WeatherDetailPlaceholder(
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun WeatherDetailPlaceholder(
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.94f)
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(28.dp)
                    .background(
                        color = SkyBlue.copy(alpha = 0.15f),
                        shape = CircleShape
                    )
            )

            Spacer(modifier = Modifier.height(18.dp))

            SkeletonBox(
                modifier = Modifier
                    .fillMaxWidth(0.65f)
                    .height(11.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            SkeletonBox(
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .height(18.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            SkeletonBox(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(10.dp)
            )
        }
    }
}
