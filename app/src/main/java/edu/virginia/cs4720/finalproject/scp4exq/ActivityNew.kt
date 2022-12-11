package edu.virginia.cs4720.finalproject.scp4exq

import android.Manifest
import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.OnTokenCanceledListener
import kotlinx.android.synthetic.main.activity_new.*
import java.util.*

class ActivityNew : AppCompatActivity() {

    lateinit var chooseDate: Button
    lateinit var chosenDate: TextView
    lateinit var notesField: EditText
    lateinit var latField: TextView
    lateinit var longField: TextView
    lateinit var locationButton: Button

    val PERMISSION_ID = 42
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        chooseDate = findViewById(R.id.button2)
        chosenDate = findViewById(R.id.chooseDate)
        notesField = findViewById(R.id.new_notes)
        latField = findViewById(R.id.latitude_new)
        longField = findViewById(R.id.longitude_new)
        locationButton = findViewById(R.id.location_button)

        locationButton.setOnClickListener {
            getLastLocation()
        }

        chooseDate.setOnClickListener {
            val cal = Calendar.getInstance()
            val y = cal.get(Calendar.YEAR)
            val m = cal.get(Calendar.MONTH)
            val d = cal.get(Calendar.DAY_OF_MONTH)

            val dpg = DatePickerDialog(this,
                {
                        _, year, monthOfYear, dayOfMonth -> chosenDate.text =
                    (year.toString() + "-" + formatLeadingZero(monthOfYear + 1) + "-" + formatLeadingZero(dayOfMonth))
                }, y, m, d)
            dpg.show()
        }

        val context = this

        submitButton.setOnClickListener {
            if (itemTitle.text.toString().isNotEmpty() && chosenDate.text.toString().isNotEmpty()
                && notesField.text.toString().length < 256 && latField.text.isNotEmpty() && longField.text.isNotEmpty()) {
                var item = Waypoint(itemTitle.text.toString(), chosenDate.text.toString(),
                    notesField.text.toString(), latField.text.toString(), longField.text.toString())
                var db = DatabaseHandler(context)
                db.insertData(item)

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(context, "Input validation error.", Toast.LENGTH_SHORT).show()
            }
        }
        supportActionBar?.title = "Waypoint Journal"
    }

    private fun formatLeadingZero(input : Int) : String {
        return if (input < 10) {
            "0$input";
        } else {
            input.toString();
        }
    }

    @SuppressLint("MissingPermission")
    private fun getLastLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                fusedLocationClient.getCurrentLocation(LocationRequest.PRIORITY_HIGH_ACCURACY, object : CancellationToken() {
                    override fun onCanceledRequested(p0: OnTokenCanceledListener) = CancellationTokenSource().token
                    override fun isCancellationRequested() = false
                    })
                    .addOnCompleteListener(this) { task ->
                    var location: Location? = task.result
                    if (location == null) {
                        requestNewLocationData()
                    } else {
                        findViewById<TextView>(R.id.latitude_new).text = location.latitude.toString()
                        findViewById<TextView>(R.id.longitude_new).text = location.longitude.toString()
                    }
                }
            } else {
                Toast.makeText(this, "Turn on location", Toast.LENGTH_LONG).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        } else {
            requestPermissions()
        }
    }

    @SuppressLint("MissingPermission")
    private fun requestNewLocationData() {
        var mLocationRequest = LocationRequest()
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        mLocationRequest.interval = 10
        mLocationRequest.fastestInterval = 5
        mLocationRequest.numUpdates = 1

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        fusedLocationClient!!.requestLocationUpdates(
            mLocationRequest, mLocationCallback,
            Looper.myLooper()
        )
    }

    private val mLocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            var mLastLocation: Location? = locationResult.lastLocation
            findViewById<TextView>(R.id.latitude_new).text = mLastLocation?.latitude.toString()
            findViewById<TextView>(R.id.longitude_new).text = mLastLocation?.longitude.toString()
        }
    }

    private fun isLocationEnabled(): Boolean {
        var locationManager: LocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    private fun checkPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION),
            PERMISSION_ID
        )
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == PERMISSION_ID) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                getLastLocation()
            }
        }
    }
}

/***************************************************************************************
 *  REFERENCES
 *  Title: How to open a new activity with a button click - Android Kotlin Example
 *  Author: Simple Schwarz
 *  Date: October 6, 2021
 *  URL: https://medium.com/@simple.schwarz/how-to-open-a-new-activity-with-a-button-click-android-kotlin-example-bd2107946bbe
 *
 *  Title: DatePicker in Android
 *  Author: chaitanyamunje
 *  Date: 17 Jul, 2022
 *  URL: https://www.geeksforgeeks.org/datepicker-in-android/
 *
 *  Title: Android Tutorial (Kotlin) - 30 - SQLite Database Creation and Insertion
 *  Author: CodeAndroid
 *  Date: Dec 11, 2017
 *  URL: https://www.youtube.com/watch?v=OxHNcCXnxnE
 *
 *  Title: Getting Current Location in Android using Kotlin
 *  Author: Jayant
 *  Date: April 2, 2021
 *  URL: https://www.androidhire.com/current-location-in-android-using-kotlin/
 *
 *  Title: How to get location using "fusedLocationClient.getCurrentLocation" method in Kotlin?
 *  Author: Ahmed Maad
 *  Date: May 8, 2022
 *  URL: https://stackoverflow.com/questions/72159435/how-to-get-location-using-fusedlocationclient-getcurrentlocation-method-in-kot
 ***************************************************************************************/