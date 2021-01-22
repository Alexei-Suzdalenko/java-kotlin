package factura.factura.old.saveForFuture
import android.content.Intent
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import factura.factura.old.AddNewClient
import factura.factura.old.ListClient
import factura.factura.old.ListPart
import factura.factura.R
import factura.factura.assets.AppPartesDeTrabajo
import factura.factura.assets.DataBase
import java.io.File
import java.text.DateFormat
import java.util.*

class Home : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root         = inflater.inflate(R.layout.fragment_home, container, false)




        val relativeLayoutGrani = root.findViewById<RelativeLayout>(R.id.relativeLayout)
        val scrollView          = root.findViewById<ScrollView>(R.id.scrollview)
        MobileAds.initialize(requireContext()) {}
        val mAdView2 = root.findViewById<AdView>(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView2.loadAd(adRequest)
        mAdView2.adListener = object: AdListener() {
            override fun onAdLoaded() {
                relativeLayoutGrani.post{
                    val heidhtAdview = mAdView2.height + 7
                    val layoutParams = scrollView.layoutParams as RelativeLayout.LayoutParams
                    layoutParams.setMargins(0,1, 0,heidhtAdview)
                    scrollView.layoutParams = layoutParams
                }
            }
        }





        val data = root.findViewById<TextView>(R.id.data      )
        val c     = Calendar.getInstance()
        val dateInfo= DateFormat.getDateInstance(DateFormat.FULL).format(c.time)
            data.text    = dateInfo
        val addClient    = root.findViewById<Button>(R.id.addClient   )
        val listClient   = root.findViewById<Button>(R.id.listClient  )
        val goToListPart = root.findViewById<Button>(R.id.goToListPart)
        val json         = root.findViewById<Button>(R.id.json)

        addClient.setOnClickListener {
            startActivity(Intent(requireContext(), AddNewClient::class.java).putExtra("action", "addNewClient"))
        }

        listClient.setOnClickListener{
            startActivity(Intent(requireContext(), ListClient::class.java))
        }

        goToListPart.setOnClickListener{
            startActivity(Intent(requireContext(), ListPart::class.java))
        }
      // val listClient = root.findViewById<Button>(R.id.listClient)
      // listClient.setOnClickListener{
      //     (context as AppCompatActivity).supportActionBar!!.title = resources.getString(R.string.client)
      //     parentFragmentManager.beginTransaction().replace(R.id.nav_host_fragment, ListClients(), ListClients().tag).commit()
      // }
        val dataBase = DataBase(requireContext())

        json.setOnClickListener{
            val title    = requireContext().resources.getString(R.string.app_nam)
            var body     = "$title \n\n"
            val listPart = dataBase.readDataPart()
            val nameClient = requireContext().resources.getString(R.string.name)
            val fecha = requireContext().resources.getString(R.string.fecha)
            val start = requireContext().resources.getString(R.string.startTimePart)
            val finish = requireContext().resources.getString(R.string.timeFinishPart)
            val work   = requireContext().resources.getString(R.string.workPart)
            val auto = requireContext().resources.getString(R.string.autoPart)
            val price = requireContext().resources.getString(R.string.pricePart)

            for (x in listPart){
                body = "$body \n $nameClient " + x.name_client
                body = "$body \n $fecha "      + x.fecha
                body = "$body \n $start "      + x.start_part
                body = "$body \n $finish "     + x.finish_part
                body = "$body \n $work "       + x.work_part
                if (auto != "")  body = "$body \n $auto "       + x.auto_part
                if (price != "")  body = "$body \n $price "       + x.price_part
                body += "\n\n"
            }

            try{
                val directory = File(AppPartesDeTrabajo.file_name)
                if(!directory.exists()) directory.mkdirs()
                val file = "file.txt"
                val f = File(directory, file)
                    f.createNewFile()
                f.writeText(body, Charsets.UTF_8)
                MediaScannerConnection.scanFile(requireContext(), arrayOf(f.path), arrayOf("application/txt"), null)

                val uri = Uri.parse(f.absolutePath)
                val i = Intent()
                i.action = Intent.ACTION_SEND
                i.putExtra(Intent.EXTRA_TEXT, "extra text")
                i.putExtra(Intent.EXTRA_STREAM, uri)
                i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                i.type = "application/txt"
                startActivity(Intent.createChooser(i, "share"))

            } catch (e:Exception){ Log.d("tag", "" + e.message)}

        }

        return root
    }
}
// https://www.youtube.com/watch?v=OLbsiE42JvE