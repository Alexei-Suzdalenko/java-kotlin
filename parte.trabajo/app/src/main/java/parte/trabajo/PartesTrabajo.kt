package parte.trabajo
import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.google.firebase.analytics.FirebaseAnalytics
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import parte.trabajo.assets.DataBase
import java.io.File
import java.io.FileOutputStream
class PartesTrabajo : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener{
    lateinit var drawerLayout       : DrawerLayout
    lateinit var too                : ActionBarDrawerToggle
    lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var firebaseAnalytics: FirebaseAnalytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        //  Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show()
        drawerLayout                = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController           = findNavController(R.id.nav_host_fragment)
        appBarConfiguration         = AppBarConfiguration(setOf(R.id.nav_home), drawerLayout)
        //     R.id.nav_home,
        //     R.id.nav_gallery,
        //     R.id.nav_slideshow,
        //     R.id.addNewClient
        // ), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        too = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_nam)
        drawerLayout.addDrawerListener(too)
        navView.setNavigationItemSelectedListener(this)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)


        firebaseAnalytics = FirebaseAnalytics.getInstance(this)
        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, drawer_layout.toString())
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "image")
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle)
      // val db = DataBase(this)
      // db.updateParteUriImage("4")
      // db.updateParteUriImage("5")
      // db.updateParteUriImage("6")
      // db.updateParteUriImage("7")
      // db.updateParteUriImage("8")
      // db.updateParteUriImage("9")



        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M){
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                val permi = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                requestPermissions(permi, 123)
            }
        }
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M){
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                val permi = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                requestPermissions(permi, 12)
            }
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
            val arrayA    = ArrayList<String>()
            arrayA.add(Manifest.permission.CALL_PHONE)
            if(arrayA.isNotEmpty()){
                ActivityCompat.requestPermissions(this, arrayA.toTypedArray(), 120)
            }
        }
   // val fr: Fragment = findViewById(R.id.nav_host_fragment)
   //     fr.
   // //   supportFragmentManager.beginTransaction().replace(R.id.nav_host_fragment, AddClient(), null).commit()
   // //   find(R.id.nav_host_fragment).removeAllViewsInLayout()
   //    supportFragmentManager.beginTransaction().add(R.id.nav_host_fragment, AddClient(), null).commit()
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.share -> { shareFun(); true }
            R.id.comment -> { startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=parte.trabajo"))); true }
            else -> super.onOptionsItemSelected(item)
        }
    }
  //  override fun onSupportNavigateUp(): Boolean {
  //      val navController = findNavController(R.id.nav_host_fragment)
  //      return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
  //  }


    private fun shareFun(){
        val work1 = BitmapFactory.decodeResource(resources, R.drawable.work1)
        val path  = getExternalFilesDir(Environment.DIRECTORY_PICTURES).toString()+"/partesDeTrabajo.png"
        val file  = File(path)

        try{
            val out = FileOutputStream(file)
            work1.compress(Bitmap.CompressFormat.PNG, 77, out)
            out.flush()
            out.close()
            val title = getString(R.string.app_name)
            val pu = FileProvider.getUriForFile(this, "parte.trabajo.fileprovider", File(file.path))
            val shareIntent = Intent()
            shareIntent.action = Intent.ACTION_SEND
            shareIntent.putExtra(Intent.EXTRA_TEXT, "$title \n\n https://play.google.com/store/apps/details?id=parte.trabajo")
            shareIntent.putExtra(Intent.EXTRA_STREAM, pu)
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            shareIntent.type = "image/*"
            startActivity(Intent.createChooser(shareIntent, "Share"))
        } catch (e: Exception){ Log.d("tag", "Error !!!" + e.message) }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.clientAdd     -> { startActivity(Intent(this, AddNewClient::class.java).putExtra("action", "addNewClient")) }
            R.id.menuListClient-> { startActivity(Intent(this, ListClient::class.java)) }
            R.id.listPartMenu  -> { startActivity(Intent(this, ListPart::class.java))   }
        }
        drawerLayout.closeDrawer( GravityCompat.START )
        return true
    }


}
