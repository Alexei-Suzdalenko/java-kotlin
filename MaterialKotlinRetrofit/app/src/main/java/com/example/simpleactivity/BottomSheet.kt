package com.example.simpleactivity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.SyncStateContract
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomsheet.BottomSheetBehavior

class BottomSheet : AppCompatActivity() {

    private lateinit var bottomAppBar: BottomAppBar
    private lateinit var bottomSheet: LinearLayout
    // private var bottomSheetBehavior: BottomSheetBehavior
    private lateinit var linearLayout: LinearLayout
    private lateinit var imageView: ImageView

    private lateinit var custom_bottom_sheet: ConstraintLayout




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bottom_sheet)

      //  bottomAppBar = findViewById(R.id.bottomAppBar)
      //  bottomSheet = findViewById(R.id.bottomSheet)
      //  val bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
      //  bottomAppBar.setNavigationOnClickListener { bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED; }

        custom_bottom_sheet = findViewById(R.id.custom_bottom_sheet)
        val bottomSheetBehavior2 = BottomSheetBehavior.from(custom_bottom_sheet)

        val headerLayout: LinearLayout = findViewById(R.id.header_layout)
        headerLayout.setOnClickListener {
            if(bottomSheetBehavior2.state !=  BottomSheetBehavior.STATE_EXPANDED){
                bottomSheetBehavior2.state = BottomSheetBehavior.STATE_EXPANDED
            } else {BottomSheetBehavior.STATE_COLLAPSED;}
        }
        val headerImage: ImageView = findViewById(R.id.header_arrow)

        bottomSheetBehavior2.addBottomSheetCallback(
            object : BottomSheetBehavior.BottomSheetCallback() {
                  override fun onSlide(bottomSheet: View, slideOffset: Float) {
                      headerImage.rotation = slideOffset * 180

                      Log.d("none", "on slide")
                  }

                     override fun onStateChanged(bottomSheet: View, newState: Int) {
                         when(newState){
                             BottomSheetBehavior.STATE_COLLAPSED -> {
                                 // DO SOME FINGs
                             }
                         }
                         Log.d("none", "onStateChanged")
                     }
        })



        // https://www.youtube.com/watch?v=TlWgVvuPeOI&list=PLGCjwl1RrtcSxDB7LTgNf5k_PD3gIs5hD&index=5&ab_channel=TVACStudio
    }
}