package com.example.whereyou

import android.content.Intent
import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.speech.RecognizerIntent
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.whereyou.databinding.ActivityHomeBinding
import com.example.whereyou.services.LightSensorService
import com.example.whereyou.services.LocationService
import com.example.whereyou.services.MapEventServices
import com.example.whereyou.services.MapRenderingServices
import androidx.core.app.ActivityCompat
import com.example.whereyou.model.MyLocation
import com.example.whereyou.services.AccelerometerSensorService
import com.example.whereyou.services.MagneticFieldSensorService
import com.example.whereyou.services.NotificationService
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsResponse
import com.google.android.gms.location.SettingsClient
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.Task
import com.parse.ParseUser
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import java.util.Date
import java.util.Locale

class HomeActivity : AppCompatActivity(), LocationService.LocationUpdateListener {
    private val getSimplePermission= registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { }
    private lateinit var binding : ActivityHomeBinding
    private lateinit var locationService: LocationService
    private lateinit var lightSensorService: LightSensorService
    private lateinit var accelerometerSensorService: AccelerometerSensorService
    private lateinit var magneticFieldSensorService: MagneticFieldSensorService
    private lateinit var map: MapView
    private lateinit var mapRenderingService: MapRenderingServices
    private lateinit var mapEventService: MapEventServices

