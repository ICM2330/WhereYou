package com.example.whereyou.services

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager

class MagneticFieldSensorService (context: Context){
    private var sensorManager: SensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private var magneticFieldSensor: Sensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)
    private var magneticFieldEventListener: SensorEventListener? = null
    private val rotationMatrix = FloatArray(9)
    private val orientationValues = FloatArray(3)

    fun registerMagneticFieldSensorListener(listener:(rotation: Float)->Unit){
        magneticFieldEventListener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent?) {
                if (event != null && event.sensor.type == Sensor.TYPE_MAGNETIC_FIELD) {
                    // Obtén la matriz de rotación y la orientación
                    SensorManager.getRotationMatrix(rotationMatrix, null, event.values, event.values)

                    // Convierte el ángulo en radianes y luego a grados
                    val azimuthInRadians = SensorManager.getOrientation(rotationMatrix, orientationValues)[0]
                    val azimuthInDegrees = Math.toDegrees(azimuthInRadians.toDouble()).toFloat()

                    // Devuelve la rotación en el eje Z encontrada
                    listener(-azimuthInDegrees)
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