package com.hexg.guideview

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.Intent
import io.realm.Realm

@SuppressLint("StaticFieldLeak")
class App : Application() {
    @Volatile
    lateinit var CONTEXT: Context

    //Record the time the app was first started. We need this for debugging
    //use raw api and not XTIME api so we don't cause app startup delays
    val STARTUP_TIME = System.currentTimeMillis() / 1000

    override fun attachBaseContext(ctx: Context) {
        try {
            //NOTE App.INSTANCE is not READY here yet...not sure why
            //we need context asap for Pref class dependencies which loads XDb
            //App.INSTANCE.CONTEXT is ready BEFORE App.INSTANCE so we should use this as much as possible in place of App.INSTANCE
            CONTEXT = ctx
            INSTANCE = this

        } catch (e: Throwable) {
            e.printStackTrace()
        } finally {
            super.attachBaseContext(CONTEXT)
        }
    }

    override fun onCreate() {
        super.onCreate()

        // TODO init realm need work in DB thread
        Realm.init(INSTANCE.CONTEXT)

        //
    }

    companion object {
        @Volatile
        lateinit var INSTANCE: App
        //check if device has app that can install/upgrade software such as GooglePlay
        //we detect if there is anyone that can handle "market://" url intent
        val DEVICE_HAS_MARKET_APP by lazy {
            Intent(Intent.ACTION_VIEW).resolveActivity(INSTANCE.packageManager) != null
        }
    }
}