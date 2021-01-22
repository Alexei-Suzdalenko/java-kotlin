package lex.coder
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_main.*
import lex.coder.util.App
import lex.coder.util.App.Companion.myRef
import lex.coder.util.Serv
import lex.coder.util.User
import lex.coder.util.UserNameAdaptor


class MainActivity : AppCompatActivity() {
    lateinit var listUsers: MutableList<User>
    lateinit var listTotal : MutableList<String>
    lateinit var listNames : MutableList<String>
    lateinit var listTime : MutableList<String>
    lateinit var c: Context
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ContextCompat.startForegroundService(this, Intent(this, Serv::class.java))

        // myRef.setValue("Hello, World!")
        c = application
        listTotal = mutableListOf()
        listUsers = mutableListOf()
        listNames = mutableListOf()
        listTime = mutableListOf()

       val postListener = object : ValueEventListener {
           override fun onCancelled(p0: DatabaseError) {}
           override fun onDataChange(dataSnapshot: DataSnapshot) {
               // Get Post object and use the values to update the UI
               listTotal.clear()
               listNames.clear()
               listTime.clear()
               listUsers.clear()
               for ( messageSnapshot in dataSnapshot.children){
                   val user = messageSnapshot.getValue(User::class.java) ?: return
                   listUsers.add(user)
               }
               getOnlyNameDiferentUser()
               App.playSound(c)
           }
       }
       myRef.addValueEventListener(postListener)

       reset.setOnClickListener {
           myRef.setValue(null)
       }
    }





    fun getOnlyNameDiferentUser(){

        for (user in listUsers){
            Log.e("tag", " listNames == " + user.toString() )

            val total = user.name+"___"+user.time
            val match = listTotal.filter { it in total }
            if(match.isEmpty()){
                     listTotal.add(total)
                     listNames.add(user.name)
                     listTime.add(user.time)
                 }

        }
        Log.e("tag", " listNames == " + listNames.toString() )



        listView.adapter = UserNameAdaptor(this, listTotal)

        listView.setOnItemClickListener { parent, view, position, id ->

            val i = Intent(this, Chat::class.java)
            i.apply {
                putExtra("name_user", listNames[position])
                putExtra("time_user", listTime[position])
            }
            startActivity(i)
        }
    }
}
