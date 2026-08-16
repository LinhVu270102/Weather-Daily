package com.example.weatherdaily.ui.settings

import androidx.compose.foundation.background
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
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Language
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherdaily.ui.theme.SkyBlue

@Composable
fun MoreScreen(contentPadding: PaddingValues, modifier: Modifier = Modifier) {
    LazyColumn(
        modifier.fillMaxSize().padding(bottom = contentPadding.calculateBottomPadding()),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 20.dp),
    ) {
        item {
            Column(Modifier.statusBarsPadding().padding(bottom = 20.dp)) {
                Text("Khác", color = Color.White, fontSize = 30.sp, fontWeight = FontWeight.Bold)
                Text("Cá nhân hóa trải nghiệm của bạn", color = Color.White.copy(.72f), fontSize = 13.sp)
            }
        }
        item { SettingsGroup("Vị trí và thông báo", listOf(Icons.Outlined.LocationOn to "Địa điểm đã lưu", Icons.Outlined.Notifications to "Thông báo thời tiết")) }
        item { Spacer(Modifier.height(12.dp)) }
        item { SettingsGroup("Tùy chỉnh", listOf(Icons.Outlined.DarkMode to "Giao diện", Icons.Outlined.Language to "Ngôn ngữ", Icons.Outlined.Settings to "Đơn vị đo")) }
        item { Spacer(Modifier.height(12.dp)) }
        item { SettingsGroup("Thông tin", listOf(Icons.Outlined.Info to "Giới thiệu ứng dụng")) }
    }
}

@Composable
private fun SettingsGroup(title: String, items: List<Pair<ImageVector, String>>) {
    Card(
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surface.copy(.94f)),
    ) {
        Column(Modifier.fillMaxWidth().padding(8.dp)) {
            Text(title, color = MaterialTheme.colorScheme.onSurface.copy(.55f), fontSize = 11.sp, fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp))
            items.forEach { (icon, label) ->
                Row(Modifier.fillMaxWidth().padding(10.dp), verticalAlignment = Alignment.CenterVertically) {
                    androidx.compose.foundation.layout.Box(Modifier.size(38.dp).background(SkyBlue.copy(.12f), CircleShape), contentAlignment = Alignment.Center) {
                        Icon(icon, null, tint = SkyBlue, modifier = Modifier.size(20.dp))
                    }
                    Spacer(Modifier.width(12.dp))
                    Text(label, Modifier.weight(1f), fontSize = 15.sp, fontWeight = FontWeight.Medium)
                    Icon(Icons.Outlined.ChevronRight, null, tint = MaterialTheme.colorScheme.onSurface.copy(.35f))
                }
            }
        }
    }
}
@Preview(
    showBackground = true,
    widthDp = 390,
    heightDp = 844
)

@Composable
private fun MoreScreenPreview () {
    MaterialTheme{
        MoreScreen(
            contentPadding = PaddingValues(
                bottom = 80.dp
            )
        )
    }
}