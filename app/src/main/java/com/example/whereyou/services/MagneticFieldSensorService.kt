package com.example.whereyou.services

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log

class MagneticFieldSensorService (context: Context){
    private var sensorManager: SensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private var magneticFieldSensor: Sensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)
    private var magneticFieldEventListener: SensorEventListener? = null
    private val azimuthValues = FloatArray(3)

    fun registerMagneticFieldSensorListener(listener:(rotation: Float)->Unit){
        magneticFieldEventListener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent?) {
                if (event != null && event.sensor.type == Sensor.TYPE_MAGNETIC_FIELD) {
                    // Obtén la matriz de rotación y la orientación
                    System.arraycopy(event.values, 0, azimuthValues, 0, 3)

                    val azimuth = azimuthValues[0]
                    var adjustedAzimuth = 0.0

                    // Asegurarse de que el ángulo esté en el rango de 0 a 180 grados
                    if(azimuth < 0){
                        adjustedAzimuth = ((azimuth - 180) % 180).toDouble()
                    }
                    else{
                        adjustedAzimuth = ((azimuth + 180) % 180).toDouble()
                    }
                    Log.i("MF", "Grados: $adjustedAzimuth")
                    // Devuelve la rotación en el eje Z encontrada
                    listener(adjustedAzimuth.toFloat())
                }

            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
                // Nada
            }
        }
        magneticFieldSensor.let {
                sensor -> sensorManager.registerListener(magneticFieldEventListener,sensor,SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    fun unregisterMagneticFieldSensorListener(){
        magneticFieldEventListener?.let {
            sensorManager.unregisterListener(it)
            magneticFieldEventListener = null
        }
    }
}