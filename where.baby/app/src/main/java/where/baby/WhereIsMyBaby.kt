package where.baby
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.core.app.NotificationManagerCompat
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_where_is_my_baby.*
import where.baby.utils.App
import where.baby.utils.LocationUpdatesService
import where.baby.utils.Utils

@Suppress("UNREACHABLE_CODE")
class WhereIsMyBaby : AppCompatActivity() {
    private var locationManager: LocationManager? = null
    private var isLocationWork = false
    private lateinit var notificationManager: NotificationManagerCompat
    var value = 0

    @SuppressLint("CommitPrefEdits")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_where_is_my_baby)

 /////////////////////////////////////////////////////////////////////////////       ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        Utils(this).startTestService()


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



        notificationManager = NotificationManagerCompat.from(this)
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        val role = App.sharedPreferences.getString("role", "none").toString()
        when (role) {
            "son" -> Utils(this).startAlarm()
            "father" -> {
                val key = App.sharedPreferences.getString("keyApp", "Error").toString()
                editText_where_is_my_baby.setText(key)
                stopService(Intent(this, LocationUpdatesService::class.java))
            }
            else -> {
                stopService(Intent(this, LocationUpdatesService::class.java))
            }
        }

        Utils(this).getPermissionApplication()
        isLocationWork = Utils(this).checkIsLocationWork()
        checkUserLocationEnabled()

        if(role == "son" || role == "father"){
            finish(); startActivity(Intent(this, MapsActivity::class.java))
        }


        button_where_is_my_baby.setOnClickListener {
            Utils(this).getPermissionApplication()
            if(notificationManager.areNotificationsEnabled()) {   // test notification disabled
                val userEnableLocation = resources.getString(R.string.disable_notification)
                val message = resources.getString(R.string.user_disable)
                val locationSettings = resources.getString(R.string.disable_n)
                Utils(this).openNotificationManager(userEnableLocation, message, locationSettings)
                return@setOnClickListener
            }
            if(!Utils(this).checkIsLocationWork()) {    // test location enabled
                val userEnableLocation = resources.getString(R.string.user_enable_location)
                val message = resources.getString(R.string.becouse)
                val locationSettings = resources.getString(R.string.location_setttings)
                Utils(this).showUserAlert(userEnableLocation,message,locationSettings)
                return@setOnClickListener
            }
            val editTextValue = editText_where_is_my_baby.text.toString().trim()
            if(editTextValue.isEmpty()) return@setOnClickListener
         //  if (editTextValue.length > 7 ){ val m = resources.getString(R.string.theKeyNeedByMore)
         //      Snackbar.make(it, m, Snackbar.LENGTH_LONG).show()
         //     } else {
                   App.editor.putString("keyApp", editTextValue)
                   App.editor.apply()
                   val m = resources.getString(R.string.key_saved)
                Snackbar.make(it, m, Snackbar.LENGTH_LONG).show()
                functionSetRoleTelephone()
             //   startActivity(Intent(this, MapsActivity::class.java))
          //     }
        }


    }


    private fun checkUserLocationEnabled() : Boolean{
        if(!isLocationWork){
            val userEnableLocation = resources.getString(R.string.user_enable_location)
            val message = resources.getString(R.string.becouse)
            val locationSettings = resources.getString(R.string.location_setttings)
            Utils(this).showUserAlert(userEnableLocation,message,locationSettings)
        }
        return isLocationWork
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.setting_file, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.set_notification ->{
                val userEnableLocation = resources.getString(R.string.disable_notification)
                val message = resources.getString(R.string.user_disable)
                val locationSettings = resources.getString(R.string.disable_n)
                Utils(this).openNotificationManager(userEnableLocation, message, locationSettings); return true }
            R.id.set_location ->{
                val userEnableLocation = resources.getString(R.string.user_enable_location)
                val message = resources.getString(R.string.becouse)
                val locationSettings = resources.getString(R.string.location_setttings)
                Utils(this).showUserAlert(userEnableLocation,message,locationSettings); return true }
        }
        return super.onOptionsItemSelected(item)
    }


    @SuppressLint("CommitPrefEdits")
    private fun functionSetRoleTelephone(){
        val d = AlertDialog.Builder(this)
        val title = resources.getString(R.string.set_r)
        d.setTitle(title)
        val message = resources.getString(R.string.set_phone)
        d.setMessage(message)
        val father = resources.getString(R.string.father_)
        val son = resources.getString(R.string.son_)
        d.setNegativeButton(son){ a, b ->
            App.editor.putString("role", "son")
            App.editor.apply()
            startActivity(Intent(this, MapsActivity::class.java))
        }
        d.setPositiveButton(father){ a, b ->
            App.editor.putString("role", "father")
            App.editor.apply()
            startActivity(Intent(this, MapsActivity::class.java))
        }
        d.show()
    }





}
