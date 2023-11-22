package com.example.whereyou

import android.Manifest
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import android.view.Menu
import android.view.MenuItem
import com.example.whereyou.databinding.ActivityMapaGrupoBinding
import com.example.whereyou.model.MyLocation
import com.example.whereyou.services.LocationService
import com.example.whereyou.services.MapEventServices
import com.example.whereyou.services.MapRenderingServices
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsResponse
import com.google.android.gms.location.SettingsClient
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import java.util.Date

class MapaGrupoActivity : AppCompatActivity() , LocationService.LocationUpdateListener{
    private val getSimplePermission= registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { }
    private lateinit var binding : ActivityMapaGrupoBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var locationService: LocationService
    private lateinit var map: MapView
    private lateinit var mapRenderingService: MapRenderingServices

    private val locationSettings= registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()
    ) {
        if (it.resultCode == RESULT_OK) {
            locationService.startLocationUpdates()
        } else {
            Toast.makeText(this, "La localizacion esta desactivada", Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val clicked = item.itemId
        if(clicked == R.id.menuLogOut){

            val i = Intent(this, MainActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(i)
        }
        return super.onOptionsItemSelected(item)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapaGrupoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        locationService = LocationService(this, this)
        map= binding.mapa
        mapRenderingService= MapRenderingServices(this,map)

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
                mapRenderingService.currentLocation= MyLocation(
                    Date(System.currentTimeMillis()),
                    GeoPoint(it.latitude, it.longitude)
                )
                mapRenderingService.center(geo)
                updateUI(it)
            }else{
                Log.i("daniel", "Esta apagada la ubicacion")
                locationSettings()
            }
        }

        binding.HAChats.setOnClickListener{
            //startActivity(Intent(baseContext, ChatsActivity::class.java))
        }

        binding.HAGrupos.setOnClickListener {
            var intent = Intent(baseContext, GruposActivity::class.java)
            startActivity(intent)
        }

        binding.HAChats.setOnClickListener {
            startActivity(Intent(baseContext,ChatsActivity::class.java))
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
        binding.GAMenuLogOut.setOnClickListener {
            auth.signOut()
            val i = Intent(this, LoginActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(i)
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
            if(distancia > 30) {
                mapRenderingService.center(mapRenderingService.currentLocation.geoPoint)
            }
            binding.HADireccion.text = ubicacion
        }
    }

    @Override
    override fun onPause(){
        super.onPause()
        map.onPause()
        locationService.stopLocationUpdates()
    }
    override fun onResume() {
        super.onResume()
        map.onResume()
        map.controller.animateTo(mapRenderingService.currentLocation.geoPoint)
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

    private fun buscaRutas(geo: GeoPoint, speed: Double, nombre: String){
        if(speed <= 0.1){
            mapRenderingService.addMarker(geo, nombre, 'Q')
        }
        if(speed > 0.1 && speed <= 14.7){
            mapRenderingService.addMarker(geo, nombre, 'c')
        }
        if(speed > 14.7 && speed <= 24.9){
            mapRenderingService.addMarker(geo, nombre, 'T')
        }
        if(speed > 24.9 && speed <= 49){
            mapRenderingService.addMarker(geo, nombre, 'C')
        }
        if(speed > 49){
            mapRenderingService.addMarker(geo, nombre, 'V')
        }
        mapRenderingService.drawRoute(mapRenderingService.currentLocation.geoPoint,geo)
    }
}