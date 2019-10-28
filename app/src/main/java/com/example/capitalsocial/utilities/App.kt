package com.example.capitalsocial.utilities

import android.app.Application

class App: Application() {

    companion object {
        var prefs: Prefs? = null
    }

    override fun onCreate() {
        super.onCreate()
        prefs = Prefs(applicationContext)
    }

}