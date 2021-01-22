package putin.putin
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import udali.putina.GameView

class Udali_putina : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val game = GameView(this)
        setContentView(game)

        // https://www.youtube.com/watch?v=apDL78MFR3o
        // https://www.youtube.com/watch?v=vKeMrkDLkmk
    }
}
