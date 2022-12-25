package com.example.lab13_k

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class MainActivity : AppCompatActivity() {
    private var tv_clock: TextView? = null
    private var btn_start: Button? = null
    private var flag = false
    private val receiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val b = intent.extras
            tv_clock!!.text = String.format(
                Locale.getDefault(),
                "%02d:%02d:%02d",
                b!!.getInt("H"),
                b.getInt("M"),
                b.getInt("S")
            )
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tv_clock = findViewById(R.id.tv_clock)
        btn_start = findViewById(R.id.btn_start)
        registerReceiver(receiver, IntentFilter("MyMessage"))

        if (flag)
            btn_start?.text = "暫停"
        else
            btn_start?.text = "開始"

        btn_start?.setOnClickListener(View.OnClickListener { v: View? ->
            flag = !flag
            if (flag) {
                btn_start?.text = "暫停"
                Toast.makeText(this, "計時開始", Toast.LENGTH_SHORT).show()
            } else {
                btn_start?.text = "開始"
                Toast.makeText(this, "計時開始", Toast.LENGTH_SHORT).show()
            }
            startService(Intent(this, MyService::class.java).putExtra("flag", flag))
        })
    }


    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver)
    }
}