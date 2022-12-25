package com.example.lab13_k

import android.app.Service
import android.content.Intent
import android.os.Bundle
import android.os.IBinder

class MyService : Service() {

    var flag = false
    private var h = 0
    private var m = 0
    private var s = 0

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        flag = intent.getBooleanExtra("flag", false)
        Thread {
            while (flag) {
                try {
                    Thread.sleep(1000)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
                s++
                if (s >= 60) {
                    s = 0
                    m++
                    if (m >= 60) {
                        m = 0
                        h++
                    }
                }
                val i = Intent("MyMessage")
                val bundle = Bundle()
                bundle.putInt("H", h)
                bundle.putInt("M", m)
                bundle.putInt("S", s)
                i.putExtras(bundle)
                sendBroadcast(i)
            }
        }.start()
        return START_STICKY
    }
    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }
}