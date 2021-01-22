package where.baby
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_maps.*
import where.baby.utils.*
import java.lang.Exception

// data base diseno-web-cantabria
class MapsActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var mMap: GoogleMap
    var role = ""
    var key = ""
    private var dela = ""
    private val name = App.sharedPreferences.getString("keyApp", "").toString()
    lateinit var c : Context
    private lateinit var mAdView : AdView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        Utils(this).startTestService()


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        MobileAds.initialize(this) {}
        mAdView = findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)
        mAdView.adListener = object: AdListener() {
            override fun onAdLoaded() {
                relativeLayoutGeneral.post{
                    val dateHeight = danger_no_data.height
                    val heidhtAdview = mAdView.height
                    val layoutParams = linearLayout.layoutParams as RelativeLayout.LayoutParams
                    layoutParams.setMargins(0,dateHeight, 0,heidhtAdview)
                    linearLayout.layoutParams = layoutParams
                }
            }
        }



        c = this
        role = App.sharedPreferences.getString("role", "").toString()
        key  = App.sharedPreferences.getString("keyApp", "Error").toString()
        title = resources.getString(R.string.role_) + " - " + role
        if(role == "") finish()
        Log.d("tag", "role role role : $role")

        Utils(this).getPermissionApplication()
        if(role == "son") Utils(this).startAlarm()
        else stopService(Intent(this, LocationUpdatesService::class.java))

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)


        val postListener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                try{
                    val post = dataSnapshot.getValue(User::class.java) ?: return
                    if(dela == "poshlo"){
                        Log.d("tag", "post $post")
                        danger_no_data.visibility = View.VISIBLE
                        val data = post.latitude.toString() + " - " + post.longitude.toString()
                        danger_no_data.text = data
                    //   mMap.clear()
                       val sydney = LatLng(post.latitude, post.longitude)
                       mMap.addMarker(MarkerOptions().position(sydney).title(name))
                       mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
                    }
                } catch (e:Exception){
                    Log.d("tag", "ERROR " + e.message)
                }
            }
        }

        val testForDataHaveValue = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if(dataSnapshot.value == null){
                    danger_no_data.visibility = View.VISIBLE
                    val forYouKey = "No data for your key = $key"
                    danger_no_data.text = forYouKey
                }
            }
        }

        if(role == "father"){
            DataBase.database.child(key).addValueEventListener(postListener)
            DataBase.database.child(key).addListenerForSingleValueEvent(testForDataHaveValue)
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
       // mMap.isIndoorEnabled = false
        mMap.isMyLocationEnabled = true
        mMap.uiSettings.isMyLocationButtonEnabled = true
        dela = "poshlo"
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.change_roll_key, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.change_roll_name ->{ finish(); startActivity(Intent(this, ChangeKeyOrRole::class.java))
                return true }
            R.id.share ->{
              return true }
        }
        return super.onOptionsItemSelected(item)
    }

}
