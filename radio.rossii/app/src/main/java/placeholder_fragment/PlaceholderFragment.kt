package placeholder_fragment
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import radiorossii.radio.rossii.Grani_novosti_rossii
import radiorossii.radio.rossii.R
import kotlinx.android.synthetic.main.fragment_main.*
import org.json.JSONArray
import org.json.XML
import org.jsoup.Jsoup
import radio.App.Companion.blogList
import radio.App.Companion.glavnoe
import radio.App.Companion.lentaList
import radio.App.Companion.mnenieList
import radio.App.Companion.politicaList
import radio.App.Companion.statiiList
import radio.App.Companion.todayList
import radio.App.Companion.youtubeList
import java.net.URL
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
        val index    = arguments?.getInt(ARG_SECTION_NUMBER) ?: 1
        val root     = inflater.inflate(R.layout.fragment_main, container, false)
        val listView = root.findViewById<ListView>(R.id.listView)
        when(index){
            1 -> {
                val top = Thread{
                    val url = "https://grani-ru-org.appspot.com"
                    try {
                        if(todayList.isEmpty()){
                            val doc = Jsoup.connect(url).get()
                            var text = doc.select("div[class=\"one top\"]")
                            var h1 = text.select("h3").first().text()
                            var a = text.select("a").first().attr("href")
                            a = url + a
                            var desc = text.select("a").select("p").last().text()
                            var img = text.select("a").select("div[class=\"has left\"]").select("img").first().attr("src")
                            img = "https://grani-ru-org.appspot.com$img"
                            // img = img.replace("//", "https://")
                            todayList.clear()
                            todayList.add(Novosti_rossii_dataClass(h1, desc, a, img, "graniMnenie"))
                            text = doc.select("div[class=\"fp-main\"]").select("div[class=\"one\"]")
                            for (t in text){
                                h1 = t.select("h4").text()
                                desc = t.select("p").last().text()
                                a = t.select("a").first().attr("href")
                                a = url + a
                                img = t.select("img").first().attr("src")
                                img = "https://grani-ru-org.appspot.com$img"
                                Log.d("tag", "img = " + img)
                                todayList.add(Novosti_rossii_dataClass(h1, desc, a, img, "graniMnenie"))
                            }
                        }
                        listView.post {
                            hideprogressBar()
                            listView.adapter = RvAdapter (requireContext() ,todayList)
                            clickEvent(todayList)
                        }
                    } catch (e: Exception) { Log.d("tag", "error = " + e.message) }
                }
                top.start()
            }
         2 -> {
             val url = "http://www.site101.mir915bcf08b.comcb.info/rss"
             val glavn = Thread{
                 try{
                     if(glavnoe.isEmpty()){
                         val xml = URL(url).readText()
                         val xml2 = XML.toJSONObject(xml)
                         val xml3 = xml2.getJSONObject("rss")
                         val xml4 = xml3.getJSONObject("channel")
                         val xml5 = xml4.getString("item")
                         val xml6 = JSONArray(xml5)
                         glavnoe.clear()
                         for (i in 0 until xml6.length()) {
                             val item = xml6.getJSONObject(i)
                             var img = item.getJSONObject("enclosure").getString("url")
                             img = img.replace("https://kasparov.ru", "http://www.site101.mir915bcf08b.combc.info")
                             val title = item.getString("title")
                             var link = item.getString("link")
                             link = link.replace("https://kasparov.ru",  "http://www.site101.mir915bcf08b.combc.info")
                             val des = item.getString("description")
                             glavnoe.add(Novosti_rossii_dataClass(title, des, link, img, "kasparovBlog"))
                         }
                     }
                     listView.post {
                         hideprogressBar()
                         listView.adapter = RvAdapter(requireContext() , glavnoe)
                         clickEvent(glavnoe)
                     }
                 } catch (e: java.lang.Exception){Log.d("tag", "" + e.message)}
             }
             glavn.start()
         }
         3 -> {
             val lenta = Thread{
                 val url = "https://grani-ru-org.appspot.com/export/news-rss2.xml"
                 try {
                     if(lentaList.isEmpty()){
                         val xml = URL(url).readText()
                         val xml2 = XML.toJSONObject(xml)
                         val xml3 = xml2.getJSONObject("rss")
                         val xml4 = xml3.getJSONObject("channel")
                         val xml5 = xml4.getString("item")
                         val xml6 = JSONArray(xml5)
                         lentaList.clear()
                         for (i in 0 until xml6.length()) {
                             val item = xml6.getJSONObject(i)
                             var img = item.getJSONObject("enclosure").getString("url")
                             img = img.replace("graniru.org", "grani-ru-org.appspot.com")
                             val title = item.getString("title")
                             var link = item.getString("link")
                             link = link.replace("graniru.org",  "grani-ru-org.appspot.com")
                             val des = item.getString("description")
                             lentaList.add(Novosti_rossii_dataClass(title, des, link, img, "graniMnenie"))
                         }
                     }
                     listView.post {
                         hideprogressBar()
                         listView.adapter = RvAdapter (requireContext() , lentaList)
                         clickEvent(lentaList)
                     }
                 } catch (e: Exception) { Log.d("tag", "error = " + e.message) }
             }
             lenta.start()
         }
         4 -> {
             val blog = Thread{
                 val url = "http://www.site101.mir915bcf08b.comcb.info"
                 try {
                     if(blogList.isEmpty()){
                         val doc = Jsoup.connect(url).get()
                         val text = doc.select("div[class=\"boxContainer tableCell1\"]")
                         for (t in text){
                             val h1 = t.select("div[class=\"title\"]").text()
                             val desc = t.select("div[class=\"articleItem\"]").text()
                             var a = t.select("a").first().attr("href")
                             a = url + a
                             var img = t.select("img").attr("src")
                             img = url + img
                             blogList.add(Novosti_rossii_dataClass(h1, desc, a, img, "kasparovBlog"))
                         }
                     }
                     listView.post {
                         hideprogressBar()
                         listView.adapter = RvAdapter (requireContext() , blogList)
                         clickEvent(blogList)
                     }
                 } catch (e: Exception) { Log.d("tag", "error = " + e.message) }
             }
             blog.start()
         }
         5 -> {
             val mnenie = Thread{
                 val url = "https://grani-ru-org.appspot.com/export/articles-rss2.xml"
                 try {
                     if(mnenieList.isEmpty()){
                         val xml = URL(url).readText()
                         val xml2 = XML.toJSONObject(xml)
                         val xml3 = xml2.getJSONObject("rss")
                         val xml4 = xml3.getJSONObject("channel")
                         val xml5 = xml4.getString("item")
                         val xml6 = JSONArray(xml5)
                         mnenieList.clear()
                         for (i in 0 until xml6.length()) {
                             val item = xml6.getJSONObject(i)
                             var img = item.getJSONObject("enclosure").getString("url")
                             img = img.replace("graniru.org", "grani-ru-org.appspot.com")
                             val title = item.getString("title")
                             var link = item.getString("link")
                             link = link.replace("graniru.org",  "grani-ru-org.appspot.com")
                             val des = item.getString("description")
                             mnenieList.add(Novosti_rossii_dataClass(title, des, link, img, "graniMnenie"))
                         }
                     }
                     listView.post {
                         hideprogressBar()
                         listView.adapter = RvAdapter (requireContext() ,mnenieList)
                         clickEvent(mnenieList)
                     }
                 } catch (e: Exception) { Log.d("tag", "error = " + e.message) }
             }
             mnenie.start()
         }
         6 -> {
             val statii = Thread{
                 val url = "http://www.site101.mir915bcf08b.comcb.info"
                 try {
                     if(statiiList.isEmpty()){
                         val doc = Jsoup.connect(url).get()
                         val text = doc.select("div[class=\"boxContainer tableCell2\"]")
                         for (t in text){
                             val h1 = t.select("h2").text()
                             val desc = t.select("div[class=\"atext lgtxt\"]").text()
                             var a = t.select("a").first().attr("href")
                             a = url + a
                             var img = t.select("img").last().attr("src")
                             img = url + img
                             statiiList.add(Novosti_rossii_dataClass(h1, desc, a, img, "kasparovBlog"))
                         }
                     }
                     listView.post {
                         hideprogressBar()
                         listView.adapter = RvAdapter (requireContext() ,  statiiList)
                         clickEvent(statiiList)
                     }
                 } catch (e: Exception) { Log.d("tag", "error 6 = " + e.message) }
             }
             statii.start()
         }
         7 -> {
             val url = "http://www.site101.mir915bcf08b.comcb.info/rss/policy.xml"
             val politica = Thread{
                 try{
                     if(politicaList.isEmpty()){
                         val xml = URL(url).readText()
                         val xml2 = XML.toJSONObject(xml)
                         val xml3 = xml2.getJSONObject("rss")
                         val xml4 = xml3.getJSONObject("channel")
                         val xml5 = xml4.getString("item")
                         val xml6 = JSONArray(xml5)
                         politicaList.clear()
                         for (i in 0 until xml6.length()) {
                             val item = xml6.getJSONObject(i)
                             var img = item.getJSONObject("enclosure").getString("url")
                             img = img.replace("https://kasparov.ru", "http://www.site101.mir915bcf08b.combc.info")
                             val title = item.getString("title")
                             var link = item.getString("link")
                             link = link.replace("https://kasparov.ru",  "http://www.site101.mir915bcf08b.combc.info")
                             val des = item.getString("description")
                             politicaList.add(Novosti_rossii_dataClass(title, des, link, img, "kasparovBlog"))
                         }
                     }
                     listView.post {
                         hideprogressBar()
                         listView.adapter = RvAdapter(requireContext() , politicaList)
                         clickEvent(politicaList)
                     }
                 } catch (e: java.lang.Exception){Log.d("tag", "error 7" + e.message)}
             }
             politica.start()
         }
         8 -> {
             val youTube = Thread{
                 try{
                     val urlFromGithub = JSONArray(URL("https://diseno-desarrollo-web-app-cantabria.github.io/radio.rossii/youtube.js").readText())
                     if(youtubeList.isEmpty()){
                         youtubeList.clear()
                         for(i in 0 until urlFromGithub.length()){
                             val xml = URL(urlFromGithub.getString(i)).readText()
                             val xml2 = XML.toJSONObject(xml)
                             val xml3 = xml2.getJSONObject("feed")
                             val xml4 = xml3.getString("entry")
                             val xml5 = JSONArray(xml4)
                             for (i in 0 until 3) {
                                 val item = xml5.getJSONObject(i)
                                 val title = item.getString("title")
                                 Log.d("tag", "title " + title)
                                 var url = item.getString("yt:videoId")
                                 url = "https://www.youtube.com/embed/$url"
                                 Log.d("tag", "title " + url)
                                 val img = item.getJSONObject("media:group").getJSONObject("media:thumbnail").getString("url")
                                 Log.d("tag", "img " + img)
                                 youtubeList.add(Novosti_rossii_dataClass(title, "", url, img, "youTube"))
                             }
                         }
                     }
                     listView.post {
                         hideprogressBar()
                         listView.adapter = RvAdapter(requireContext() , youtubeList)
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
       listView.setOnItemClickListener{  _ , _ , position, _ ->
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
       }
   }

   private fun hideprogressBar(){
        progressBarFirst.visibility = View.GONE
    }
}