package novosti.rossii.ui.main
import android.util.Log
import org.json.JSONArray
import org.json.XML
import org.jsoup.Jsoup
import novosti.rossii.ui.main.App.Companion.blogList
import novosti.rossii.ui.main.App.Companion.glavnoe
import novosti.rossii.ui.main.App.Companion.lentaList
import novosti.rossii.ui.main.App.Companion.mnenieList
import novosti.rossii.ui.main.App.Companion.objestvoList
import novosti.rossii.ui.main.App.Companion.politicaList
import novosti.rossii.ui.main.App.Companion.statiiList
import novosti.rossii.ui.main.App.Companion.todayList
import novosti.rossii.ui.main.App.Companion.youtubeList
import java.net.URL

object NewsUtil {

    fun clear(){
        todayList.clear()
        glavnoe.clear()
        lentaList.clear()
        blogList.clear()
        mnenieList.clear()
        statiiList.clear()
        politicaList.clear()
        objestvoList.clear()
        youtubeList.clear()
    }

    fun totalUpdate(){
       // clear()
     //  val thread1 = Thread{
     //      try{
     //          getTop()
     //      } catch (e:Exception){
     //          Log.d("tag", "Error tu NewsUtil totalUpdate function1 " + e.message)
     //      }
     //  }
     //  thread1.start()

        val thread2 = Thread{
            try{
                getGlavnoe()
            } catch (e:Exception){
                Log.d("tag", "Error tu NewsUtil totalUpdate function2 " + e.message)
            }
        }
        thread2.start()

        val thread3 = Thread{
            try{
                getLenta()
            } catch (e:Exception){
                Log.d("tag", "Error tu NewsUtil totalUpdate function3 " + e.message)
            }
        }
        thread3.start()

        val thread4 = Thread{
            try{
                getBlog()
            } catch (e:Exception){
                Log.d("tag", "Error tu NewsUtil totalUpdate function4 " + e.message)
            }
        }
        thread4.start()

        val threa5 = Thread{
            try{
                getMnenie()
            } catch (e:Exception){
                Log.d("tag", "Error tu NewsUtil totalUpdate function5 " + e.message)
            }
        }
        threa5.start()

        val thread6 = Thread{
            try{
                getStatii()
            } catch (e:Exception){
                Log.d("tag", "Error tu NewsUtil totalUpdate function6 " + e.message)
            }
        }
        thread6.start()

        val thread7 = Thread{
            try{
                getPolitica()
            } catch (e:Exception){
                Log.d("tag", "Error tu NewsUtil totalUpdate function7 " + e.message)
            }
        }
        thread7.start()

        val thread8 = Thread{
            try{
                getYoutube()
            } catch (e:Exception){
                Log.d("tag", "Error tu NewsUtil totalUpdate function8 " + e.message)
            }
        }
        thread8.start()
    }



    fun getTop() {
        val url = "https://grani-ru-org.appspot.com"
        if (todayList.isEmpty()) {
            val doc = Jsoup.connect(url).get()
            var text = doc.select("div[class=\"one top\"]")
            var h1 = text.select("h3").first().text()
            var a = text.select("a").first().attr("href")
            a = url + a
            var desc = text.select("a").select("p").last().text()
            var img =
                text.select("a").select("div[class=\"has left\"]").select("img").first().attr("src")
            img = "https://grani-ru-org.appspot.com$img"
            //  img = img.replace("//", "https://")

           todayList.clear()
           todayList.add(Novosti_rossii_dataClass(h1, desc, a, img, "graniMnenie"))
            text = doc.select("div[class=\"fp-main\"]").select("div[class=\"one\"]")
            for (t in text) {
                h1 = t.select("h4").text()
                desc = t.select("p").last().text()
                a = t.select("a").first().attr("href")
                a = url + a
                img = t.select("img").first().attr("src")
                img = "https://grani-ru-org.appspot.com$img"
                //    img = img.replace("//", "https://")
                todayList.add(Novosti_rossii_dataClass(h1, desc, a, img, "graniMnenie"))
            }
        }
    }


    fun getGlavnoe(){
        val url = "http://www.site101.mir915bcf08b.comcb.info/rss"
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
                App.glavnoe.add(Novosti_rossii_dataClass(title, des, link, img, "kasparovBlog"))
            }
        }
    }

    fun getLenta(){
        val url = "https://grani-ru-org.appspot.com/export/news-rss2.xml"
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
    }


    fun getBlog(){
        val url = "http://www.site101.mir915bcf08b.comcb.info"
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
    }


    fun getMnenie(){
        val url = "https://grani-ru-org.appspot.com/export/articles-rss2.xml"
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
    }


    fun getStatii(){
        val url = "http://www.site101.mir915bcf08b.comcb.info"
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
    }


    fun getPolitica(){
        val url = "http://www.site101.mir915bcf08b.comcb.info/rss/policy.xml"
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
    }


    fun getYoutube(){
        val urlFromGithub = JSONArray(URL("https://diseno-desarrollo-web-app-cantabria.github.io/novosti_rossii/channels1.js").readText())
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
                    var url = item.getString("yt:videoId")
                    url = "https://www.youtube.com/embed/$url"
                    val img = item.getJSONObject("media:group").getJSONObject("media:thumbnail").getString("url")
                    youtubeList.add(Novosti_rossii_dataClass(title, "", url, img, "youTube"))
                }
            }
        }
    }









}