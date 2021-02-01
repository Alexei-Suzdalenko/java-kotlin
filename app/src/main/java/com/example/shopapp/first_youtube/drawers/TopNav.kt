package com.example.shopapp.first_youtube.drawers
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.shopapp.R
import com.example.shopapp.first_youtube.drawers.ui.main.SectionsPagerAdapter

class TopNav : AppCompatActivity() {
    var dobleBackPressed = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_top_nav)
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)

      val actionBar = supportActionBar
        actionBar!!.title = resources.getString(R.string.app_name)
        actionBar.subtitle = resources.getString(R.string.description)
        actionBar.setElevation(0F)
        actionBar.setBackgroundDrawable(ColorDrawable(Color.parseColor("#E86441")));




       // val fireStore = FirebaseFirestore.getInstance()
      //  fireStore.collection("categories")
      //      .get()
      //      .addOnSuccessListener { documents ->
      //          for (document in documents) {
      //              Log.e("info",  document.id.toString() + " => " + document.data.toString())
      //          }
      //      }

      //  val user = User()
      //  fireStore.collection("categories").document("test test test2").set(user)
       // fireStore.collection("categories").document("Сырныйй  сад ю").delete()

      // val mDatabase = FirebaseDatabase.getInstance()
      // mDatabase.reference.child("users").setValue(user);




    }

    fun dobleBackToExit(){
        if(dobleBackPressed) {
            super.onBackPressed(); return
        }
        this.dobleBackPressed = true
        Toast.makeText(this, resources.getString(R.string.please_click), Toast.LENGTH_LONG).show()
        Handler().postDelayed({dobleBackPressed = false}, 2000)
    }

    override fun onBackPressed() {
        dobleBackToExit()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu to use in the action bar
        val inflater = menuInflater
        inflater.inflate(R.menu.botom_nav_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle presses on the action bar menu items
        when (item.itemId) {
            R.id.cor -> {

                return true
            }
            R.id.web -> {

                return true
            }
            R.id.my_orders -> {

                return true
            }
            R.id.my_accoutn -> {

                return true
            }
            R.id.site -> {

                return true
            }
            R.id.admin -> {

                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }




}