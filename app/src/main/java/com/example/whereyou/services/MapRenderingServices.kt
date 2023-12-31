package com.example.whereyou.services

import android.content.Context
import android.graphics.Color
import android.location.Geocoder
import android.os.StrictMode
import android.util.Log
import androidx.core.content.res.ResourcesCompat
import com.example.whereyou.R
import com.example.whereyou.model.MyLocation
import com.google.android.gms.maps.model.LatLng
import org.osmdroid.bonuspack.routing.OSRMRoadManager
import org.osmdroid.bonuspack.routing.Road
import org.osmdroid.bonuspack.routing.RoadManager
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.Polyline
import org.osmdroid.views.overlay.TilesOverlay
import java.util.Date

class MapRenderingServices(private val context: Context, private val map : MapView) {
    companion object{
        const val lowerLeftLatitude=1.396967
        const val lowerLeftLongitude= -78.903968
        const val upperRightLatitude= 11.983639
        const val upperRightLongitude= -71.869905
    }
    private var longPressedMarker:Marker?= null
    private var searchBtnMarker: Marker?=null
    private var currentPositionMarker: Marker?=null
    private var markers: MutableList<Marker> = mutableListOf()
    var currentLocation: MyLocation = MyLocation(Date(System.currentTimeMillis()))
    private var roadManager:RoadManager = OSRMRoadManager(context, "ANDROID")
    private var geocoder: Geocoder
    private var roadOverlay: Polyline? = null
    private var beforePoint: Marker?= null

