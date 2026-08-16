package com.spindeck.player

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat

private data class Track(val title: String, val artist: String)

class MainActivity : ComponentActivity() {
    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= 33 && ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            permissionLauncher.launch(Manifest.permission.READ_MEDIA_AUDIO)
        } else if (Build.VERSION.SDK_INT < 33 && ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
        setContent { SpinDeckApp() }
    }
}

@Composable
private fun SpinDeckApp() {
    val tracks = remember { listOf<Track>() }
    var selected by remember { mutableStateOf<Track?>(null) }
    MaterialTheme(colorScheme = darkColorScheme()) {
        Surface(modifier = Modifier.fillMaxSize(), color = Color(0xFF080808)) {
            Row(Modifier.fillMaxSize()) {
                Column(Modifier.width(180.dp).fillMaxHeight().padding(16.dp)) {
                    Text("HAZ PLAYER", style = MaterialTheme.typography.titleLarge, color = Color.White)
                    Spacer(Modifier.height(24.dp))
                    Text("LIBRARY", color = Color.LightGray)
                    Spacer(Modifier.height(8.dp))
                    Text("NOW PLAYING", color = Color.LightGray)
                }
                Column(Modifier.fillMaxSize().padding(24.dp)) {
                    Text("Library", style = MaterialTheme.typography.headlineMedium, color = Color.White)
                    Spacer(Modifier.height(16.dp))
                    if (tracks.isEmpty()) {
                        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Text("No local music found yet", color = Color.Gray)
                        }
                    } else {
                        LazyColumn { items(tracks) { track ->
                            ListItem(
                                headlineContent = { Text(track.title, color = Color.White) },
                                supportingContent = { Text(track.artist, color = Color.Gray) },
                                modifier = Modifier.background(Color(0xFF111111))
                            )
                        }}
                    }
                }
            }
        }
    }
}
