package factura.factura
import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.transition.TransitionManager.go
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import com.itextpdf.text.Document
import com.itextpdf.text.Paragraph
import com.itextpdf.text.pdf.PdfWriter
import factura.factura.assets.AppPartesDeTrabajo
import factura.factura.util.FileDownloader
import factura.factura.util.GetPermisson
import kotlinx.android.synthetic.main.activity_factura.*
import java.io.*
class MyOrganizationsDetails : AppCompatActivity() {
    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor
    val NONE = "none"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_factura)
        title                             = resources.getString(R.string.det_org)
        AppPartesDeTrabajo.go_to_org_data = false
        checkPer()
        GetPermisson().go()
        sharedPreferences = getSharedPreferences("tag", Context.MODE_PRIVATE)

        if (sharedPreferences.getString("name_org", NONE).toString() != NONE ) name_org.setText(sharedPreferences.getString("name_org", NONE).toString())

        if (sharedPreferences.getString("your_name", NONE).toString() != NONE ) your_name.setText(sharedPreferences.getString("your_name", NONE).toString())

        if (sharedPreferences.getString("company_address", NONE).toString() != NONE ) company_address.setText(sharedPreferences.getString("company_address", NONE).toString())

        if (sharedPreferences.getString("city", NONE).toString() != NONE ) city.setText(sharedPreferences.getString("city", NONE).toString())

        if (sharedPreferences.getString("state", NONE).toString() != NONE ) state.setText(sharedPreferences.getString("state", NONE).toString())

        if (sharedPreferences.getString("country", NONE).toString() != NONE ) country.setText(sharedPreferences.getString("country", NONE).toString())

        if (sharedPreferences.getString("zip", NONE).toString() != NONE ) zip.setText(sharedPreferences.getString("zip", NONE).toString())













      //  startActivity(Intent(this, CrearFactura::class.java)) // I need to delete this





   // try{
   //     // save text file to external storage
   //     val path = getExternalFilesDir(null)
   //     val letDirectory = File(path, "LET")
   //     if ( !letDirectory.exists()){  letDirectory.mkdirs() }
   //     val file = File(letDirectory, "records.txt")
   //     FileOutputStream(file).use {
   //         it.write("record goes here".toByteArray() )
   //     }
   //     file.appendText("[{\"name\":\"pete\"}]")

   //     val inputAsString = FileInputStream(file).bufferedReader().use { it.readText() }

   //     Toast.makeText(this, inputAsString, Toast.LENGTH_LONG).show()
   // } catch (e: FileNotFoundException){
   //     Log.d("tag", "-- >  >  >" + e.message)
   // }







     //  val filePuth = getExternalFilesDir(null).toString() + "/file1.pdf"
     //  try{
     //      val doc = Document()
     //      PdfWriter.getInstance(doc, FileOutputStream(filePuth))
     //      doc.open()
     //      doc.add(Paragraph("123"))
        //  val p1 = Paragraph("qaweqwe werqwerq")
        //  p1.alignment = Paragraph.ALIGN_CENTER
        //  doc.add(p1)
        //  val table = PdfPTable(3)
        //  for(x in 0 .. 11){
        //      table.addCell("x  dfgsdfgdsfgs sdfgsdfgsdfg sdfg sdfg sdfg sdf gsdf gsdf g er gerg reg sdrg wserg azsgrda erg asrg aerg aesrg aerg aerg arg aergarg areg aerg arega garga")
        //  }
        //  doc.add(table)
    //       doc.close()

         //  val uri = Uri.parse(filePuth)
         //  val i = Intent()
         //  i.type = "application/pdf"
         //  i.action = Intent.ACTION_SEND
         //  i.putExtra(Intent.EXTRA_STREAM, uri)
         //  i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
         //  startActivity(Intent.createChooser(i, "share"))
       //  Log.d("tag", "------->" + Build.VERSION.SDK_INT)

   //    var pdfUri = Uri.parse(filePuth)
   //   if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
   //       pdfUri = FileProvider.getUriForFile(this, "$packageName.provider", File(filePuth))
   //   } else {
   //  //     pdfUri = Uri.parse(filePuth)
   //   }
   //      val uri = Uri.parse(filePuth)

   //      val share = Intent()
   //      share.action = Intent.ACTION_SEND
   //      share.type = "application/pdf"
   //      share.putExtra(Intent.EXTRA_STREAM, uri)
   //      share.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
   //      startActivity(Intent.createChooser(share, "Share"))
   // } catch (e: Exception){ Log.d("tag", "ERROR ERROR ERROR > > > " + e.message)}



     //  val intent = Intent().apply {
     //      type = "image/*"
     //      action = Intent.ACTION_GET_CONTENT
     //      putExtra(Intent.EXTRA_MIME_TYPES, arrayOf("image/jpeg", "image/png"))
     //  }
     //  startActivityForResult(Intent.createChooser(intent, "Select Image"), 123)

     //   FileDownloader(this).execute("one", "two")

    //   try {

    //     val targetPdf: String = getExternalFilesDir(null).toString()
    //     val fi = File(targetPdf, "share")
    //     if(!fi.exists()) fi.mkdir()
    //     val f = File(fi, "share_text.pdf")
    //         f.createNewFile()

    //     val document = Document()
    //      PdfWriter.getInstance(document, FileOutputStream(f))


    //     document.open()
    //     document.add(Paragraph("123 h uh ipuh iouh iouh oiuh iouhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh"))
    //     document.close()


      //     Log.d("tag", "---> " + f.absolutePath)


       //    ShowFiles()
       //  openFolder(fi.absolutePath )
       //  val extStorageDirectory: String = getExternalStorageDirectory().toString()
       //  val folder = File(extStorageDirectory, "Download")
       //  if(!folder.exists()) folder.mkdir()

       //  val file = File(folder, "Read.pdf")
       //  try {
       //      file.createNewFile()
       //  } catch (e1: Exception) {
       //      e1.printStackTrace()
       //  }

          //  DownloadFile(targetPdf, file)

         //    val uri = Uri.parse(targetPdf)
         //    val i = Intent()
         //    i.type = "application/pdf"
         //    i.action = Intent.ACTION_SEND
         //    i.putExtra(Intent.EXTRA_STREAM, uri)
         //    i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
         //  //  startActivity(Intent.createChooser(i, "share"))

       //    Log.d("tag", "ok - - - -> " + file.absolutePath)








    //   val path = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".fileprovider", f)
    //   Log.d("tag", "view() Method path--> $path")

    //   val pdfIntent = Intent(Intent.ACTION_VIEW)
    //   pdfIntent.setDataAndType(path, "application/pdf")
    //   pdfIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
    //   pdfIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)


    //    //   startActivity(pdfIntent)
    //   } catch (e: Exception) {
    //       Log.d("tag", "E RO RO O R R R R R" + e.message)
    //       Toast.makeText(this, "No Application available to view PDF", Toast.LENGTH_SHORT).show()
    //   }


       // DownloadFile(this).execute("", "maven.pdf")


    }








    private fun checkPer() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M){
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                val permi = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                requestPermissions(permi, 123)
            }
        }
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M){
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                val permi = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                requestPermissions(permi, 12)
            }
        }
    }


    //class DownloadFile(val c : Context) : AsyncTask<String?, Void?, Void?>() {
  //    override fun doInBackground(vararg params: String?): Void? {

  //        val folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
  //        val pdfFile = File(folder, "sample.pdf")

  //        try {
  //            pdfFile.createNewFile()
  //        } catch (e: Exception) {
  //            Log.d("tag", "doInBackground() error" + e.message)
  //        }


  //        FileDownloader().downloadFile("http://www.africau.edu/images/default/sample.pdf", pdfFile, c)

  //        return null
  //    }
  //}








































 // fun ShowFiles() {
 //     try {
 //         val path = getExternalStorageDirectory().toString() + "/Download"
 //         Log.d("tag", "Path: $path")
 //         val files = File(path).listFiles()
 //         Log.d("Files", "Size: " + files!!.size)
 //         for (i in files.indices) {
 //             Log.d("tag", "FileName:" + files[i].name)
 //         }
 //     } catch (e: java.lang.Exception) {
 //         e.printStackTrace()
 //     }
 // }


 //  fun openFolder(targetPdf: String) {

 //      val selectedUri = Uri.parse(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString())
 //      val intent = Intent(Intent.ACTION_GET_CONTENT)
 //      intent.setDataAndType(selectedUri, "*/pdf")

 //      if (intent.resolveActivityInfo(packageManager, 0) != null) {
 //          startActivity(intent)
 //      } else {
 //          // if you reach this place, it means there is no any file
 //          // explorer app installed on your device
 //      }

 //  }




  //  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
  //      super.onActivityResult(requestCode, resultCode, data)
  //      if (requestCode == 123 && resultCode ==  Activity.RESULT_OK && data != null && data.data != null ){
  //          val selectImagePath = data.data
  //          Toast.makeText(this, "image url ==>> $selectImagePath", Toast.LENGTH_LONG).show()
  //          Log.d("tag", "puth -> " + selectImagePath)
  //          // content://com.android.providers.media.documents/document/image%3A1874
  //          // content://com.android.providers.media.documents/document/image%3A1889
  //      }
  //  }






    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.first, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.save_data_org -> {
                editor = sharedPreferences.edit()
                editor.putString("name_org",        name_org.text.toString())
                editor.putString("your_name",       your_name.text.toString())
                editor.putString("company_address", company_address.text.toString())
                editor.putString("city",            city.text.toString())
                editor.putString("state",           state.text.toString())
                editor.putString("country",         country.text.toString())
                editor.putString("zip",             zip.text.toString())
                editor.apply()
                val message =  resources.getString(R.string.date_saved)
                Toast.makeText(this, message, Toast.LENGTH_LONG ).show()
                finish()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}

