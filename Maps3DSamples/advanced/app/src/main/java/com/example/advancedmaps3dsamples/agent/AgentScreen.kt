package com.example.advancedmaps3dsamples.agent

import android.os.Bundle
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.advancedmaps3dsamples.ui.theme.AdvancedMaps3DSamplesTheme
import com.google.android.gms.maps3d.GoogleMap3D
import com.google.android.gms.maps3d.Map3DOptions
import com.google.android.gms.maps3d.Map3DView
import com.google.android.gms.maps3d.OnMap3DViewReadyCallback
import com.google.android.gms.maps3d.model.Map3DMode
import com.google.android.gms.maps3d.model.camera
import com.google.android.gms.maps3d.model.latLngAltitude

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgentScreen() {
    var text by remember { mutableStateOf("") }
    var map by remember { mutableStateOf<GoogleMap3D?>(null) }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Alora Agent") }) },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            AgentMapView(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                onMapReady = { googleMap -> map = googleMap }
            )

            Text(
                text = "Conversation will appear here.",
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.5f)
                    .padding(16.dp),
                style = MaterialTheme.typography.bodyLarge
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = text,
                    onValueChange = { text = it },
                    modifier = Modifier.weight(1f),
                    placeholder = { Text("Ask Alora...") }
                )
                IconButton(onClick = { /* TODO: Handle text submission */ }) {
                    Icon(
                        imageVector = Icons.Default.Send,
                        contentDescription = "Send Message"
                    )
                }
            }
        }
    }
}

@Composable
private fun AgentMapView(
    modifier: Modifier = Modifier,
    onMapReady: (GoogleMap3D) -> Unit
) {
    AndroidView(
        modifier = modifier,
        factory = { context ->
            Map3DView(context, Map3DOptions()).apply {
                onCreate(null)
                getMap3DViewAsync(object : OnMap3DViewReadyCallback {
                    override fun onMap3DViewReady(googleMap: GoogleMap3D) {
                        googleMap.setMapMode(Map3DMode.HYBRID)

                        // Create a Camera object using the correct DSL builder from the project
                        val initialCamera = camera {
                            center = latLngAltitude {
                                latitude = 40.7128
                                longitude = -74.0060
                                altitude = 0.0
                            }
                            range = 15000.0 // Range is in meters
                        }

                        // Set the camera using the correct 3D API method
                        googleMap.setCamera(initialCamera)

                        onMapReady(googleMap)
                    }

                    override fun onError(error: Exception) {
                        Log.e("AgentScreen", "Error initializing map", error)
                    }
                })
            }
        },
        onRelease = { view -> view.onDestroy() }
    )
}

@Preview
@Composable
fun AgentScreenPreview() {
    AdvancedMaps3DSamplesTheme {
        AgentScreen()
    }
}
