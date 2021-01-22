package parte.trabajo.assets
import android.app.Application
import java.io.File

class AppPartesDeTrabajo : Application() {
    companion object{
        var file_name      = "/file"
        var dir_global     = "/dirVishevsky"
        var dir_image      = "/dir_image"
        var get_image_url  = "0"
        lateinit var root  : File
    }

    override fun onCreate() {
        super.onCreate()
        dir_global = getExternalFilesDir(null).toString() + "/dir_vishevsky"
        dir_image  = getExternalFilesDir(null).toString() + "/putin_juilo" + dir_image
        root       = getExternalFilesDir(null)!!
        file_name  = getExternalFilesDir(null).toString() + "/putin_ebanoe_juilo" + file_name
    }
}