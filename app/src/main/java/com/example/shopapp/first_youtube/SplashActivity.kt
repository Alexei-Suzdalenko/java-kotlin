package com.example.shopapp.first_youtube
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.shopapp.R

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
           // startActivity(Intent(this, UserProfileActivity::class.java))
            // startActivity(Intent(this, RegisterActivity::class.java))
             startActivity(Intent(this, LoginActivity::class.java))
            // startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 500)
    }
}