package com.example.whereyou.services

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import kotlin.math.sqrt

class GyroscopeSensorService (context: Context){
    private var sensorManager: SensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private var gyroscopeSensor: Sensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)
    private var gyroscopeEventListener: SensorEventListener? = null

    fun registerGyroscopeSensorListener(listener:(rotation: Float)->Unit){
        gyroscopeEventListener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent?) {
                if (event != null && event.sensor.type == Sensor.TYPE_GYROSCOPE) {
                    val rotation = event.values[1]
                    //Se devuelve el resultado
                    listener(rotation)
                }

            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
                // Nada
            }
        }
        gyroscopeSensor.let {
                sensor -> sensorManager.registerListener(gyroscopeEventListener,sensor,
            SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    fun unregisterAccelerometerSensorListener(){
        gyroscopeEventListener?.let {
            sensorManager.unregisterListener(it)
            gyroscopeEventListener = null
        }
    }
}