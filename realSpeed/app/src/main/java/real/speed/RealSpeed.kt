package real.speed
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CompoundButton
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import real.speed.util.Loc

class RealSpeed : AppCompatActivity() , LocationListener{
    // 483
    var count = 0
    private var locationManager: LocationManager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ){
            requestPermissions(arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 321)
        } else {
            doStuff()
        }

        updateSpeed(null)

        switch_one.setOnCheckedChangeListener{ compoundButton: CompoundButton, b: Boolean ->
            updateSpeed(null)
        }
    }

    override fun onLocationChanged(location: Location?) {
        if(location != null){
            val a = location.latitude
            val b = location.longitude
            val c = location.altitude
            var d = location.accuracy
            Toast.makeText(this, "Location Altitude $c", Toast.LENGTH_LONG).show()
            val customLocation = Loc(location, switch_one.isChecked)
            updateSpeed(customLocation)
        }
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
        TODO("Not yet implemented")
    }

    override fun onProviderEnabled(provider: String?) {
        TODO("Not yet implemented")
    }

    override fun onProviderDisabled(provider: String?) {
        TODO("Not yet implemented")
    }

    @SuppressLint("MissingPermission")
    private fun doStuff(){
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        locationManager?.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0f, this)
        Toast.makeText(this, "Waiting for GPS connection !!!", Toast.LENGTH_LONG).show()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == 321) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) doStuff()
            else finish()
        }
    }

    private fun updateSpeed(location: Loc?){
      // var currentSpeed : Float = 0F
        var currentSpeed = ""
        var akt = ""
       if(location != null) {
           location.setUserMetricUnits(false)
           currentSpeed = location.speed.toString()

           akt = location.getAltitude().toString()
       }
      //  val fmt = Formatter(StringBuilder())
      //      fmt.format(Locale.US, "%5.1f", currentSpeed)
      //  var strCurrentString= fmt.toString()
      //  strCurrentString = strCurrentString.replace(" ", "0")

     //   if(useMetricsUnits()) {
     //       val text = "$strCurrentString km/h"
     //       textView.text = text
     //   } else {
     //       val text = "$strCurrentString miles/h"
     //       textView.text = text
     //   }
        count ++



        if(useMetricsUnits()) {
            val text = "$currentSpeed km/h enabled " + akt
            textView.text = text
        } else {
            val text = "$currentSpeed miles/h disabled " + akt
            textView.text = text
        }
    }

   private fun useMetricsUnits() : Boolean{
     //  return switch_one.isChecked
     return true
   }

}

