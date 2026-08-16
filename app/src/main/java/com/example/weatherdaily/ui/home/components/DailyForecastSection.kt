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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.weatherdaily.ui.home.SectionTitle
import com.example.weatherdaily.ui.home.SkeletonBox
import com.example.weatherdaily.ui.home.WeatherSectionCard

@Composable
fun DailyForecastSection(
    modifier: Modifier = Modifier
) {
    WeatherSectionCard(modifier = modifier) {
        SectionTitle(title = "Dự báo 7 ngày")

        repeat(5) {
            DailyForecastPlaceholder()
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