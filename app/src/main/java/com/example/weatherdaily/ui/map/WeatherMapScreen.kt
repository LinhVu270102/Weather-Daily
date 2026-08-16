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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherdaily.ui.home.SkeletonBox
import com.example.weatherdaily.ui.theme.SkyBlue
import com.example.weatherdaily.domain.model.WeatherForecast
import com.example.weatherdaily.domain.model.WeatherMapPoint
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import org.json.JSONArray
import org.json.JSONObject
import org.maplibre.android.MapLibre
import org.maplibre.android.camera.CameraPosition
import org.maplibre.android.geometry.LatLng
import org.maplibre.android.maps.MapView
import org.maplibre.android.maps.Style
import org.maplibre.android.module.http.HttpRequestUtil
import okhttp3.OkHttpClient
import kotlin.math.roundToInt

@Composable
fun WeatherMapScreen(
    forecast: WeatherForecast? = null,
    gridPoints: List<WeatherMapPoint> = emptyList(),
    isLoading: Boolean = false,
    errorMessage: String? = null,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier.fillMaxSize().statusBarsPadding().padding(bottom = contentPadding.calculateBottomPadding()).padding(16.dp)
    ) {
        Text("Bản đồ thời tiết", color = Color.White, fontSize = 30.sp, fontWeight = FontWeight.Bold)
        Text(
            forecast?.location?.let { "${it.name} · ${it.latitude.formatCoordinate()}, ${it.longitude.formatCoordinate()}" }
                ?: "Theo dõi thời tiết quanh khu vực",
            color = Color.White.copy(.72f), fontSize = 13.sp
        )
        if (errorMessage != null) {
            Text(
                errorMessage,
                color = Color(0xFFFFE0E0),
                fontSize = 12.sp,
                modifier = Modifier.padding(top = 6.dp),
            )
        } else if (isLoading) {
            Text(
                "Đang tải dữ liệu thời tiết trên bản đồ...",
                color = Color.White.copy(.72f),
                fontSize = 12.sp,
                modifier = Modifier.padding(top = 6.dp),
            )
        }
        Spacer(Modifier.height(20.dp))
        Card(
            Modifier.fillMaxSize(),
            shape = RoundedCornerShape(28.dp),
            colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surface.copy(.94f)),
        ) {
            Box(Modifier.fillMaxSize().background(MaterialTheme.colorScheme.surfaceVariant.copy(.65f))) {
                OpenWeatherMap(
                    latitude = forecast?.location?.latitude ?: 16.0544,
                    longitude = forecast?.location?.longitude ?: 108.2022,
                    points = gridPoints,
                )
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
                            if (forecast == null) {
                                SkeletonBox(Modifier.width(115.dp).height(14.dp))
                                Spacer(Modifier.height(7.dp))
                                SkeletonBox(Modifier.width(170.dp).height(10.dp))
                            } else {
                                Text(forecast.location.name, fontWeight = FontWeight.SemiBold)
                                Spacer(Modifier.height(4.dp))
                                Text(
                                    listOfNotNull(
                                        forecast.location.administrativeArea,
                                        forecast.location.country,
                                    ).distinct().joinToString(", "),
                                    fontSize = 11.sp,
                                    color = MaterialTheme.colorScheme.onSurface.copy(.55f),
                                )
                            }
                        }
                        if (forecast == null) SkeletonBox(Modifier.width(44.dp).height(25.dp))
                        else Column(horizontalAlignment = Alignment.End) {
                            Text(
                                "${forecast.current.temperatureCelsius.roundToInt()}°",
                                fontSize = 22.sp, fontWeight = FontWeight.Bold,
                            )
                            Text(
                                "Gió ${forecast.current.windSpeedKmh.roundToInt()} km/h",
                                fontSize = 10.sp,
                                color = MaterialTheme.colorScheme.onSurface.copy(.5f),
                            )
                        }
                    }
                }
            }
        }
    }
}

private fun Double.formatCoordinate(): String = String.format(java.util.Locale.US, "%.2f", this)

