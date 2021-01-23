package com.example.simpleactivity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import kotlinx.android.synthetic.main.activity_navigation_left_driwer.*

class NavigationLeftDriwer : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation_left_driwer)

        toolbar1.setTitle("asdfasdf")

        val actionBarDrawerToggle = ActionBarDrawerToggle(
            this,
                    drawerLayout,
                    toolbar1,
                    R.string.open, R.string.close
        )

        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()


        navigationView.setNavigationItemSelectedListener{
            when(it.itemId){
                R.id.first -> {
                    Toast.makeText(this, "text", Toast.LENGTH_LONG).show()
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
                else -> false
            }

        }



    }
}