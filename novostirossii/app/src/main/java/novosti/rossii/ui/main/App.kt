package novosti.rossii.ui.main
import android.app.Application

class App : Application() {
    companion object{
        var showMessage = true
        lateinit var todayList:      MutableList<Novosti_rossii_dataClass>
        lateinit var glavnoe:        MutableList<Novosti_rossii_dataClass>
        lateinit var lentaList:      MutableList<Novosti_rossii_dataClass>
        lateinit var blogList:       MutableList<Novosti_rossii_dataClass>
        lateinit var mnenieList:     MutableList<Novosti_rossii_dataClass>
        lateinit var statiiList:     MutableList<Novosti_rossii_dataClass>
        lateinit var politicaList:   MutableList<Novosti_rossii_dataClass>
        lateinit var objestvoList:   MutableList<Novosti_rossii_dataClass>
        lateinit var youtubeList:    MutableList<Novosti_rossii_dataClass>
    }

    override fun onCreate() {
        super.onCreate()
        todayList = arrayListOf()
        glavnoe = arrayListOf()
        lentaList = arrayListOf()
        blogList = arrayListOf()
        mnenieList = arrayListOf()
        statiiList = arrayListOf()
        politicaList = arrayListOf()
        objestvoList = arrayListOf()
        youtubeList = arrayListOf()

        showMessage = true
    }
}