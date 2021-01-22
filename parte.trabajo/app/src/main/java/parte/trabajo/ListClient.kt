package parte.trabajo
import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.list_client.*
import parte.trabajo.assets.CustomAdapter
import parte.trabajo.assets.DataBase
import parte.trabajo.assets.DataPerson
import java.lang.Exception

class ListClient : AppCompatActivity() {
    private lateinit var db: DataBase
    private var listPerson : List<DataPerson> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.list_client)

        db                   = DataBase(this)
        listPerson           = db.readData()
        if (listPerson.isNotEmpty()) {
            messageListClient.visibility = View.GONE
            mainListView.adapter = CustomAdapter(this, listPerson)
            mainListView.setSelection(listPerson.size)
            mainListView.setOnItemClickListener{ _ , _ , posit, _ ->
                option(listPerson[posit].name, listPerson[posit].tlf, listPerson[posit].id)
            }
        }
    }

    private fun option(nameItem: String, tlfItem: String, idItem: String){
        val builder               = AlertDialog.Builder(this)
        val name                  = resources.getString(R.string.name)
            builder.setTitle("$name $nameItem")
        val array                 = ArrayAdapter<String>(this, R.layout.button)
            val createPart        = resources.getString(R.string.create_part)
            array.add(createPart)
            val call              = resources.getString(R.string.call)
            array.add(call)
            val updateClient      = resources.getString(R.string.update_client)
            array.add(updateClient)
            builder.setAdapter(array){ _ , which ->
                when(which){
                    0->{ val i = Intent(this, CreateNewPart::class.java)
                             i.putExtra("action", "new_part")
                             i.putExtra("id_client", idItem)
                             i.putExtra("name", nameItem)
                             startActivity(i)
                    }
                    1->{ if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                              val arrayA    = ArrayList<String>()
                                  arrayA.add(Manifest.permission.CALL_PHONE)
                              if(arrayA.isNotEmpty()){
                                  ActivityCompat.requestPermissions(this, arrayA.toTypedArray(), 120)
                              }
                          }
                          try{ startActivity(Intent(Intent.ACTION_CALL, Uri.parse("tel: $tlfItem"))) } catch (e: Exception){}
                    }
                    2->{ val i = Intent(this, AddNewClient::class.java)
                             i.putExtra("action", "update")
                             i.putExtra("id_client", idItem)
                             i.putExtra("name", nameItem)
                             i.putExtra("tlf", tlfItem)
                         startActivity(i)
                    }
                }
            }
            val delete     = resources.getString(R.string.delete)
            builder.setNeutralButton(delete){ _ , _ ->
                val builderCheckDelete = AlertDialog.Builder(this)
                val title              = resources.getString(R.string.delete_client)
                    builderCheckDelete.setTitle("$title : $nameItem ")
                val areSure            = resources.getString(R.string.are_sure)
                    builderCheckDelete.setMessage(areSure)
                val no                 = resources.getString(R.string.cancel)
                    builderCheckDelete. setPositiveButton(no){ d , _ -> d.dismiss() }
                val yes                = resources.getString(R.string.yes)
                    builderCheckDelete.setNegativeButton(yes){ _ , _ -> val db = DataBase(this)
                        val person = DataPerson(idItem, "", "")
                        db.deletePerson(person)
                        startActivity(Intent(this, ListClient::class.java))
                        finish()
                    }
                builderCheckDelete.show()
            }
            val cancel    = resources.getString(R.string.cancel)
            builder.setNegativeButton(cancel){ dialog, _ -> dialog.dismiss() }

        builder.show()
    }





















}
