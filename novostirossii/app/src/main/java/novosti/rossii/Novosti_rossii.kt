package novosti.rossii
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import novosti.rossii.ui.main.App
import novosti.rossii.ui.main.NewsUtil
import novosti.rossii.ui.main.SectionsPagerAdapter
import org.json.JSONArray
import java.net.URL

class Novosti_rossii : AppCompatActivity() {
    lateinit var sharedPreferences: SharedPreferences
    lateinit var prefEditor : SharedPreferences.Editor
    @SuppressLint("CommitPrefEdits")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        sharedPreferences = getSharedPreferences("news", Context.MODE_PRIVATE)

        setContentView(R.layout.activity_main)
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)


        NewsUtil.totalUpdate()

      //   if(App.showMessage) showHelloMessage(this)

      //  fab.setOnClickListener { view ->
      //      Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
      //              .setAction("Action", null).show()
      //  }



        val keyToEnableNewsYoutube = sharedPreferences.getString("key", "off").toString()


        val test = Thread{
            try{
                val urlFromGithub = JSONArray(URL("https://diseno-desarrollo-web-app-cantabria.github.io/novosti_rossii/yes.js").readText()).get(0).toString()
                coordinatorlayoutAA.post{
                        prefEditor = sharedPreferences.edit()
                        prefEditor.putString("key", urlFromGithub)
                        prefEditor.apply()
                }
            } catch (e: Exception){ Log.d("tag", "Error Novosti_Rossii.kt => " + e.message)}
        }
        if(keyToEnableNewsYoutube != "yes"){
            test.start()
        }
    }


    private fun showHelloMessage(c:Context){
        val b = AlertDialog.Builder(c)
            b.setTitle("Внимание")
            b.setMessage("Цель данного приложения - максимальное распространение достоверной информации. \n\n" +
                    "Чем больше людей будет мыслить критически, тем быстрее мы-народ России будем жить по Человечески. \n\n" +
                    "Поэтому, пожалуйста, поделитесь ссылкой на это приложение, чтобы как можно больше людей задумалось над происходящим в России." )
            b.setNeutralButton("Коментарии"){dialog, which ->
                App.showMessage = false
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=novosti.rossii"))); dialog.dismiss()
            }
            b.setPositiveButton("Поделиться"){dialog, which ->
                val s    = Intent()
                s.action = Intent.ACTION_SEND
                s.type   = "text/plain"
                s.putExtra(Intent.EXTRA_TEXT, "Новости Россиии без цензуры \n\n https://play.google.com/store/apps/details?id=novosti.rossii")
                startActivity(Intent.createChooser(s, ""))
                App.showMessage = false; dialog.dismiss()
            }
            b.setNegativeButton("Уже поделился!"){dialog, which ->
                App.showMessage = false; dialog.dismiss()
            }
            b.show()
    }


}