package tic.tac.toe
import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.view.View
import krestiki.noliki.Krestiki_noliki

class BoardView(c: Context) : View(c) {
    init {
        val LINE_THINSK        = 5
        val ELT_MARGIN         = 20
        val ELT_STROKE_WIDTH   = 15
        val gridPaint          : Paint = Paint()
        val oPaint             : Paint = Paint(Paint.ANTI_ALIAS_FLAG)
            oPaint.color       = Color.RED
            oPaint.style       = Paint.Style.STROKE
            oPaint.strokeWidth = ELT_STROKE_WIDTH.toFloat()
        val xPaint             : Paint = Paint()
        var width              = 0
        var height             = 0
        var eltW               = 0
        val eltH               = 0
        lateinit var activity  : Krestiki_noliki
    }
}