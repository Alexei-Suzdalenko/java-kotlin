package russkoe.radio_rossii
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import russkoe.radio_rossii.ui.main.SectionsPagerAdapter
class Novosti_rossii : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        setContentView(R.layout.activity_main)
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)


      //  fab.setOnClickListener { view ->
      //      Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
      //              .setAction("Action", null).show()
      //  }
    }
}