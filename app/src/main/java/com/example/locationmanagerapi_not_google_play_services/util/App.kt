package com.example.locationmanagerapi_not_google_play_services.util

import android.app.Application
import android.location.Location

class App: Application() {
    companion object{
        var velocity = "0.0"
        var distance = 0
        var oldLocation: Location? = null

    }
}