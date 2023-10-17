package com.example.whereyou

import android.content.Intent
import android.Manifest
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
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
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsResponse
import com.google.android.gms.location.SettingsClient
import com.google.android.gms.tasks.Task
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import java.util.Date

class HomeActivity : AppCompatActivity(), LocationService.LocationUpdateListener {
    private val getSimplePermission= registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { }
    private lateinit var binding : ActivityHomeBinding
    private lateinit var locationService: LocationService
    private lateinit var lightSensorService: LightSensorService
    private lateinit var accelerometerSensorService: AccelerometerSensorService
    private lateinit var map: MapView
    private lateinit var mapRenderingService: MapRenderingServices
    private lateinit var mapEventService: MapEventServices



    private val locationSettings= registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()
    ) {
        if (it.resultCode == RESULT_OK) {
            locationService.startLocationUpdates()
        } else {
            //Todo
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
            Log.i("distancia", "la distancia es: $distancia eso")
            mapRenderingService.currentLocation.geoPoint= GeoPoint(location.latitude,location.longitude)
            mapRenderingService.addMarker(mapRenderingService.currentLocation.geoPoint, typeMarker = 'A')
            if(distancia>=30){
                mapRenderingService.currentLocation.fecha=Date(System.currentTimeMillis())
                mapRenderingService.center(mapRenderingService.currentLocation.geoPoint)
            }
        }
    }
    @Override
    override fun onPause(){
        super.onPause()
        map.onPause()
        lightSensorService.unregisterLightSensorListener()
        accelerometerSensorService.unregisterAccelerometerSensorListener()
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
    }

    private fun locationSettings(){
        val builder= LocationSettingsRequest.Builder().addLocationRequest(locationService.locationRequest)
        val client: SettingsClient = LocationServices.getSettingsClient(this)
        val task: Task<LocationSettingsResponse> = client.checkLocationSettings(builder.build())

        task.addOnSuccessListener {
            locationService.startLocationUpdates()
        }

        task.addOnFailureListener{
            if(it is ResolvableApiException){
                try {
                    val isr: IntentSenderRequest = IntentSenderRequest.Builder(it.resolution).build()
                    locationSettings.launch(isr)
                }catch (sendEx: IntentSender.SendIntentException){
                    //eso!!
                }
            }
        }
    }

    override fun onLocationUpdate(location: Location) {
        updateUI(location)
    }

    private fun changeSpeedStatus(speed: Float){
        if(speed <= 3){
            binding.speedStatus.text = "ESTADO: Quieto"
            binding.speedStatus.setTextColor(resources.getColor(R.color.verde))
        }
        if(speed > 3 && speed <= 14.7){
            binding.speedStatus.text = "ESTADO: Caminando"
            binding.speedStatus.setTextColor(resources.getColor(R.color.amarilloVerdoso))
        }
        if(speed > 14.7 && speed <= 24.9){
            binding.speedStatus.text = "ESTADO: Trotando"
            binding.speedStatus.setTextColor(resources.getColor(R.color.amarillo))
        }
        if(speed > 24.9 && speed <= 49){
            binding.speedStatus.text = "ESTADO: Corrieno"
            binding.speedStatus.setTextColor(resources.getColor(R.color.naranja))
        }
        if(speed > 49){
            binding.speedStatus.text = "ESTADO: En un vehiculo"
            binding.speedStatus.setTextColor(resources.getColor(R.color.rojo))
        }
    }
}