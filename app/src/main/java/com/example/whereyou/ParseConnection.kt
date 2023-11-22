package com.example.whereyou

import android.app.Application
import com.parse.Parse
class ParseConnection : Application() {
    override fun onCreate() {
        super.onCreate()
        Parse.initialize(
            Parse.Configuration.Builder(this)
                .applicationId("ParseAppId")
                .clientKey("") // should correspond to Application Id env variable
                .server(IP_GCP)
                .build()
        )
    }

    companion object {
        const val IP_GCP = "http://34.168.219.6:1337/parse"
    }
}