@Composable
private fun OpenWeatherMap(
    latitude: Double,
    longitude: Double,
    points: List<WeatherMapPoint>,
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val mapView = remember {
        MapLibre.getInstance(context)
        HttpRequestUtil.setOkHttpClient(
            OkHttpClient.Builder()
                .addNetworkInterceptor { chain ->
                    val request = chain.request().newBuilder()
                        .header(
                            "User-Agent",
                            "WeatherDaily/1.0 (Android; com.example.weatherdaily)",
                        )
                        .header("X-Requested-With", context.packageName)
                        .build()
                    chain.proceed(request)
                }
                .build(),
        )
        MapView(context).apply { onCreate(null) }
    }

    DisposableEffect(mapView, lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_START -> mapView.onStart()
                Lifecycle.Event.ON_RESUME -> mapView.onResume()
                Lifecycle.Event.ON_PAUSE -> mapView.onPause()
                Lifecycle.Event.ON_STOP -> mapView.onStop()
                else -> Unit
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
            mapView.onPause()
            mapView.onStop()
            mapView.onDestroy()
        }
    }

    LaunchedEffect(mapView, points) {
        mapView.getMapAsync { map ->
            map.setStyle(Style.Builder().fromJson(createMapStyle(points)))
        }
    }
    LaunchedEffect(mapView, latitude, longitude) {
        mapView.getMapAsync { map ->
            map.cameraPosition = CameraPosition.Builder()
                .target(LatLng(latitude, longitude))
                .zoom(8.5)
                .build()
        }
    }

    Box(Modifier.fillMaxSize()) {
        AndroidView(factory = { mapView }, modifier = Modifier.fillMaxSize())
        Text(
            "© OpenStreetMap contributors",
            modifier = Modifier.align(Alignment.BottomStart).padding(8.dp)
                .background(Color.White.copy(alpha = .82f), RoundedCornerShape(4.dp)).padding(4.dp),
            color = Color.Black.copy(alpha = .72f),
            fontSize = 9.sp,
        )
    }
}

private fun createMapStyle(points: List<WeatherMapPoint>): String {
    val features = JSONArray()
    val halfCell = .09
    points.forEach { point ->
        val coordinates = JSONArray().put(JSONArray().apply {
            put(JSONArray().put(point.longitude - halfCell).put(point.latitude - halfCell))
            put(JSONArray().put(point.longitude + halfCell).put(point.latitude - halfCell))
            put(JSONArray().put(point.longitude + halfCell).put(point.latitude + halfCell))
            put(JSONArray().put(point.longitude - halfCell).put(point.latitude + halfCell))
            put(JSONArray().put(point.longitude - halfCell).put(point.latitude - halfCell))
        })
        features.put(JSONObject().apply {
            put("type", "Feature")
            put("properties", JSONObject().apply {
                put("temperature", point.temperatureCelsius.roundToInt())
                put("color", temperatureColorHex(point.temperatureCelsius))
            })
            put("geometry", JSONObject().put("type", "Polygon").put("coordinates", coordinates))
        })
    }

    val geoJson = JSONObject().put("type", "FeatureCollection").put("features", features)
    return JSONObject().apply {
        put("version", 8)
        put("sources", JSONObject().apply {
            put("openstreetmap", JSONObject().apply {
                put("type", "raster")
                put("tiles", JSONArray().put("https://tile.openstreetmap.org/{z}/{x}/{y}.png"))
                put("tileSize", 256)
                put("maxzoom", 19)
                put("attribution", "© OpenStreetMap contributors")
            })
            put("weather-grid", JSONObject().put("type", "geojson").put("data", geoJson))
        })
        put("layers", JSONArray().apply {
            put(JSONObject().put("id", "osm").put("type", "raster").put("source", "openstreetmap"))
            put(JSONObject().apply {
                put("id", "weather-temperature")
                put("type", "fill")
                put("source", "weather-grid")
                put("paint", JSONObject().apply {
                    put("fill-color", JSONArray().put("get").put("color"))
                    put("fill-opacity", .48)
                    put("fill-outline-color", "rgba(255,255,255,0.35)")
                })
            })
        })
    }.toString()
}

private fun temperatureColor(temperature: Double): Color = when {
    temperature < 15 -> Color(0xFF4A90E2)
    temperature < 23 -> Color(0xFF22A699)
    temperature < 30 -> Color(0xFFFFC857)
    temperature < 35 -> Color(0xFFFF8A4C)
    else -> Color(0xFFE74C3C)
}

private fun temperatureColorHex(temperature: Double): String = when {
    temperature < 15 -> "#4A90E2"
    temperature < 23 -> "#22A699"
    temperature < 30 -> "#FFC857"
    temperature < 35 -> "#FF8A4C"
    else -> "#E74C3C"
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
