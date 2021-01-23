package com.example.simpleactivity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    lateinit var button: Button
    lateinit var floatingActionButton: FloatingActionButton
    lateinit var snackbar_layout: CoordinatorLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val contextView = findViewById<View>(R.id.context_view)
        button = findViewById(R.id.button)
        floatingActionButton = findViewById(R.id.floatingActionButton)
        snackbar_layout = findViewById(R.id.snackbar_layout)

        // snackbar
        button.setOnClickListener {
            val snackbar =  Snackbar.make(snackbar_layout, "Вы уверены ?", Snackbar.LENGTH_INDEFINITE)
            snackbar.duration = 10000
            snackbar.animationMode = BaseTransientBottomBar.ANIMATION_MODE_SLIDE
            snackbar.anchorView = floatingActionButton
            snackbar.setAction("Да", {});
            snackbar.show()
        }

        // dialog
        MaterialAlertDialogBuilder(this)
            .setIcon(R.drawable.ic_launcher_background)
            .setTitle(resources.getString(R.string.app_name))
            .setMessage("message")
            .setNeutralButton("Neutral") { dialog, which -> }
            .setNegativeButton("Positive") { dialog, which -> }
            .setPositiveButton("Negative") { dialog, which -> }
            .show()



    }
}