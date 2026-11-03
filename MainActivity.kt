package com.privatedrive.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Bolt
import androidx.compose.material.icons.rounded.Cloud
import androidx.compose.material.icons.rounded.CloudUpload
import androidx.compose.material.icons.rounded.Description
import androidx.compose.material.icons.rounded.Folder
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material.icons.rounded.Photo
import androidx.compose.material.icons.rounded.PhotoLibrary
import androidx.compose.material.icons.rounded.PlayCircle
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.Send
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material.icons.rounded.Sync
import androidx.compose.material.icons.rounded.VerifiedUser
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

// ---------- Colors ----------
private val BgTop = Color(0xFF0B1033)
private val BgBottom = Color(0xFF1C1470)
private val Accent = Color(0xFF7C4DFF)
private val AccentBlue = Color(0xFF3D8BFF)
private val GlassLight = Color(0x33FFFFFF)
private val GlassDark = Color(0x0DFFFFFF)
private val GlassBorder = Color(0x40FFFFFF)
private val SoftText = Color(0xB3FFFFFF)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent { PrivateDriveApp() }
    }
}

@Composable
fun PrivateDriveApp() {
    MaterialTheme(colorScheme = darkColorScheme(primary = Accent)) {
        var showSplash by remember { mutableStateOf(true) }
        Box(
            Modifier
                .fillMaxSize()
                .background(Brush.verticalGradient(listOf(BgTop, BgBottom)))
        ) {
            if (showSplash) SplashScreen { showSplash = false } else HomeScreen()
        }
    }
}

// ---------- Glass card ----------
@Composable
fun Glass(
    modifier: Modifier = Modifier,
    radius: Int = 22,
    content: @Composable BoxScope.() -> Unit
) {
    val shape = RoundedCornerShape(radius.dp)
    Box(
        modifier
            .clip(shape)
            .background(Brush.linearGradient(listOf(GlassLight, GlassDark)))
            .border(1.dp, GlassBorder, shape),
        content = content
    )
}

// ---------- Splash ----------
@Composable
fun SplashScreen(onDone: () -> Unit) {
    val progress = remember { Animatable(0f) }
    LaunchedEffect(Unit) {
        progress.animateTo(1f, tween(2400, easing = LinearEasing))
        delay(300)
        onDone()
    }
    Column(
        Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.weight(1f))
        Box(contentAlignment = Alignment.Center) {
            Box(
                Modifier
                    .size(170.dp)
                    .clip(CircleShape)
                    .background(
                        Brush.radialGradient(listOf(Accent.copy(alpha = 0.55f), Color.Transparent))
                    )
            )
            Icon(
                Icons.Rounded.Cloud, null,
                tint = AccentBlue, modifier = Modifier.size(130.dp)
            )
            Icon(
                Icons.Rounded.Lock, null,
                tint = Color.White, modifier = Modifier.size(52.dp)
            )
        }
        Spacer(Modifier.height(28.dp))
        Text("Private Drive", color = Color.White, fontSize = 38.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(6.dp))
        Text("Your Personal Cloud Storage", color = SoftText, fontSize = 17.sp)
        Spacer(Modifier.height(26.dp))
        Text("Backup \u2022 Store \u2022 Access \u2022 Anywhere", color = SoftText, fontSize = 14.sp)
        Spacer(Modifier.weight(1f))
        Text("Initializing your secure drive...", color = Color.White, fontSize = 14.sp)
        Spacer(Modifier.height(10.dp))
        LinearProgressIndicator(
            progress = { progress.value },
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(CircleShape),
            color = Accent,
            trackColor = GlassLight
        )
        Spacer(Modifier.height(6.dp))
        Text("${(progress.value * 100).toInt()}%", color = Color.White, fontSize = 13.sp)
        Spacer(Modifier.height(36.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                Icons.Rounded.VerifiedUser, null,
                tint = Color(0xFF22C55E), modifier = Modifier.size(40.dp)
            )
            Spacer(Modifier.width(12.dp))
            Column {
                Text("100% Secure", color = Color.White, fontWeight = FontWeight.SemiBold)
                Text("Your Data, Our Priority", color = SoftText, fontSize = 13.sp)
            }
        }
        Spacer(Modifier.height(24.dp))
    }
}

// ---------- Home ----------
private data class Category(val name: String, val size: String, val icon: ImageVector, val tint: Color)

private val categories = listOf(
    Category("Photos", "12.5 GB", Icons.Rounded.Photo, Color(0xFF42A5F5)),
    Category("Videos", "8.7 GB", Icons.Rounded.PlayCircle, Color(0xFF9C5CFF)),
    Category("Documents", "5.2 GB", Icons.Rounded.Description, Color(0xFF4F8DFF)),
    Category("Others", "10.6 GB", Icons.Rounded.Folder, Color(0xFFFFB300)),
    Category("Backups", "6.0 GB", Icons.Rounded.Sync, Color(0xFF5C7CFF)),
    Category("Favorites", "1.3 GB", Icons.Rounded.Star, Color(0xFFFFC107)),
)

@Composable
fun HomeScreen() {
    var tab by remember { mutableIntStateOf(0) }
    var autoBackup by remember { mutableStateOf(true) }

    Column(Modifier.fillMaxSize()) {
        Column(
            Modifier
                .weight(1f)
                .statusBarsPadding()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            TopBar()
            ConnectionCard()
            StorageCard()
            categories.chunked(3).forEach { row ->
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    row.forEach { CategoryCard(it, Modifier.weight(1f)) }
                }
            }
            AutoBackupRow(autoBackup) { autoBackup = it }
            StartBackupButton()
        }
        BottomBar(tab) { tab = it }
    }
}

