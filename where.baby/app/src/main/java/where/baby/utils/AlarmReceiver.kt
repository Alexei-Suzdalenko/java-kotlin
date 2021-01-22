package where.baby.utils
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import where.baby.MapsActivity

class AlarmReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {

        Utils(context!!).startAlarm()

      //  val i = Intent(context, MapsActivity::class.java)
      //  i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
      //  context!!.startActivity(i)

        val startService = Intent(context, LocationUpdatesService::class.java)

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            context!!.startForegroundService(startService)
        }else{
            context!!.startService(startService)
        }

    }
}