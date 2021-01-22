package novosti.rossii.ui.main
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_main.*
import novosti.rossii.Grani_novosti_rossii
import novosti.rossii.R
import novosti.rossii.ui.main.App.Companion.blogList
import novosti.rossii.ui.main.App.Companion.glavnoe
import novosti.rossii.ui.main.App.Companion.lentaList
import novosti.rossii.ui.main.App.Companion.mnenieList
import novosti.rossii.ui.main.App.Companion.politicaList
import novosti.rossii.ui.main.App.Companion.statiiList
import novosti.rossii.ui.main.App.Companion.todayList
import novosti.rossii.ui.main.App.Companion.youtubeList


class PlaceholderFragment : Fragment() {
    companion object {
        private const val ARG_SECTION_NUMBER = "novosti rossii"
        @JvmStatic
        fun newInstance(sectionNumber: Int): PlaceholderFragment {
            return PlaceholderFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }

    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val index = arguments?.getInt(ARG_SECTION_NUMBER) ?: 1
        val root = inflater.inflate(R.layout.fragment_main, container, false)
        val rv = root.findViewById<RecyclerView>(R.id.rv)
        val llm = LinearLayoutManager(requireContext())
            rv.layoutManager = llm
            rv.setHasFixedSize(true)
       // Log.d("tag", "index = " + index)

        when(index){
            1 -> {
                val top = Thread{
                    try {
                        if(todayList.isEmpty()) {
                            Log.d("tag", "start for PlaceholderFragment 1") ; NewsUtil.getTop()
                        }
                        rv.post {
                            hideprogressBar()
                            val adapter = RvAdapter (todayList)
                            rv.adapter = adapter
                            clickEvent(todayList)
                        }
                    } catch (e: Exception) { Log.d("tag", "error = " + e.message) }
                }
                top.start()
            }
            2 -> {
                val glavn = Thread{
                    try{
                       if(glavnoe.isEmpty()) {
                           Log.d("tag", "start for PlaceholderFragment 2"); NewsUtil.getGlavnoe()
                       }
                        rv.post {
                            hideprogressBar()
                            val adapter = RvAdapter(glavnoe)
                            rv.adapter = adapter
                            clickEvent(glavnoe)
                        }
                    } catch (e: java.lang.Exception){Log.d("tag", "" + e.message)}
                }
                glavn.start()
            }
            3 -> {
                val lenta = Thread{
                    try {
                    if(lentaList.isEmpty()) NewsUtil.getLenta()
                        rv.post {
                            hideprogressBar()
                            val adapter = RvAdapter (lentaList)
                            rv.adapter = adapter
                            clickEvent(lentaList)
                        }
                    } catch (e: Exception) { Log.d("tag", "error = " + e.message) }
                }
                lenta.start()
            }
            4 -> {
                val blog = Thread{
                    try {
                        if(blogList.isEmpty()) {
                            Log.d("tag", "start for PlaceholderFragment 3");
                            NewsUtil.getBlog()
                        }
                        rv.post {
                            hideprogressBar()
                            val adapter = RvAdapter (blogList)
                            rv.adapter = adapter
                            clickEvent(blogList)
                        }
                    } catch (e: Exception) { Log.d("tag", "error = " + e.message) }
                }
                blog.start()
            }
            5 -> {
                val mnenie = Thread{
                    try {
                     NewsUtil.getMnenie()
                        rv.post {
                            hideprogressBar()
                            val adapter = RvAdapter (mnenieList)
                            rv.adapter = adapter
                            clickEvent(mnenieList)
                        }
                    } catch (e: Exception) { Log.d("tag", "error = " + e.message) }
                }
                mnenie.start()
            }
            6 -> {
                val statii = Thread{
                    try {
                      NewsUtil.getStatii()
                        rv.post {
                            hideprogressBar()
                            val adapter = RvAdapter (statiiList)
                            rv.adapter = adapter
                            clickEvent(statiiList)
                        }
                    } catch (e: Exception) { Log.d("tag", "error 6 = " + e.message) }
                }
                statii.start()
            }
            7 -> {
                val politica = Thread{
                    try{
                       NewsUtil.getPolitica()
                        rv.post {
                            hideprogressBar()
                            val adapter = RvAdapter(politicaList)
                            rv.adapter = adapter
                            clickEvent(politicaList)
                        }
                    } catch (e: java.lang.Exception){Log.d("tag", "error 7" + e.message)}
                }
                politica.start()
            }
            8 -> {
                val youTube = Thread{
                    try{
                       NewsUtil.getYoutube()
                        rv.post {
                            hideprogressBar()
                            val adapter = RvAdapter(youtubeList)
                            rv.adapter = adapter
                            clickEvent(youtubeList)
                        }
                    } catch (e: Exception){
                        Log.d("tag", "Error => " + e.message)}
                }
                val sharedPreferences = activity!!.getSharedPreferences("news", Context.MODE_PRIVATE)
                val keyToEnableNewsYoutube = sharedPreferences.getString("key", "disabled youtube").toString()
                if (keyToEnableNewsYoutube == "yes") youTube.start()
            }
        }
        return root
    }

   private fun clickEvent(novosti_rossii: MutableList<Novosti_rossii_dataClass>){
       rv.addOnItemTouchListener(RecyclerTouchListener(requireContext(), rv, object : RecyclerTouchListener.ClickListener {
           override fun onClick(view: View?, position: Int) {
               val title = novosti_rossii[position].title
               val img   = novosti_rossii[position].photoId
               val link  = novosti_rossii[position].link
               val page  = novosti_rossii[position].page
               val i = Intent(requireContext(), Grani_novosti_rossii::class.java)
                i.putExtra("page", page)
                i.putExtra("title", title)
                i.putExtra("img", img)
                i.putExtra("link", link)
               startActivity(i)
           }}))
   }

   private fun hideprogressBar(){
        progressBarFirst.visibility = View.GONE
    }
}