@Composable
fun TopBar() {
    Row(
        Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Icon(Icons.Rounded.Menu, null, tint = Color.White, modifier = Modifier.size(30.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Rounded.Cloud, null, tint = AccentBlue, modifier = Modifier.size(38.dp))
            Spacer(Modifier.width(8.dp))
            Text("Private Drive", color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.SemiBold)
        }
        Icon(Icons.Rounded.Notifications, null, tint = Color.White, modifier = Modifier.size(28.dp))
    }
}

@Composable
fun ConnectionCard() {
    Glass(Modifier.fillMaxWidth()) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(18.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(Modifier.size(12.dp).clip(CircleShape).background(Color(0xFF22C55E)))
                    Spacer(Modifier.width(8.dp))
                    Text("Connected", color = Color(0xFF4ADE80), fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
                }
                Text("via Telegram", color = SoftText, fontSize = 14.sp, modifier = Modifier.padding(start = 20.dp))
            }
            Box(
                Modifier
                    .size(56.dp)
                    .clip(CircleShape)
                    .background(Brush.linearGradient(listOf(Color(0xFF29B6F6), Color(0xFF1E88E5)))),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Rounded.Send, null, tint = Color.White, modifier = Modifier.size(28.dp))
            }
        }
    }
}

@Composable
fun StorageCard() {
    Glass(Modifier.fillMaxWidth()) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            StorageRing(0.74f)
            Spacer(Modifier.width(20.dp))
            Column {
                Text("Total Stored", color = SoftText, fontSize = 15.sp)
                Text("37.0 GB", color = Color.White, fontSize = 26.sp, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(10.dp))
                Box(
                    Modifier
                        .clip(RoundedCornerShape(50))
                        .background(Accent)
                        .clickable { }
                        .padding(horizontal = 22.dp, vertical = 10.dp)
                ) {
                    Text("Backup Folders", color = Color.White, fontWeight = FontWeight.SemiBold)
                }
            }
        }
    }
}

@Composable
fun StorageRing(percent: Float) {
    Box(Modifier.size(104.dp), contentAlignment = Alignment.Center) {
        Canvas(Modifier.fillMaxSize()) {
            val stroke = Stroke(width = 18f, cap = StrokeCap.Round)
            val inset = 9f
            val arcSize = Size(size.width - inset * 2, size.height - inset * 2)
            drawArc(
                color = GlassLight, startAngle = 0f, sweepAngle = 360f, useCenter = false,
                topLeft = Offset(inset, inset), size = arcSize, style = stroke
            )
            drawArc(
                brush = Brush.sweepGradient(
                    listOf(Color(0xFF00C9A7), Color(0xFFFFB300), Color(0xFF7C4DFF), Color(0xFF00C9A7))
                ),
                startAngle = -90f, sweepAngle = 360f * percent, useCenter = false,
                topLeft = Offset(inset, inset), size = arcSize, style = stroke
            )
        }
        Text("${(percent * 100).toInt()}%", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun CategoryCard(c: Category, modifier: Modifier = Modifier) {
    Glass(modifier.clickable { }, radius = 20) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(c.icon, null, tint = c.tint, modifier = Modifier.size(42.dp))
            Spacer(Modifier.height(8.dp))
            Text(c.name, color = Color.White, fontSize = 15.sp, fontWeight = FontWeight.SemiBold, textAlign = TextAlign.Center)
            Text(c.size, color = SoftText, fontSize = 13.sp)
        }
    }
}

@Composable
fun AutoBackupRow(checked: Boolean, onChange: (Boolean) -> Unit) {
    Glass(Modifier.fillMaxWidth(), radius = 18) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Rounded.Bolt, null, tint = Color(0xFFFFB300), modifier = Modifier.size(34.dp))
            Spacer(Modifier.width(12.dp))
            Column(Modifier.weight(1f)) {
                Text("Automatic Backup", color = Color.White, fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
                Text("Keep your files always safe", color = SoftText, fontSize = 13.sp)
            }
            Switch(
                checked = checked,
                onCheckedChange = onChange,
                colors = SwitchDefaults.colors(checkedTrackColor = Accent)
            )
        }
    }
}

@Composable
fun StartBackupButton() {
    Box(
        Modifier
            .fillMaxWidth()
            .height(56.dp)
            .clip(RoundedCornerShape(50))
            .background(Brush.horizontalGradient(listOf(Accent, AccentBlue)))
            .clickable { },
        contentAlignment = Alignment.Center
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Rounded.CloudUpload, null, tint = Color.White, modifier = Modifier.size(28.dp))
            Spacer(Modifier.width(10.dp))
            Text("Start Backup", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
        }
    }
}

@Composable
fun BottomBar(selected: Int, onSelect: (Int) -> Unit) {
    val items = listOf(
        "Home" to Icons.Rounded.Home,
        "Files" to Icons.Rounded.Folder,
        "Gallery" to Icons.Rounded.PhotoLibrary,
        "Search" to Icons.Rounded.Search,
        "Settings" to Icons.Rounded.Settings,
    )
    Glass(Modifier.fillMaxWidth(), radius = 0) {
        Row(
            Modifier
                .navigationBarsPadding()
                .padding(horizontal = 8.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            items.forEachIndexed { i, (label, icon) ->
                val on = i == selected
                Column(
                    Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(16.dp))
                        .background(if (on) Accent.copy(alpha = 0.35f) else Color.Transparent)
                        .clickable { onSelect(i) }
                        .padding(vertical = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(icon, null, tint = if (on) Color(0xFFB69CFF) else SoftText, modifier = Modifier.size(26.dp))
                    Text(label, color = if (on) Color(0xFFB69CFF) else SoftText, fontSize = 12.sp)
                }
            }
        }
    }
}
