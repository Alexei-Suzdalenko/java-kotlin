package where.baby.utils
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import where.baby.MapsActivity

class AutStart: BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {

        Utils(context!!).startAlarm()

        val startService = Intent(context, LocationUpdatesService::class.java)
        val showActivity = Intent(context, MapsActivity::class.java)
            showActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)

        Log.d("tag", "start process by BroadcastReceiver")

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            context!!.startForegroundService(startService)
    //        context!!.startActivity(showActivity)
        }else{
            context!!.startService(startService)
    //        context!!.startActivity(showActivity)
        }

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        Utils(context!!).startTestService()
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    }
}