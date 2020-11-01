package com.example.lab3

import android.app.Application
import tw.ktrssreader.config.readerGlobalConfig

class RssApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        readerGlobalConfig {
            setApplicationContext(this@RssApplication)
            enableLog = true
        }
    }
}