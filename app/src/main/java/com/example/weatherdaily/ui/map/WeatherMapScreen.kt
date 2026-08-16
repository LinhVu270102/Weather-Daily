package com.example.weatherdaily.ui.map

import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Layers
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.MyLocation
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
import com.example.weatherdaily.ui.theme.SkyBlue

@Composable
fun WeatherMapScreen(contentPadding: PaddingValues, modifier: Modifier = Modifier) {
    Column(
        modifier.fillMaxSize().statusBarsPadding().padding(bottom = contentPadding.calculateBottomPadding()).padding(16.dp)
    ) {
        Text("Bản đồ thời tiết", color = Color.White, fontSize = 30.sp, fontWeight = FontWeight.Bold)
        Text("Theo dõi thời tiết quanh khu vực", color = Color.White.copy(.72f), fontSize = 13.sp)
        Spacer(Modifier.height(20.dp))
        Card(
            Modifier.fillMaxSize(),
            shape = RoundedCornerShape(28.dp),
            colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surface.copy(.94f)),
        ) {
            Box(Modifier.fillMaxSize().background(MaterialTheme.colorScheme.surfaceVariant.copy(.65f))) {
                MapGrid()
                Column(Modifier.align(Alignment.TopEnd).padding(14.dp)) {
                    MapButton(Icons.Outlined.Layers, "Lớp bản đồ")
                    Spacer(Modifier.height(10.dp))
                    MapButton(Icons.Outlined.MyLocation, "Vị trí của tôi")
                }
                Card(
                    Modifier.align(Alignment.BottomCenter).fillMaxWidth().padding(14.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surface),
                ) {
                    Row(Modifier.padding(15.dp), verticalAlignment = Alignment.CenterVertically) {
                        Box(Modifier.size(42.dp).background(SkyBlue.copy(.12f), CircleShape), contentAlignment = Alignment.Center) {
                            Icon(Icons.Outlined.LocationOn, null, tint = SkyBlue)
                        }
                        Spacer(Modifier.width(12.dp))
                        Column(Modifier.weight(1f)) {
                            SkeletonBox(Modifier.width(115.dp).height(14.dp))
                            Spacer(Modifier.height(7.dp))
                            SkeletonBox(Modifier.width(170.dp).height(10.dp))
                        }
                        SkeletonBox(Modifier.width(44.dp).height(25.dp))
                    }
                }
            }
        }
    }
}

@Composable
private fun MapGrid() {
    Box(Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxSize()) { repeat(9) { Spacer(Modifier.fillMaxWidth().height(44.dp).background(if (it % 2 == 0) SkyBlue.copy(.035f) else Color.Transparent)) } }
        repeat(4) { index ->
            Box(Modifier.padding(start = (50 + index * 65).dp, top = (100 + index * 85).dp).size(18.dp).background(SkyBlue.copy(.6f), CircleShape))
        }
    }
}

@Composable
private fun MapButton(icon: androidx.compose.ui.graphics.vector.ImageVector, description: String) {
    IconButton(onClick = {}, modifier = Modifier.background(MaterialTheme.colorScheme.surface, CircleShape)) {
        Icon(icon, description, tint = SkyBlue)
    }
}
@Preview(
    showBackground = true,
    widthDp = 390,
    heightDp = 844
)

@Composable
private fun WeatherMapScreenPreview () {
    MaterialTheme{
        WeatherMapScreen(
            contentPadding = PaddingValues(
            bottom = 80.dp
        )
        )
    }
}



