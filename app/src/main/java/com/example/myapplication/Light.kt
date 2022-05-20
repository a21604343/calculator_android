package com.example.myapplication

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import java.util.*

class Light private constructor(context: Context): SensorEventListener {

    private val TAG = Light::class.java.simpleName

    // Este objeto permite-nos aceder aos vários sensores
    private var sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager

    // Inicializa a captura de valores por parte do sensor
    private fun start() {
        sensorManager.registerListener(this,
            sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT), SensorManager.SENSOR_DELAY_NORMAL)
    }

    // Este método é invocado quando a precisão do sensor altera
    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        Log.i(TAG, "onAccuracyChanged")

    }

    // Este método é invocado quando o sensor recolhe uma nova amostra
    override fun onSensorChanged(event: SensorEvent) {
        Log.i(TAG, Arrays.toString(event.values))
    }

    companion object {

        private var listener: OnLightValueChangedListener? = null
        private var instance: Light? = null

        fun registerListener(listener: OnLightValueChangedListener) {
            this.listener = listener
        }

        fun unregisterListener() {
            listener = null
        }

        // Se tivermos vários listeners, temos de os notificar com um forEach
        fun notifyListener(event: SensorEvent) {
            listener?.onLightValueChanged(event.values)
        }

        fun start(context: Context) {
            instance = if(instance == null) Light(context) else instance
            instance?.start()
        }

    }

}