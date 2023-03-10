package edu.virginia.cs4720.finalproject.scp4exq

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class ActivityMap : AppCompatActivity(), OnMapReadyCallback {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment
        mapFragment?.getMapAsync(this)

        supportActionBar?.title = "Waypoint Journal"
    }

    override fun onMapReady(googleMap: GoogleMap) {
        val centerUS = LatLng(44.967243,  -103.771556)

        val db = DatabaseHandler(applicationContext)
        val data = db.readData()

        for (w in data) {
            googleMap.addMarker(
                MarkerOptions()
                    .position(LatLng(w.lat.toDouble(), w.long.toDouble()))
                    .title(w.title)
            )
        }

        googleMap.moveCamera(CameraUpdateFactory.newLatLng(centerUS))
    }
}

/***************************************************************************************
 *  REFERENCES
 *  Title: Adding a Map with a Marker
 *  Author: Google Developers
 *  Date: Dec 9, 2022
 *  URL: https://developers.google.com/maps/documentation/android-sdk/map-with-marker#kotlin
 ***************************************************************************************/

// Based on a file with the following license:
// Copyright 2020 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

