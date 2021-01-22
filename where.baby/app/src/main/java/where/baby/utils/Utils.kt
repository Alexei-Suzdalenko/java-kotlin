package where.baby.utils

import android.Manifest
import android.R
import android.app.Activity
import android.app.AlarmManager
import android.app.AlertDialog
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat


class Utils(val c: Context) {

    fun startTestService(){
        val i = Intent(c, TestService::class.java)
        ContextCompat.startForegroundService(c, i)
    }






    private val REQUEST = 123
    var locationManager: LocationManager?= c.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    val alarmManager = c.getSystemService(Context.ALARM_SERVICE) as AlarmManager


    fun getPermissionApplication(){
        if(ActivityCompat.checkSelfPermission(c, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(c as Activity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST)
        }
        if( ActivityCompat.checkSelfPermission(c, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(c as Activity, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION), REQUEST)
        }
        if( ActivityCompat.checkSelfPermission(c, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(c as Activity, Manifest.permission.ACCESS_COARSE_LOCATION)) {
                ActivityCompat.requestPermissions(c as Activity, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION), REQUEST)
            }
        }
    }

    fun checkIsLocationWork() : Boolean{
        return locationManager!!.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager!!.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER)
    }

    fun openNotificationManager(q: String,b:String,e:String){
     val d = AlertDialog.Builder(c)
     d.setTitle(q)
     d.setMessage(b)
     d.setPositiveButton(e){ a, b ->
         if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
             val i = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS)
             i.putExtra(Settings.EXTRA_APP_PACKAGE, c.packageName)
             c.startActivity(i)
         } else{
             val i = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
             i.data = Uri.parse("package:$c.packageName")
             c.startActivity(i)
         }
     }
     val no = c.resources.getString(R.string.no)
     d.setNegativeButton(no){a,b-> a.dismiss()}
     d.show()
    }

    fun showUserAlert(s:String,h:String,f:String){
         val d = AlertDialog.Builder(c)
         d.setTitle(s)
         d.setMessage(h)
         d.setPositiveButton(f){ a, b -> c.startActivity( Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)); a.dismiss() }
         val no = c.resources.getString(R.string.no)
         d.setNegativeButton(no){a,b-> a.dismiss()}
         d.show()
     }

     fun startAlarm(){

         val startService = Intent(c, LocationUpdatesService::class.java)
         if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
             ContextCompat.startForegroundService(c, startService)
         }else{
             c.startService(startService)
         }


         Log.d("tag", "start alarm first")
       //  val notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
       //  val r = RingtoneManager.getRingtone(c.applicationContext, notification)
       //  r.play()
         val next = System.currentTimeMillis() + 600000
         val i = Intent(c, AlarmReceiver::class.java)
         val pendingIntent = PendingIntent.getBroadcast(c, 0, i, PendingIntent.FLAG_UPDATE_CURRENT)
         if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
             alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, next, pendingIntent)
         } else{
             alarmManager.setExact(AlarmManager.RTC_WAKEUP, next, pendingIntent)
         }
     }











}