package factura.factura.old
import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.media.MediaScannerConnection
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.kyanogen.signatureview.SignatureView
import com.squareup.picasso.Picasso
import factura.factura.R
import kotlinx.android.synthetic.main.create_new_part.*
import factura.factura.assets.AppPartesDeTrabajo
import factura.factura.assets.DataBase
import factura.factura.assets.Part
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.util.*
import kotlin.collections.ArrayList

class CreateNewPart : AppCompatActivity() {
    lateinit var intentCustom : Intent
    var name_client       = ""
    var id_part           = "none_user"
    lateinit var bitmap   : Bitmap
    lateinit var signature: SignatureView
    lateinit var db       : DataBase

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.create_new_part)

        db = DataBase(this)
        cancelPart.setOnClickListener{ finish() }
        signature = findViewById<View>(R.id.signature) as SignatureView
        signature.setOnTouchListener{ view, motionEvent ->
            when(motionEvent.action){
                MotionEvent.ACTION_DOWN -> { idScrollSignature.requestDisallowInterceptTouchEvent(true); false }
                MotionEvent.ACTION_UP   -> { idScrollSignature.requestDisallowInterceptTouchEvent(true); false }
                MotionEvent.ACTION_MOVE -> { idScrollSignature.requestDisallowInterceptTouchEvent(true); false }
                else -> true
            }
        }
     //  signature.post{ val widthSignature = signature.width
     //                  val params         = signature.layoutParams
     //                      params.height  = (widthSignature * 0.7).toInt() + 1
     //                  signature.layoutParams = params
     //  }


        val calendar = Calendar.getInstance()
        val hours    = calendar.get(Calendar.HOUR_OF_DAY)
        val h = if (hours < 10) "0$hours"
                else hours.toString()
        val minute  = calendar.get(Calendar.MINUTE)
        val m = if (minute < 10) "0$minute"
                else minute.toString()
        val day   = calendar.get(Calendar.DATE).toString()
        val month = calendar.get(Calendar.MONTH)+1
        val year  = calendar.get(Calendar.YEAR).toString()
        val date  = "$day/$month/$year"
        datePart.setText(date)
        val result = "$h:$m"
        timeStartPart.setText(result)
        timeFinishPart.setText(result)


        intentCustom = intent
        val action   = intentCustom.getStringExtra("action")
        if (action == "new_part"){
            AppPartesDeTrabajo.get_image_url = "0"
            name_client                      = intentCustom.getStringExtra("name")!!.toString()
            var name_client_part             = resources.getString(R.string.name_client)
                name_client_part             = "$name_client_part $name_client"
                nameClientPart.text = name_client_part
        }


        if (action == "update"){
            id_part = intentCustom.getStringExtra("id_part")!!.toString()
            updatePart.visibility = View.VISIBLE
            savePart.visibility   = View.GONE
            val name = intentCustom.getStringExtra("name_client")?.toString()
            var nameClentPart   = resources.getString(R.string.name_client)
                nameClentPart   = "$nameClentPart $name"
            nameClientPart.text = nameClentPart
            val fecha = intentCustom.getStringExtra("fecha")?.toString()
            datePart.setText(fecha)
            val startPart = intentCustom.getStringExtra("start")?.toString()
            timeStartPart.setText(startPart)
            val finishPart = intentCustom.getStringExtra("finish")?.toString()
            timeFinishPart.setText(finishPart)
            val workPartText = intentCustom.getStringExtra("work")?.toString()
            workPart.setText(workPartText)
            val price = intentCustom.getStringExtra("price")?.toString()
            pricePart.setText(price)
            val autoPartText = intentCustom.getStringExtra("auto")?.toString()
            autoPart.setText(autoPartText)
            Log.d("tag", "information by update - > " + AppPartesDeTrabajo.get_image_url)
            if( AppPartesDeTrabajo.get_image_url != "0" ){
                imageSignature.visibility = View.VISIBLE
                signature.visibility      = View.GONE
                saveSignature.visibility  = View.GONE
                updatePart.visibility     = View.VISIBLE
                Picasso.get().load(File( AppPartesDeTrabajo.get_image_url )).into(imageSignature)
            }
        }

        savePart.setOnClickListener     { savePartOnly()  }
        updatePart.setOnClickListener   { updatePartOnly()}

        clearSignature.setOnClickListener{ signature.clearCanvas()
          //  Log.d("tag", "id_part = $id_part")
          //  Log.d("tag", "AppPartesDeTrabajo.get_image_url = " + AppPartesDeTrabajo.get_image_url)
            db.updateParteUriImage(id_part)
            val file = File(AppPartesDeTrabajo.get_image_url)
            val r =  file.delete()
            if(file.exists()) file.delete()
            AppPartesDeTrabajo.get_image_url = "0"
            finish() ; val intent = intent
            startActivity(intent)
        }

        saveSignature.setOnClickListener{ bitmap = signature.signatureBitmap; saveImage(bitmap) }




    } /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private fun saveImage(b: Bitmap){
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
        try{
            val bytes          = ByteArrayOutputStream()
            b.compress(Bitmap.CompressFormat.PNG , 80, bytes)
            val wallPaperDirectory = File(AppPartesDeTrabajo.dir_image)
            if ( !wallPaperDirectory.exists() ){
                wallPaperDirectory.mkdirs()
            }
            val path_image = Calendar.getInstance().timeInMillis.toString()+".png"
            val f          = File(wallPaperDirectory, path_image)
                f.createNewFile()
            val fo = FileOutputStream(f)
                fo.write(bytes.toByteArray())
                MediaScannerConnection.scanFile(this, arrayOf(f.path), arrayOf("image/png"), null)
                fo.close()
            val sign_accept = resources.getString(R.string.sign_accept)
                Toast.makeText(this, sign_accept, Toast.LENGTH_LONG).show()

            imageSignature.visibility        = View.GONE
            signature.visibility             = View.GONE
            AppPartesDeTrabajo.get_image_url = f.absolutePath
            Picasso.get().load(File(AppPartesDeTrabajo.get_image_url)).into(imageSignature)

            if ("none_user" == id_part){ savePartOnly() }
            else updatePartOnly()
        } catch (e: Exception){ Log.d("tag", "E R R O R R R R R ____>>>> " + e.message)}
    }



    private fun savePartOnly(){
        val idClient = intentCustom.getIntExtra("id_client", 0).toString()
        val p = Part(
            AppPartesDeTrabajo.get_image_url,
            "",
            idClient,
            name_client,
            datePart.text.toString().trim(),
            timeStartPart.text.toString().trim(),
            timeFinishPart.text.toString().trim(),
            workPart.text.toString().trim(),
            autoPart.text.toString().trim(),
            pricePart.text.toString().trim()
            )
        db.addPart(p)
        finish()
        startActivity(Intent(this, ListPart::class.java))
    }



    private fun updatePartOnly(){
        val nameClient     = intentCustom.getStringExtra("name_client")?.toString()      ?: "name error"
        val idClientUpdate = intentCustom.getStringExtra("id_client_update")?.toString() ?: "id client error"
        val idPart         = intentCustom.getStringExtra("id_part")?.toString()          ?: "id part error"
        val p = Part(
            AppPartesDeTrabajo.get_image_url,
            idPart,
            idClientUpdate,
            nameClient,
            datePart.text.toString().trim(),
            timeStartPart.text.toString().trim(),
            timeFinishPart.text.toString().trim(),
            workPart.text.toString().trim(),
            autoPart.text.toString().trim(),
            pricePart.text.toString().trim()
        )
        db.updatePart(p)
        finish()
        startActivity(Intent(this, ListPart::class.java))
    }
}





























