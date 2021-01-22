package lex.coder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_chat.*
import lex.coder.util.App
import lex.coder.util.App.Companion.myRef
import lex.coder.util.User
import lex.coder.util.UserAdaptor

class Chat : AppCompatActivity() {
    lateinit var listUsers: MutableList<User>
    lateinit var listNames : MutableList<String>
    var nameUser = ""
    var timeUser = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        listUsers = mutableListOf()
        listNames = mutableListOf()

        nameUser = intent.getStringExtra("name_user")?.toString() ?: ""
        timeUser = intent.getStringExtra("time_user")?.toString() ?: ""
        title = "Chat $nameUser"

        val postListener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val children = dataSnapshot.children
                listUsers.clear()
                children.forEach {
                    val u= it.getValue(User::class.java) as User
                    if (nameUser == u.name.toString() && timeUser ==  u.time.toString()) {
                        listUsers.add(u)
                    }
                }
               showMessages()
            }
        }
        myRef.addValueEventListener(postListener)

        save.setOnClickListener {
            val text = editText.text.toString()
            editText.setText("")
            if (text == "") return@setOnClickListener
            val user = User(nameUser, "admin", text, timeUser)
            val key = myRef.push().key.toString()
            myRef.child(key).setValue(user)
        }
    }

    fun showMessages(){
        Log.e("tag", "=== start show message === " )
        for(list in listUsers){
            Log.e("tag", "__> " + list.name)
        }
        listViewMessages.adapter = UserAdaptor(this, listUsers)
    }


}