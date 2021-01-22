package parte.trabajo
import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import com.itextpdf.text.Document
import com.itextpdf.text.Paragraph
import com.itextpdf.text.pdf.PdfWriter
import kotlinx.android.synthetic.main.list_part.*
import parte.trabajo.assets.AdapterParts
import parte.trabajo.assets.AppPartesDeTrabajo
import parte.trabajo.assets.DataBase
import parte.trabajo.assets.Part
import java.io.File
import java.io.FileOutputStream
import java.lang.Exception

class ListPart : AppCompatActivity() {
    lateinit var c           : Context
    val fileName             = "text"
    lateinit var db          : DataBase
    var listPart: List<Part> = ArrayList()
    var a                    = "as"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.list_part)

        a                        = resources.getString(R.string.numero)
        c                        = this
        db                       = DataBase(this)
        listPart                 = db.readDataPart()
        if (listPart.isNotEmpty()) textViewListPart.visibility = View.GONE
        val size = listPart.size - 1
        for( x in 0..size){
            Log.d("tag", "url image = " + listPart[x].puth_image)
        }
        listViewListPart.adapter = AdapterParts(this, listPart)
        listViewListPart.setOnItemClickListener { _ , _ , position, _ ->         Log.d("tag", "price AAAAArriba !!!-> " + listPart[position].price_part)
            touchPart(listPart[position].id_part, listPart[position].id_client, listPart[position].puth_image, listPart[position].name_client, listPart[position].fecha, listPart[position].start_part, listPart[position].finish_part, listPart[position].work_part, listPart[position].price_part ,listPart[position].auto_part )
        }
    }


    private fun touchPart(id_part:String, id_client:String, puth_image:String, name_client:String, fecha:String, start_part:String, finish_part:String, work_part:String, price:String , auto_part:String){
        val builder = AlertDialog.Builder(this)
        val nameClient = resources.getString(R.string.name_client)
            builder.setTitle("$nameClient $name_client")
            val adapter = ArrayAdapter<String>(this, R.layout.button)
            val sentPart = resources.getString(R.string.sent_part)
            adapter.add(sentPart)
            val changePart = resources.getString(R.string.change_part)
            adapter.add(changePart)
            val cancel = resources.getString(R.string.cancel)
            builder.setPositiveButton(cancel){ dialog, _ -> dialog.dismiss() }
            val delete = resources.getString(R.string.delete_part)

            builder.setNegativeButton(delete){  d , _ -> d.dismiss()
                val secondBuilder = AlertDialog.Builder(this)
                val areYouSure = resources.getString(R.string.are_you_sure)
                secondBuilder.setTitle(delete)
                secondBuilder.setMessage(areYouSure)
                val no = resources.getString(R.string.cancel)
                secondBuilder.setPositiveButton(no){d , _ -> d.dismiss() }
                val yes =  resources.getString(R.string.yes)
                secondBuilder.setNegativeButton(yes){ _, _ ->
                    val imageSrc = db.getImageSrc(id_part).toString()
                    val file     = File(imageSrc)
                    val one      = file.delete()
                    db.deletePart(id_part); val intent = intent; finish(); startActivity(intent) }
                secondBuilder.show()
            }

             builder.setAdapter(adapter){ _ , item ->
                 when(item){
                     0->{    checkPer()
                             val sending = resources.getString(R.string.creating_archive_for_send)
                             Toast.makeText(this, sending, Toast.LENGTH_LONG).show()
                             showPdf(id_part, name_client, fecha, start_part, finish_part, work_part, price, auto_part, puth_image)
                     }
                     1->{ val i = Intent(this, CreateNewPart::class.java)
                         AppPartesDeTrabajo.get_image_url = puth_image
                         i.putExtra("image",      puth_image) // not use
                         i.putExtra("action", "update")
                         i.putExtra("id_part",       id_part)
                         i.putExtra("name_client",   name_client)
                         i.putExtra("fecha",         fecha)
                         i.putExtra("start",         start_part)
                         i.putExtra("finish",        finish_part)
                         i.putExtra("price",         price)
                         i.putExtra("work",          work_part)
                         i.putExtra("id_clien",      id_client)
                         i.putExtra("auto",          auto_part)
                         finish(); startActivity(i)
                     }
                 }
             }
             builder.show()
    }


















    fun showPdf(id:String, name:String, fecha:String, start:String, finish:String, work:String, price:String, auto:String, puth:String ){
        val filePuth = getExternalFilesDir(null).toString() + "/" + fileName + ".pdf"
        try{
            val doc = Document()
                PdfWriter.getInstance(doc, FileOutputStream(filePuth))
                doc.open()
                val number = resources.getString(R.string.number)
                    doc.add(Paragraph("$number $id"))
                val data = resources.getString(R.string.fecha)
                    doc.add(Paragraph("$data: $fecha"))
                val nameClient = resources.getString(R.string.name)
                    doc.add(Paragraph("$nameClient $name"))
                val time_work = resources.getString(R.string.time_work)
                    doc.add(Paragraph("$time_work: $start - $finish"))
                val workExecuted = resources.getString(R.string.work_executed)
                    doc.add(Paragraph("$workExecuted $work"))
                val total = resources.getString(R.string.in_total)
                   if (price.length > 2)   doc.add(Paragraph("$total $price"))
                val autorization = resources.getString(R.string.autorization)
                   if (auto.length > 2)  doc.add(Paragraph("$autorization $auto"))
                if ( puth == "0") {}
                else { val image = com.itextpdf.text.Image.getInstance(puth)
                     val scaler = 10.toFloat()
                    image.scalePercent(scaler)
                    doc.add(image)
                }
                doc.close()
            shareRealPdf()
        } catch (e: Exception){ Log.d("tag", "" + e.message)}
    }




    private fun shareRealPdf(){
        val f = getExternalFilesDir(null).toString()+"/"+fileName+".pdf"
        val uri = Uri.parse(f)
        val i = Intent()
        i.action = Intent.ACTION_SEND
        i.putExtra(Intent.EXTRA_TEXT, "extra text")
        i.putExtra(Intent.EXTRA_STREAM, uri)
        i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        i.type = "application/pdf"
        startActivity(Intent.createChooser(i, "share"))
    }
















































































































    fun checkPer(){
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
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            val arrayA    = ArrayList<String>()
            arrayA.add(Manifest.permission.CALL_PHONE)
            if(arrayA.isNotEmpty()){
                ActivityCompat.requestPermissions(this, arrayA.toTypedArray(), 120)
            }
        }
    }































































}








































































































































































































































































































































