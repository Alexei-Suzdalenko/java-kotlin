package com.example.simpleactivity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import com.google.android.material.bottomappbar.BottomAppBar

class ButtomAppBar : AppCompatActivity() {

    private lateinit var bottomAppBar: BottomAppBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buttom_app_bar)

        bottomAppBar = findViewById(R.id.bottom_app_bar)
        setSupportActionBar(bottomAppBar)

        bottomAppBar.setNavigationOnClickListener {
            bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu);
        menuInflater.inflate(R.menu.bottom_bar_menu, menu)
        return true;
    }
}