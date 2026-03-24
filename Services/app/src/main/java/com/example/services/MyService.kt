package com.example.services

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class MyService: Service() {

    val scope = CoroutineScope(Dispatchers.IO)

    override fun onCreate() {
        super.onCreate()
        log("onCreate")
    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val count = intent?.getIntExtra(COUNT_EXTRA,0) ?: 0
        log("onStartCommand")
        scope.launch {
            for (i in count until count+100) {
                delay(1000)
                log("Timer $i")
            }
        }
        return START_REDELIVER_INTENT
    }

    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
        log("onDestroy")
    }

    override fun onBind(intent: Intent?): IBinder {
        TODO("Not yet implemented")
    }

    private fun log(message: String) {
        Log.d("SERVICE_TAG", "MyService: $message")
    }

    companion object{

        const val COUNT_EXTRA = "count-extra"
        fun newIntent(context: Context,count:Int): Intent{
            val intent = Intent(context, MyService::class.java)
            intent.putExtra(COUNT_EXTRA,count)
            return intent
        }

    }
}
