package where.baby.utils
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class TestReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {

        App.startAlarmAndService(context!!)

        Log.d("tag", "Start TestReceiver_ _ _ _ _ ")

       // Utils(context!!).startTestService()

    }
}