package com.example.bloodbank2_0_main
//
//import android.content.Context
//import android.location.Geocoder
//import android.location.LocationManager
//import androidx.core.content.ContextCompat.getSystemService
//
//class LocationUtils(context: Context) {
//
//    private val locationManager = getSystemService(context, LocationManager::class.java)
//    private val geocoder = Geocoder(context)
//
//    fun getCurrentLocality(): String? {
//        var location = locationManager?.getLastKnownLocation(LocationManager.GPS_PROVIDER)
//        return if (location != null) {
//            val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
//            if (addresses.isNotEmpty()) {
//                addresses?.get(0)?.locality
//            } else {
//                null
//            }
//        } else {
//            null
//        }
//    }
//
//}
