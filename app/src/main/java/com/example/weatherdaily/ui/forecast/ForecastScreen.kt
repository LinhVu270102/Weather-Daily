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

@Composable
fun ForecastScreen(contentPadding: PaddingValues, modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier.fillMaxSize().padding(bottom = contentPadding.calculateBottomPadding()),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        item { ForecastHeader() }
        item { TemperatureOverviewCard() }
        items(7) { ForecastDayCard() }
    }
}

@Composable
private fun ForecastHeader() {
    Row(
        Modifier.fillMaxWidth().statusBarsPadding().padding(top = 4.dp, bottom = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(Modifier.weight(1f)) {
            Text("Dự báo", color = Color.White, fontSize = 30.sp, fontWeight = FontWeight.Bold)
            Text("Tổng quan thời tiết 7 ngày", color = Color.White.copy(.72f), fontSize = 13.sp)
        }
        IconButton(onClick = {}, modifier = Modifier.background(Color.White.copy(.14f), CircleShape)) {
            Icon(Icons.Outlined.Tune, "Bộ lọc", tint = Color.White)
        }
    }
}

@Composable
private fun TemperatureOverviewCard() {
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
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
                    repeat(7) { SkeletonBox(Modifier.width(10.dp).height((35 + it % 3 * 18).dp), SkyBlue.copy(.32f)) }
                }
            }
        }
    }
}

@Composable
private fun ForecastDayCard() {
    Card(
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surface.copy(.94f)),
    ) {
        Row(Modifier.fillMaxWidth().padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Column(Modifier.weight(1f)) {
                SkeletonBox(Modifier.width(90.dp).height(15.dp))
                Spacer(Modifier.height(8.dp))
                SkeletonBox(Modifier.width(125.dp).height(10.dp))
            }
            Box(Modifier.size(45.dp).background(SkyBlue.copy(.12f), CircleShape), contentAlignment = Alignment.Center) {
                Icon(Icons.Outlined.Cloud, null, tint = SkyBlue)
            }
            Spacer(Modifier.width(20.dp))
            SkeletonBox(Modifier.width(58.dp).height(20.dp))
        }
    }
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
