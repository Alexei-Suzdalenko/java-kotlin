package where.baby.utils
import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.google.android.gms.location.*

class App: Application() {

    companion object{
        const val testId = "test_id"
        const val channel_id = "cadena-dial"
        const val name = "Cadena Dial Radio"
        const val nameTest = "simple_test"
        lateinit var sharedPreferences: SharedPreferences
        lateinit var editor: SharedPreferences.Editor
        const val nextRequest: Long = 30000   // 60 * 1000

/////////////////////////////////////////////////////////////////////////////////        ///////////////////////////////////////////////////////////////////////////////////////////////////
        lateinit var locationRequestTest: LocationRequest
        lateinit var mFusedLocationClientTest: FusedLocationProviderClient
        lateinit var locationCallbackTest: LocationCallback
        lateinit var alarmManager : AlarmManager

        fun startAlarmAndService(c : Context){
            val intent        = Intent(c, TestReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(c, 17, intent, PendingIntent.FLAG_UPDATE_CURRENT)
            val next = System.currentTimeMillis() + 30000
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, next, pendingIntent)
            } else { alarmManager.setExact(AlarmManager.RTC_WAKEUP, next, pendingIntent) }

            Utils(c).startTestService()
        }
/////////////////////////////////////////////////////////////////////////////////        //////////////////////////////////////////////////////////////////////////////////////////////////
    }




    @SuppressLint("CommitPrefEdits")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(){
        super.onCreate()
        val serChannel = NotificationChannel(channel_id, name, NotificationManager.IMPORTANCE_HIGH)
            val manager = getSystemService(NotificationManager::class.java)
                manager?.createNotificationChannel(serChannel)

////////////////////////////////////        /////////////////////////////////////////////////////////////////////////////////////////////////////////////
        Utils(this).startTestService()

        val serChannelTest = NotificationChannel(testId, nameTest, NotificationManager.IMPORTANCE_HIGH)
        val managerTest = getSystemService(NotificationManager::class.java)
        managerTest?.createNotificationChannel(serChannelTest)

        alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent        = Intent(applicationContext, TestReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(applicationContext, 17, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        val next = System.currentTimeMillis() + 3000
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, next, pendingIntent)
        } else { alarmManager.setExact(AlarmManager.RTC_WAKEUP, next, pendingIntent) }

        mFusedLocationClientTest = LocationServices.getFusedLocationProviderClient(this)
        mFusedLocationClientTest.lastLocation.addOnSuccessListener{ location ->
            if (location != null) {
                Log.d("tag", "---- " + location.latitude +  " " + location.longitude)
                val user = User(location.latitude,  location.longitude)
                val key  = sharedPreferences.getString("keyApp", "error").toString() + "_test"
                val role = sharedPreferences.getString("role", "none").toString()
                if(role == "son") {
                    DataBase.database.child(key).setValue(user)
                }
            }
        }
        locationRequestTest = LocationRequest.create()
        locationRequestTest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequestTest.interval = 10000 // 10 seconds
        locationRequestTest.fastestInterval = nextRequest // 60 seconds to next request
        locationCallbackTest = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult ?: return
                for (location in locationResult.locations){
                    Log.d("tag", "new location " + location.latitude)
                    Log.d("tag", "new location " + location.longitude)
                    val user = User(location.latitude,  location.longitude)
                    val key = sharedPreferences.getString("keyApp", "error").toString() + "_test"
                    val role = sharedPreferences.getString("role", "none").toString()
                    if(role == "son") {
                        DataBase.database.child(key).setValue(user)
                    }
                }
            }
        }
//////////////////////////////////////////////////////////////////////////////////////////////////////////


        sharedPreferences = getSharedPreferences("tag", Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
    }
}