package radio.budilnik
import android.app.AlarmManager
import android.app.AlertDialog
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import budilnik.Receiver_radio_budilnik
import budilnik.Servce_radio_budilnik
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.custom.view.*
import java.util.*
class Index : AppCompatActivity() {
    private lateinit var context: Context
    private lateinit var alarmManager: AlarmManager
    private var dataTimeString = ""
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var edit: SharedPreferences.Editor
    private var nameRadio = ""
    lateinit var mAdView:AdView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        MobileAds.initialize(this){}
        mAdView = findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)
        mAdView.adListener = object: AdListener(){
            override fun onAdLoaded() {
                relativeLayoutGeneral.post {
                    val heidhtAdview = mAdView.height + 7
                    val layoutParams = scrollView.layoutParams as RelativeLayout.LayoutParams
                    layoutParams.setMargins(0,0, 0,heidhtAdview)
                    scrollView.layoutParams = layoutParams
                }
            }
        }



        context           = this
        alarmManager      = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        sharedPreferences = getSharedPreferences("radio", Context.MODE_PRIVATE)
        edit              = sharedPreferences.edit()
        setVisibilityAlarm()

        setTime.setOnClickListener {setAlarmForTime()}
        soundOff.setOnClickListener{startActivity(Intent(this, Off_sound::class.java))}

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.view_setting -> { startActivity(Intent(this, Setting_radio_budilnik::class.java))
                return true }
            R.id.share -> { val shareIntent = Intent()
                                shareIntent.action = Intent.ACTION_SEND
                                shareIntent.type = "text/plain" ; val index = resources.getString(R.string.app_name_des)
                                shareIntent.putExtra(Intent.EXTRA_TEXT, "$index https://play.google.com/store/apps/details?id=radio.budilnik")
                startActivity(Intent.createChooser(shareIntent, ""))
                return true }
            R.id.comment -> { startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=radio.budilnik")))
                return true }
            R.id.rus_radio ->{ edit.putString("key", "radio_rus"); edit.apply()
                startActivity(Intent(this, Setting_radio_budilnik::class.java))
                return true
            }
            R.id.eng_radio -> { edit.putString("key", "eng_radio"); edit.apply()
                startActivity(Intent(this, Setting_radio_budilnik::class.java))
                return true
            }
            R.id.spain -> { edit.putString("key", "spain"); edit.apply()
                startActivity(Intent(this, Setting_radio_budilnik::class.java))
                return true
            }
            R.id.buzoter -> { edit.putString("key", "buzoter"); edit.apply()
                startActivity(Intent(this, Setting_radio_budilnik::class.java))
                return true
            }
            R.id.disableNot -> { if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                     val i = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS)
                         i.putExtra(Settings.EXTRA_APP_PACKAGE, packageName)
                     startActivity(i) } else { val i = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                                                   i.data = Uri.parse("package:$packageName"); startActivity(i) }
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    private fun setVisibilityAlarm(){
        dataTimeString = sharedPreferences.getString("dataTimeString", "").toString()
        if(dataTimeString == ""){ image.visibility = View.GONE; textView.text = resources.getString(R.string.off)
        } else { image.visibility = View.VISIBLE; textView.text = dataTimeString }
    }


    private fun setAlarmForTime(){
        val mDialogView = LayoutInflater.from(this).inflate(R.layout.custom, null)
        val builder = AlertDialog.Builder(this).setView(mDialogView).setTitle("")
        val alertDialog = builder.show()
        val titleDialogText = resources.getString(R.string.titleDialogText)
        mDialogView.titleCustom.text = titleDialogText
        mDialogView.numberPicker_hours.minValue = 0
        mDialogView.numberPicker_hours.maxValue = 23
        mDialogView.numberPicker_hours.setFormatter{String.format("%02d", it)}
        mDialogView.numberPicker_minute.minValue = 0
        mDialogView.numberPicker_minute.maxValue = 59
        mDialogView.numberPicker_minute.setFormatter{String.format("%02d", it)}

        val setHour = sharedPreferences.getInt("data_hour",111)
        val setMin  = sharedPreferences.getInt("data_minute",111)
        if(setHour != 111){
            mDialogView.numberPicker_minute.value = setMin
            mDialogView.numberPicker_hours.value  = setHour
        }

        mDialogView.cancelDialog.setOnClickListener{ alertDialog.dismiss() }

        mDialogView.goToWork.setOnClickListener{
            val i = Intent(applicationContext, Servce_radio_budilnik::class.java)
            stopService(i)
            val hours    = mDialogView.numberPicker_hours.value
            val minute   = mDialogView.numberPicker_minute.value
            val calendar = Calendar.getInstance()
                calendar[Calendar.HOUR_OF_DAY] = hours
                calendar[Calendar.MINUTE]      = minute
                calendar[Calendar.SECOND]      = 0
                calendar[Calendar.MILLISECOND] = 0
            var milliseconds = calendar.timeInMillis
            if( milliseconds < System.currentTimeMillis() ) milliseconds += 86400000
            val intent        = Intent(context, Receiver_radio_budilnik::class.java)
            val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, milliseconds, pendingIntent)
              } else { alarmManager.setExact(AlarmManager.RTC_WAKEUP, milliseconds, pendingIntent) }

            edit.putLong("dataTimeLongSetAlarm", milliseconds)
            edit.putLong("dataTimeLong",  milliseconds)
            edit.putInt("data_hour", hours)
            edit.putInt("data_minute", minute)
            edit.apply()

            nameRadio = sharedPreferences.getString("name_radio", "Другая Астрахань Радио").toString()
            val ho = if(hours  < 10) "0$hours"
                     else "" + hours
            val mi = if(minute < 10) "0$minute"
                      else "" + minute
            val use = resources.getString(R.string.use) + " $ho:$mi \n\n\n $nameRadio"
            Toast.makeText(applicationContext, use, Toast.LENGTH_LONG).show()
            dataTimeString = "$ho:$mi"
            edit.putString("dataTimeString", dataTimeString); edit.apply()
            setVisibilityAlarm()
            ContextCompat.startForegroundService(this, i)
            alertDialog.dismiss()
        }
    }

}











































