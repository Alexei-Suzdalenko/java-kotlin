package com.example.locationmanagerapi_not_google_play_services
import android.Manifest
import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.*
import android.os.Bundle
import android.provider.ContactsContract.Directory.PACKAGE_NAME
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.locationmanagerapi_not_google_play_services.util.App
import com.google.android.material.button.MaterialButton
import java.io.IOException
import java.text.DecimalFormat
import java.util.*
class MainActivity : AppCompatActivity() {
    companion object{}
    private lateinit var reset: MaterialButton
    lateinit var textViewSpeed : TextView
    private lateinit var context: Context
    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkGPS()

        reset = findViewById(R.id.reset)
        reset.setOnClickListener {
            App.velocity = "0"
            textViewSpeed.text = "0"
            App.distance = 0
            setActivityTitle(App.distance.toString())
        }
        context = this
        textViewSpeed = findViewById(R.id.textViewSpeed)
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        title = "Distance 0 m"

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION), 11)
        }

        val locationListener: LocationListener = MyLocationListener(this)

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000,1f, locationListener)

    }

    fun setActivityTitle(x: String){
        title = "Distance $x m"
    }


    private inner class MyLocationListener(val context: Context) : LocationListener {
        override fun onLocationChanged(loc: Location) {
            if(App.oldLocation == null){
                App.oldLocation = loc
            }
            val speed = loc.speed * 18 / 5
            App.velocity = DecimalFormat("#.#").format(speed).toString()
            if(speed.toInt() > 1.5){
                App.distance += loc.distanceTo(App.oldLocation).toInt()
            }
            App.oldLocation = loc

            var cityName: String? = null
            val gcd = Geocoder(context, Locale.getDefault())
            val addresses: List<Address>
            try {
                addresses = gcd.getFromLocation(loc.latitude, loc.longitude, 1)
                if (addresses.isNotEmpty()) {
                    cityName = addresses[0].locality
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
            val s = "$cityName, distance = " + App.distance + ", speed = " + App.velocity

            Toast.makeText(context, s, Toast.LENGTH_LONG).show()

            textViewSpeed.text = App.velocity
            setActivityTitle(App.distance.toString())
        }
        override fun onProviderDisabled(provider: String) {}
        override fun onProviderEnabled(provider: String) {}
        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
    }






    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            11 -> {
                if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) Toast.makeText(this, "GPS OK", Toast.LENGTH_LONG).show()
                else Toast.makeText(this, "Your denied GPS location, the Application don't work !!!", Toast.LENGTH_LONG).show()
                return
            }
        }
    }

    private fun checkGPS() {
        val locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
        if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) showGPSDisabledAlert()
    }
    private fun showGPSDisabledAlert() {
        val builder = android.app.AlertDialog.Builder(this)
        builder.setTitle("Enable GPS location")
        builder.setMessage("Enable gps to use this aplicacion").setCancelable(false).setPositiveButton("enable GPS") { dialog, which ->
            val i = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS); startActivity(i)
        }
        builder.setNegativeButton("cancel"){ dialog, which -> dialog.cancel(); }
        val alert = builder.create(); alert.show()
    }


}