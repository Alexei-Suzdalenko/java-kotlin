package where.baby.utils
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.os.Looper
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.database.snapshot.Index
import where.baby.MapsActivity
import where.baby.R

class TestService: Service() {
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
       Log.d("tag", "start Service TestService_ _ _ _ _")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        val goToIndexPrepare        = Intent(this, MapsActivity::class.java)
        val goToIndex = PendingIntent.getActivity(this, 7,goToIndexPrepare,0)
        val notification= NotificationCompat.Builder(this, App.testId)
            .setContentTitle("test")
            .setContentText("test")
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentIntent(goToIndex)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()
        startForeground(111, notification)

        val role = App.sharedPreferences.getString("role", "none").toString()
        if(role == "son") {
            App.mFusedLocationClientTest.requestLocationUpdates(App.locationRequestTest, App.locationCallbackTest, Looper.getMainLooper())
        }

        return START_NOT_STICKY
    }
}