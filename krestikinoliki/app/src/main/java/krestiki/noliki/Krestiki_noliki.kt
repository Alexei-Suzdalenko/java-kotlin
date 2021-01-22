package krestiki.noliki
import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import tic.tac.toe.BoardView
import tic.tac.toe.GameEngine

class Krestiki_noliki : Activity() {
  //  var buttons = Array(3) { arrayOfNulls<Button>(3) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      val view = BoardView(this)
        setContentView(view)

  }


}



 // https://www.youtube.com/watch?v=Atec_6EUKns 13:05


































