package radio.budilnik
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import budilnik.App

class Off_sound : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(App.player.isPlaying) App.stop()
        if(App.mp.isPlaying)     App.mpStop()

        val message = getString(R.string.message)
        Toast.makeText(this,message, Toast.LENGTH_LONG).show()

        finish()
    }
}