    init {
        Configuration.getInstance().load(context, androidx.preference.PreferenceManager.getDefaultSharedPreferences(context))
        map.setTileSource(TileSourceFactory.DEFAULT_TILE_SOURCE)
        map.setMultiTouchControls(true)
        val bogota= GeoPoint(4.62,-74.07)
        map.controller.setZoom(18.0)
        map.controller.animateTo(bogota)
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        geocoder= Geocoder(context)
    }
    fun addMarker(geo: GeoPoint, title: String?=null, typeMarker: Char){
        val marker= Marker(map)
        when (typeMarker) {
            'A' -> {
                marker.title="Ubicacion Actual"
                val icon= ResourcesCompat.getDrawable(context.resources,
                    R.drawable.baseline_location_on_24_blue, context.theme)
                marker.icon=icon
                if(currentPositionMarker!=null){
                    map.overlays.remove(currentPositionMarker)
                }
                currentPositionMarker=marker
            }
            'S' -> {
                if(title!=null){
                    marker.title= "Punto Buscado$title"
                }else{
                    marker.title="Punto Buscado"
                }
                val icon= ResourcesCompat.getDrawable(context.resources,
                    R.drawable.baseline_location_on_24_green, context.theme)
                marker.icon=icon
                if(searchBtnMarker!=null){
                    map.overlays.remove(searchBtnMarker)
                }
                searchBtnMarker= marker
                removeMarkers()
                center(geo)
            }
            'L' -> {
                if(title!=null){
                    marker.title=title
                }else{
                    marker.title="Punto Clickeado"
                }
                val icon= ResourcesCompat.getDrawable(context.resources,
                    R.drawable.baseline_location_on_24_red, context.theme)
                marker.icon=icon
                if(longPressedMarker!=null){
                    map.overlays.remove(longPressedMarker)
                }
                longPressedMarker= marker
                removeMarkers()
                center(geo)
            }
            'l'->{
                marker.title="Punto Anterior"
                val icon= ResourcesCompat.getDrawable(context.resources,R.drawable.baseline_location_on_24_black,context.theme)
                marker.icon=icon
                markers.add(marker)
            }
            'Q'->{
                if(title!=null){
                    marker.title = "$title - Quieto"
                }else{
                    marker.title="Punto de otra persona - Quieto"
                }
                val icon= ResourcesCompat.getDrawable(context.resources,
                    R.drawable.baseline_location_on_24_quieto, context.theme)
                marker.icon=icon
                if(beforePoint != null){
                    map.overlays.remove(beforePoint)
                }
                beforePoint =marker
                removeMarkers()
                center(geo)
            }
            'c'->{
                if(title!=null){
                    marker.title = "$title - Caminando"
                }else{
                    marker.title="Punto de otra persona - Caminando"
                }
                val icon= ResourcesCompat.getDrawable(context.resources,
                    R.drawable.baseline_location_on_24_caminando, context.theme)
                marker.icon=icon
                if(beforePoint != null){
                    map.overlays.remove(beforePoint)
                }
                beforePoint =marker
                removeMarkers()
                center(geo)
            }
            'T'->{
                if(title!=null){
                    marker.title = "$title - Trotando"
                }else{
                    marker.title="Punto de otra persona - Trotando"
                }
                val icon= ResourcesCompat.getDrawable(context.resources,
                    R.drawable.baseline_location_on_24_trotando, context.theme)
                marker.icon=icon
                if(beforePoint != null){
                    map.overlays.remove(beforePoint)
                }
                beforePoint =marker
                removeMarkers()
                center(geo)
            }
            'C'->{
                if(title!=null){
                    marker.title = "$title - Corriendo"
                }else{
                    marker.title="Punto de otra persona - Corriendo"
                }
                val icon= ResourcesCompat.getDrawable(context.resources,
                    R.drawable.baseline_location_on_24_corriendo, context.theme)
                marker.icon=icon
                if(beforePoint != null){
                    map.overlays.remove(beforePoint)
                }
                beforePoint =marker
                removeMarkers()
                center(geo)
            }
            'V'->{
                if(title!=null){
                    marker.title = "$title - En vehiculo"
                }else{
                    marker.title="Punto de otra persona - En vehiculo"
                }
                val icon= ResourcesCompat.getDrawable(context.resources,
                    R.drawable.baseline_location_on_24_en_vehiculo, context.theme)
                marker.icon=icon
                if(beforePoint != null){
                    map.overlays.remove(beforePoint)
                }
                beforePoint =marker
                removeMarkers()
                center(geo)
            }
        }
        marker.position=geo
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
        map.overlays.add(marker)
    }
    fun center(geo: GeoPoint){
        map.controller.animateTo(geo)
        map.controller.setZoom(20.0)
    }
    private fun removeMarkers(){
        for (i in markers.indices.reversed()) {
            map.overlays.remove(markers[i])
            markers.removeAt(i)
        }
    }
    fun drawRoute(routePoints: ArrayList<GeoPoint>) {
        val road = roadManager.getRoad(routePoints)
        removeMarkers()
        for (i in routePoints) {
            addMarker(i, typeMarker = 'T')
        }
        displayRoad(road)
    }
    fun drawRoute(start : GeoPoint, finish : GeoPoint) {
        val routePoints = ArrayList<GeoPoint>()
        routePoints.add(start)
        routePoints.add(finish)
        val road = roadManager.getRoad(routePoints)
        displayRoad(road)
    }
    private fun displayRoad(road: Road) {
        Log.i("MapsApp", "Route length: " + road.mLength + " klm")
        Log.i("MapsApp", "Duration: " + road.mDuration / 60 + " min")
        if (roadOverlay != null) {
            map.overlays.remove(roadOverlay)
        }
        roadOverlay = RoadManager.buildRoadOverlay(road)
        roadOverlay!!.outlinePaint.color = Color.RED
        roadOverlay!!.outlinePaint.strokeWidth = 10F
        map.overlays.add(roadOverlay)
    }
    fun changeMapColors(light: Float){
        if(light<5000){
            map.overlayManager.tilesOverlay.setColorFilter(TilesOverlay.INVERT_COLORS)
        }else{
            map.overlayManager.tilesOverlay.setColorFilter(null)
        }
    }
    fun findAddress (location : LatLng):String?{
        val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 2)
        if(!addresses.isNullOrEmpty()) {
            val addr = addresses[0]
            return addr.getAddressLine(0)
        }
        return null
    }

    fun findLocation(address : String): LatLng? {
        val addresses = geocoder.getFromLocationName(address, 2,
            lowerLeftLatitude,
            lowerLeftLongitude,
            upperRightLatitude,
            upperRightLongitude
        )
        if (!addresses.isNullOrEmpty()) {
            val addr = addresses[0]
            return LatLng(addr.latitude, addr.longitude)
        }
        return null
    }
}