package com.example.simpleactivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ActionButtons : AppCompatActivity() {

    private lateinit var floatingActionButton: FloatingActionButton;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_action_buttons)

        floatingActionButton.setOnClickListener {

        }
    }
}