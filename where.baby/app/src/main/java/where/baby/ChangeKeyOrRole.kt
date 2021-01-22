package where.baby
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.change_key_or_role.*
import where.baby.utils.App
import where.baby.utils.LocationUpdatesService

class ChangeKeyOrRole : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.change_key_or_role)

        Toast.makeText(this, App.sharedPreferences.getString("keyApp", "").toString(), Toast.LENGTH_LONG).show()
        val key = App.sharedPreferences.getString("keyApp", "sandbagging_¡¡¡q9¡t9quent").toString()

        change.setOnClickListener{
            val value = editText.text.toString()

            if(value == key){
                App.editor.putString("keyApp", "")
                App.editor.putString("role", "none")
                App.editor.apply()
                finish()
                stopService(Intent(this, LocationUpdatesService::class.java))
                startActivity(Intent(this, WhereIsMyBaby::class.java))
            }
        }

        Log.d("tag", "key + $key")
    }
}
