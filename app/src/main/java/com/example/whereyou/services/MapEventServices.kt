package com.example.whereyou.services

import com.google.android.gms.maps.model.LatLng
import org.osmdroid.events.MapEventsReceiver
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.MapEventsOverlay

class MapEventServices (private val map: MapView, private val mapRenderingService: MapRenderingServices){

    fun createOverlayEvents() {
        val overlayEvents = MapEventsOverlay(object : MapEventsReceiver {
            override fun singleTapConfirmedHelper(p: GeoPoint?): Boolean {
                return false
            }
            override fun longPressHelper(p: GeoPoint?): Boolean {
                if(p!=null) {
                    longPressOnMap(p)
                }
                return true
            }
        })
        map.overlays.add(overlayEvents)
    }
    private fun longPressOnMap(geo: GeoPoint) {
        val snippet: String = mapRenderingService.findAddress(LatLng(geo.latitude, geo.longitude)) ?: ""
        mapRenderingService.addMarker(geo, snippet, 'L')
        mapRenderingService.drawRoute(mapRenderingService.currentLocation.geoPoint,geo)
    }
}