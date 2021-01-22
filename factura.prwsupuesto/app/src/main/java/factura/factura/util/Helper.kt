package factura.factura.util
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
internal object Helper {
    fun getListViewSize(myListView: ListView) {
        val myListAdapter = myListView.adapter ?: return

        var totalHeight = 0
        for (size in 0 until myListAdapter.count) {
            val listItem: View = myListAdapter.getView(size, null, myListView)
            listItem.measure(0, 0)
            totalHeight += listItem.measuredHeight
            Log.d("tag", "height of listItem: = $listItem.measuredHeight")
        }

        val params: ViewGroup.LayoutParams = myListView.layoutParams
        params.height = totalHeight  + myListView.dividerHeight * (myListAdapter.count - 1)
        myListView.layoutParams = params

       // Log.d("tag", "height of listItem: = $totalHeight")
    }
}