    private val locationSettings= registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()
    ) {
        if (it.resultCode == RESULT_OK) {
            locationService.startLocationUpdates()
        } else {
            Toast.makeText(this, "La localizacion esta desactivada", Toast.LENGTH_LONG).show()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val newIntentService = Intent(this, NotificationService::class.java)
        startService(newIntentService)
        locationService = LocationService(this, this)
        lightSensorService = LightSensorService(this)
        map= binding.mapa
        mapRenderingService= MapRenderingServices(this,map)
        lightSensorService.registerLightSensorListener {
            mapRenderingService.changeMapColors(it)
        }
        accelerometerSensorService = AccelerometerSensorService(this)
        accelerometerSensorService.registerAccelerometerSensorListener {
            changeSpeedStatus(it)
        }
        magneticFieldSensorService = MagneticFieldSensorService(this)
        magneticFieldSensorService.registerMagneticFieldSensorListener {
            binding.cardinalPoints.rotation = it
        }
        mapEventService= MapEventServices(map, mapRenderingService)
        mapEventService.createOverlayEvents()
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)){
                Toast.makeText(this, "Se necesita la ubicacion!!", Toast.LENGTH_LONG).show()
            }
            getSimplePermission.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
        locationService.locationClient.lastLocation.addOnSuccessListener {
            if(it!=null){
                val geo= GeoPoint(it.latitude, it.longitude)
                mapRenderingService.addMarker(geo, typeMarker = 'A')
                mapRenderingService.currentLocation= MyLocation(Date(System.currentTimeMillis()),GeoPoint(it.latitude, it.longitude))
                mapRenderingService.center(geo)
                updateUI(it)
            }else{
                Log.i("daniel", "Esta apagada la ubicacion")
                locationSettings()
            }
        }
		binding.HABorrar.setOnClickListener {
            binding.HABusquedaUbicacion.setText("")
        }

        binding.HAOpciones.setOnClickListener {

        }

        binding.HAMicrofono.setOnClickListener {
            voiceToText()
        }

        binding.HAUbicacion.setOnClickListener {
            
        }

        binding.HAGrupos.setOnClickListener {
            var intent = Intent(baseContext, GruposActivity::class.java)
            startActivity(intent)
        }

        binding.HAChats.setOnClickListener {
            val  intent = Intent(baseContext,ChatsActivity::class.java)
            startActivity(intent)
        }

        binding.HAHome.setOnClickListener {
            var intent = Intent(baseContext, HomeActivity::class.java)
            startActivity(intent)
        }

        binding.HAAlertas.setOnClickListener {
            var intent = Intent(baseContext, NotificacionesActivity::class.java)
            startActivity(intent)
        }

        binding.HAPerfil.setOnClickListener {
            startActivity(Intent(baseContext,PerfilActivity::class.java))
        }
    }

    private val startActivityForResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == Activity.RESULT_OK) {
            var result = it.data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            binding.HABusquedaUbicacion.setText(result!![0])
        }
    }
    private fun voiceToText(){
        var intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "¿Que dirección deseas buscar?")

        if(intent.resolveActivity(packageManager) !=null){
            startActivityForResult.launch(intent)
        }

    }
    private fun updateUI(location: Location){
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                Toast.makeText(this, "Se necesita la ubicacion por favor!", Toast.LENGTH_LONG)
                    .show()
            }
            getSimplePermission.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            return
        } else {
            val distancia = mapRenderingService.currentLocation.distance(GeoPoint(location.latitude, location.longitude))
            val ubicacion = mapRenderingService.findAddress(LatLng(location.latitude, location.longitude))
            Log.i("distancia", "la distancia es: $distancia eso")
            mapRenderingService.currentLocation.geoPoint= GeoPoint(location.latitude,location.longitude)
            mapRenderingService.addMarker(mapRenderingService.currentLocation.geoPoint, typeMarker = 'A')
            mapRenderingService.currentLocation.fecha=Date(System.currentTimeMillis())

            if(distancia>=30){
                mapRenderingService.center(mapRenderingService.currentLocation.geoPoint)
            }
            binding.HAUbicacion.text = ubicacion
        }
    }
    @Override
    override fun onPause(){
        super.onPause()
        map.onPause()
        lightSensorService.unregisterLightSensorListener()
        accelerometerSensorService.unregisterAccelerometerSensorListener()
        magneticFieldSensorService.unregisterMagneticFieldSensorListener()
        locationService.stopLocationUpdates()
    }
    override fun onResume() {
        super.onResume()
        map.onResume()
        map.controller.animateTo(mapRenderingService.currentLocation.geoPoint)
        lightSensorService.registerLightSensorListener {
            mapRenderingService.changeMapColors(it)
        }
        locationService.startLocationUpdates()
        accelerometerSensorService.registerAccelerometerSensorListener {
            changeSpeedStatus(it)
        }
        magneticFieldSensorService.registerMagneticFieldSensorListener {
            binding.cardinalPoints.rotation = it
        }
    }

    private fun locationSettings(){
        val builder= LocationSettingsRequest.Builder().addLocationRequest(locationService.locationRequest)
        val client: SettingsClient = LocationServices.getSettingsClient(this)
        val task: Task<LocationSettingsResponse> = client.checkLocationSettings(builder.build())

        task.addOnSuccessListener {
            locationService.startLocationUpdates()
        }

        task.addOnFailureListener{exception ->
            if(exception is ResolvableApiException){
                try {
                    val isr: IntentSenderRequest = IntentSenderRequest.Builder(exception.resolution).build()
                    locationSettings.launch(isr)
                }catch (sendEx: IntentSender.SendIntentException){
                    //eso!!
                }
            }else{
                Toast.makeText(this, "No hay hardware para el GPS", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onLocationUpdate(location: Location) {
        updateUI(location)
    }


    private fun changeSpeedStatus(speed: Float){ //Cambiarlo a tomar la velocidad y colocarla en la base de datos
        if(speed <= 10){
            binding.speedStatus.text = "ESTADO: Quieto"
            binding.speedStatus.setTextColor(getColor(R.color.verde))
        }
        if(speed > 10 && speed <= 14.7){
            binding.speedStatus.text = "ESTADO: Caminando"
            binding.speedStatus.setTextColor(getColor(R.color.amarilloVerdoso))
        }
        if(speed > 14.7 && speed <= 24.9){
            binding.speedStatus.text = "ESTADO: Trotando"
            binding.speedStatus.setTextColor(getColor(R.color.amarillo))
        }
        if(speed > 24.9 && speed <= 49){
            binding.speedStatus.text = "ESTADO: Corrieno"
            binding.speedStatus.setTextColor(getColor(R.color.naranja))
        }
        if(speed > 49){
            binding.speedStatus.text = "ESTADO: En un vehiculo"
            binding.speedStatus.setTextColor(getColor(R.color.rojo))
        }
    }
}