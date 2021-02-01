package com.example.shopapp.first_youtube
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.shopapp.R
import com.example.shopapp.first_youtube.utils.Constants
import kotlinx.android.synthetic.main.activity_main.*
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sharedPreferences: SharedPreferences = getSharedPreferences(Constants.SHARED_TAG, Context.MODE_PRIVATE)
        val id = sharedPreferences.getString(Constants.USER_ID, "none")
        text_main.text = id
    }
}