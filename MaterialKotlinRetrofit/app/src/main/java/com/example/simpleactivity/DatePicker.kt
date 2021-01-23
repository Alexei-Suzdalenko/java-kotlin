package com.example.simpleactivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.datepicker.MaterialDatePicker
import java.util.*

class DatePicker : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_date_picker)

        val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
            calendar.clear()
        val today = MaterialDatePicker.todayInUtcMilliseconds()

        val materialPicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("SELECT A DATE")
            .setSelection(today)
            .build()

        materialPicker.addOnPositiveButtonClickListener {
            Toast.makeText(this, materialPicker.headerText , Toast.LENGTH_SHORT).show()
        }

        materialPicker.show(supportFragmentManager, "one")



    }
}