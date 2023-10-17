package com.example.whereyou.services

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import kotlin.math.sqrt

class AccelerometerSensorService(context: Context) {
    private var sensorManager: SensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private var accelerometerSensor: Sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    private var accelerometerEventListener: SensorEventListener? = null

    fun registerAccelerometerSensorListener(listener:(speed: Float)->Unit){
        accelerometerEventListener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent?) {
                if (event != null && event.sensor.type == Sensor.TYPE_ACCELEROMETER) {
                    val ax = event.values[0] // Aceleración en el eje X
                    val ay = event.values[1] // Aceleración en el eje Y
                    val az = event.values[2] // Aceleración en el eje Z

                    // Calcular la aceleración total del dispsitivo
                    val aceleration = sqrt(ax * ax + ay * ay + az * az)
                    //Se devuelve el resultado
                    listener(aceleration)
                }

            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
                // Nada
            }
        }
        accelerometerSensor.let {
                sensor -> sensorManager.registerListener(accelerometerEventListener,sensor,SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    fun unregisterAccelerometerSensorListener(){
        accelerometerEventListener?.let {
            sensorManager.unregisterListener(it)
            accelerometerEventListener = null
        }
    }
}