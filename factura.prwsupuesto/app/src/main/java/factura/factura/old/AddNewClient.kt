package factura.factura.old
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import factura.factura.R
import kotlinx.android.synthetic.main.add_new_client.*
import factura.factura.assets.DataBase
import factura.factura.assets.DataPerson

class AddNewClient : AppCompatActivity() {
    var idClient = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_new_client)

        val db = DataBase(this)
        val intent = intent
        val action = intent.getStringExtra("action")?.toString()
        if (action == "addNewClient"){
            update.visibility = View.GONE
        }
        if (action == "update"){
            save.visibility   = View.GONE
            idClient = intent.getStringExtra("id_client")!!.toString()
            Toast.makeText(this, idClient.toString(), Toast.LENGTH_LONG).show()
            val name = intent.getStringExtra("name")
            nameEdit.setText(name)
            val tel  = intent.getStringExtra("tlf")
            telefoneEdit.setText(tel)
        }
        cancel.setOnClickListener{finish()}


        save.setOnClickListener{
            val person = DataPerson(
                "",
                nameEdit.text.toString().trim(),
                telefoneEdit.text.toString().trim()
            )
            db.addPerson(person)
            startActivity(Intent(this, ListClient::class.java))
            finish()
        }


        update.setOnClickListener{
            val person = DataPerson(
                idClient,
                nameEdit.text.toString().trim(),
                telefoneEdit.text.toString().trim()
            )
            db.updatePerson(person)
            startActivity(Intent(this, ListClient::class.java))
            finish()
        }







    }
}
