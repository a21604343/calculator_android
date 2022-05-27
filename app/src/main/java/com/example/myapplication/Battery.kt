package com.example.myapplication
import android.os.Handler
import android.content.Context
import android.os.BatteryManager
import android.os.Looper
import android.util.Log

class Battery private constructor(private val context : Context) : Runnable{

    private val TAG = Battery::class.java.simpleName

    private fun start(){
        handler.postDelayed(this,TIME_BETWEEN_UPDATES)
    }

    private fun getBatteryCurrentNow() : Double {
        val manager = context.getSystemService(Context.BATTERY_SERVICE) as BatteryManager

        val value = manager.getIntProperty(
            BatteryManager.BATTERY_PROPERTY_CAPACITY
            //BatteryManager.BATTERY_PROPERTY_CURRENT_NOW
        )

        return if (value != 0 && value != Int.MIN_VALUE)
            value.toDouble() / 10000000 else 0.0
    }

    override fun run() {
        val current = getBatteryCurrentNow()
        Log.i(TAG,current.toString())

        handler.postDelayed(this,TIME_BETWEEN_UPDATES)
    }

    private val TIME_BETWEEN_UPDATES = 20*1000L


    companion object{

        private var instance : Battery? = null
        private val handler = Handler(Looper.getMainLooper())

        fun start(context: Context){
            instance = if(instance == null) Battery(context) else instance
            instance?.start()
        }

    }
}