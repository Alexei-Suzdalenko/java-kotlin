package where.baby.utils
import android.R
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.os.Looper
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.android.gms.location.*
import where.baby.MapsActivity
import where.baby.utils.App.Companion.channel_id
class LocationUpdatesService: Service() {
    lateinit var locationRequest: LocationRequest
    private lateinit var mFusedLocationClient: FusedLocationProviderClient
    lateinit var locationCallback: LocationCallback

    override fun onCreate(){
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
            mFusedLocationClient.lastLocation.addOnSuccessListener{ location ->
                if (location != null) {
                    Log.d("tag", "---- " + location.latitude +  " " + location.longitude)
                    val user = User(location.latitude,  location.longitude)
                    val key = App.sharedPreferences.getString("keyApp", "error").toString()
                    val role = App.sharedPreferences.getString("role", "none").toString()
                    if(role == "son") {
                        DataBase.database.child(key).setValue(user)
                    }
                }
            }

            locationRequest = LocationRequest.create()
            locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            locationRequest.interval = 10000 // 10 seconds
            locationRequest.fastestInterval = App.nextRequest // 60 seconds to next request

            locationCallback = object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult?) {
                    locationResult ?: return
                    for (location in locationResult.locations){
                        Log.d("tag", "new location " + location.latitude)
                        Log.d("tag", "new location " + location.longitude)
                        val user = User(location.latitude,  location.longitude)
                        val key = App.sharedPreferences.getString("keyApp", "error").toString()
                        val role = App.sharedPreferences.getString("role", "none").toString()
                        if(role == "son") {
                            DataBase.database.child(key).setValue(user)
                        }
                    }
                }
            }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        Log.d("tag", "Service started")
        val aplication = getString(R.string.ok)
        val ads = getString(R.string.ok)
        val notIntent = Intent(this, MapsActivity::class.java)
        notIntent.putExtra("stop", "stop")
        val pendIntent = PendingIntent.getActivity(this, 123, notIntent, 0)
        val news = NotificationCompat.Builder(this, channel_id)
            .setContentTitle(aplication)
            .setContentText(ads)
            .setSmallIcon(R.mipmap.sym_def_app_icon)
            .setContentIntent(pendIntent)
            .build()
        startForeground(111, news)
        val role = App.sharedPreferences.getString("role", "none").toString()
        if(role == "son") {
            startLocationUpdates()
        }
        return START_STICKY
    }

    private fun startLocationUpdates() {
        mFusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())

        var i = 0
        val t = Thread{
            try{
                while (true) {
                    Log.d("tag", "i = " + i++ )
                    Thread.sleep(10000)
                }
            } catch (e:Exception){
                Log.d("tag", "e " + e.message)
            }
        }
        t.start()
    }






























}






























































































































































































