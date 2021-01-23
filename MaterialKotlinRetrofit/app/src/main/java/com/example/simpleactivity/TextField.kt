package com.example.simpleactivity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import com.google.android.material.textfield.TextInputLayout

class TextField : AppCompatActivity() {

    private lateinit var text_input: TextInputLayout
    private lateinit var dropdown_text: AutoCompleteTextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text_field)

        text_input = findViewById(R.id.text_input);
        val items = arrayOf("a", "b", "c", "d", "e")
        val adapter = ArrayAdapter(this, R.layout.dropdown_item, items)

        dropdown_text = findViewById(R.id.dropdown_text)
        dropdown_text.setAdapter(adapter)

    }
}