package com.example.weatherdaily.ui.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.LocationCity
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.MyLocation
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherdaily.ui.home.SkeletonBox
import com.example.weatherdaily.ui.theme.*

@Composable
fun SearchScreen(onBackClick: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier.fillMaxSize()
            .background(Brush.verticalGradient(listOf(LightSkyBlue, SkyBlue, DeepBlue)))
            .statusBarsPadding().navigationBarsPadding().padding(horizontal = 16.dp)
    ) {
        Row(
            Modifier.fillMaxWidth().padding(top = 8.dp, bottom = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBackClick, modifier = Modifier.background(Color.White.copy(.14f), CircleShape)) {
                Icon(Icons.AutoMirrored.Outlined.ArrowBack, "Quay lại", tint = Color.White)
            }
            Spacer(Modifier.width(14.dp))
            Column {
                Text("Tìm địa điểm", color = Color.White, fontSize = 27.sp, fontWeight = FontWeight.Bold)
                Text("Xem thời tiết ở mọi nơi", color = Color.White.copy(.7f), fontSize = 12.sp)
            }
        }
        SearchFieldPlaceholder()
        Spacer(Modifier.height(18.dp))
        CurrentLocationCard()
        Spacer(Modifier.height(24.dp))
        SectionLabel(Icons.Outlined.History, "Tìm kiếm gần đây")
        Spacer(Modifier.height(10.dp))
        repeat(3) { SearchResultPlaceholder() }
        Spacer(Modifier.height(12.dp))
        SectionLabel(Icons.Outlined.LocationCity, "Thành phố phổ biến")
        Spacer(Modifier.height(10.dp))
        PopularCitiesPlaceholder()
    }
}

@Composable
private fun SearchFieldPlaceholder() {
    Card(shape = RoundedCornerShape(22.dp), colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surface.copy(.96f))) {
        Row(Modifier.fillMaxWidth().padding(horizontal = 17.dp, vertical = 16.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Outlined.Search, null, tint = SkyBlue)
            Spacer(Modifier.width(12.dp))
            Text("Nhập tên thành phố...", color = MaterialTheme.colorScheme.onSurface.copy(.42f), fontSize = 15.sp)
        }
    }
}

@Composable
private fun CurrentLocationCard() {
    Card(shape = RoundedCornerShape(20.dp), colors = CardDefaults.cardColors(Color.White.copy(.15f))) {
        Row(Modifier.fillMaxWidth().padding(15.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(Modifier.size(42.dp).background(Color.White.copy(.16f), CircleShape), contentAlignment = Alignment.Center) {
                Icon(Icons.Outlined.MyLocation, null, tint = Color.White)
            }
            Spacer(Modifier.width(12.dp))
            Column {
                Text("Sử dụng vị trí hiện tại", color = Color.White, fontWeight = FontWeight.SemiBold)
                Text("Cho phép truy cập vị trí của thiết bị", color = Color.White.copy(.65f), fontSize = 11.sp)
            }
        }
    }
}

@Composable
private fun SectionLabel(icon: ImageVector, title: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(icon, null, tint = Color.White.copy(.8f), modifier = Modifier.size(18.dp))
        Spacer(Modifier.width(8.dp))
        Text(title, color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
private fun SearchResultPlaceholder() {
    Card(
        Modifier.fillMaxWidth().padding(bottom = 8.dp),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surface.copy(.94f))
    ) {
        Row(Modifier.fillMaxWidth().padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(Modifier.size(38.dp).background(SkyBlue.copy(.12f), CircleShape), contentAlignment = Alignment.Center) {
                Icon(Icons.Outlined.LocationOn, null, tint = SkyBlue, modifier = Modifier.size(20.dp))
            }
            Spacer(Modifier.width(12.dp))
            Column(Modifier.weight(1f)) {
                SkeletonBox(Modifier.width(110.dp).height(13.dp))
                Spacer(Modifier.height(7.dp))
                SkeletonBox(Modifier.width(160.dp).height(9.dp))
            }
            SkeletonBox(Modifier.width(38.dp).height(20.dp))
        }
    }
}

@Composable
private fun PopularCitiesPlaceholder() {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        repeat(2) {
            Card(
                Modifier.weight(1f),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surface.copy(.94f))
            ) {
                Column(Modifier.padding(16.dp)) {
                    Icon(Icons.Outlined.LocationCity, null, tint = SkyBlue)
                    Spacer(Modifier.height(14.dp))
                    SkeletonBox(Modifier.width(85.dp).height(14.dp))
                    Spacer(Modifier.height(7.dp))
                    SkeletonBox(Modifier.width(60.dp).height(10.dp))
                }
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 390, heightDp = 844)
@Composable
private fun SearchScreenPreview() {
    WeatherDailyTheme { SearchScreen(onBackClick = {}) }
}
