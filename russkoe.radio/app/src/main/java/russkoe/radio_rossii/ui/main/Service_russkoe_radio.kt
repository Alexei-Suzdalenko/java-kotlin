package russkoe.radio_rossii.ui.main

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.widget.Toast

import read.novosti.rossii.App

import read.novosti.rossii.App.Companion.nameStationShow
import read.novosti.rossii.App.Companion.playExoplayer
import read.novosti.rossii.App.Companion.url


class Service_russkoe_radio: Service() {


    override fun onBind(intent: Intent?): IBinder? {
        return null
    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        startForeground(111, App.news)
        Toast.makeText(this, nameStationShow, Toast.LENGTH_LONG).show()
        playExoplayer(url, this)
        return START_STICKY
    }



}