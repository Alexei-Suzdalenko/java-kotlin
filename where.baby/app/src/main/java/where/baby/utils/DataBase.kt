package where.baby.utils
import com.google.firebase.database.FirebaseDatabase
object DataBase {

    val database by lazy{ FirebaseDatabase.getInstance().reference }


  //  mDatabase.child("users").child(userId).setValue(user)
    //  val database = FirebaseDatabase.getInstance()
 //  val myRef = database.getReference("message")
 //  myRef.setValue("Hello, World!")
}