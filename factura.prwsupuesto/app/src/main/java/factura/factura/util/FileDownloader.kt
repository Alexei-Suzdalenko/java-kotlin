package factura.factura.util

import android.content.Context
import android.media.RingtoneManager
import android.net.Uri
import android.os.AsyncTask
import android.os.Environment
import android.util.Log
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL


class FileDownloader(val c: Context) : AsyncTask<String?, Void?, Void?>() {
    private  val TAG = "tag"
    private  val MEGABYTE = 1024 * 1024


    fun download(fileUrl: String, directory: File, c: Context) {
        try {
            val url = URL(fileUrl)
            val urlConnection: HttpURLConnection = url.openConnection() as HttpURLConnection
            urlConnection.connect()
            val inputStream: InputStream = urlConnection.inputStream
            val fileOutputStream = FileOutputStream(directory)

            val buffer = ByteArray(MEGABYTE)
            var bufferLength = 0
            while (inputStream.read(buffer).also { bufferLength = it } > 0) {
                fileOutputStream.write(buffer, 0, bufferLength)
            }
            fileOutputStream.close()
            Log.d(TAG, "downloadFile() completed ")

            val notification: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            val r = RingtoneManager.getRingtone(c, notification)
            r.play()

        } catch (e: Exception) {
            Log.d(TAG, "downloadFile() error" + e.message)
        }
    }


    override fun doInBackground(vararg params: String?): Void? {
        val folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        val pdfFile = File(folder, "sample_test.pdf")

        try {
            pdfFile.createNewFile()
        } catch (e: Exception) {
            Log.d("tag", "doInBackground() error" + e.message)
        }
        Log.d("tag", "----------------------- start ----------------------")
        download("http://www.africau.edu/images/default/sample.pdf", pdfFile, c)
        return null
    }


}