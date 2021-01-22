package factura.factura.util
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable

object BorderBottom {

    fun borderBottomGo( bgColor: Int,  borderColor: Int,  left: Int,  top: Int,  right: Int,  bottom: Int) : LayerDrawable? {

        val borderColorDrawable = ColorDrawable(borderColor)
        val backgroundColorDrawable = ColorDrawable(bgColor)

        val drawables = arrayOf<Drawable>(borderColorDrawable, backgroundColorDrawable)

        val layerDrawable = LayerDrawable(drawables)

        layerDrawable.setLayerInset(1, left, top, right, bottom)

    return layerDrawable
    }

}