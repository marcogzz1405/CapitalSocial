package com.example.capitalsocial.activities

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.example.capitalsocial.R
import com.example.capitalsocial.bases.BaseActivity
import com.example.capitalsocial.utilities.Settings
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_map.*

class MapActivity : BaseActivity(), OnMapReadyCallback, LocationListener {

    private val cLocationRequestCode = 101
    private val cLocationUpdateCode = 102
    private lateinit var mMap: GoogleMap
    private var marker: Marker? = null
    private var latLng: LatLng? = null

    companion object {
        fun getInstance(context: Context): Intent {
            return Intent(context, MapActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        startMap()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return false
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    override fun onResume() {
        super.onResume()
        if (::mMap.isInitialized){
            initLocation()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            cLocationRequestCode -> {
                if (grantResults.isEmpty() || grantResults[0] !=
                    PackageManager.PERMISSION_GRANTED){
                    showMessage(Settings.TYPE_MESSAGE_ERROR, getString(R.string.error_title), "Unable to show location - permission required")
                } else {
                    startMap()
                }
            }
            cLocationUpdateCode -> {
                if (grantResults.isEmpty() || grantResults[0] !=
                    PackageManager.PERMISSION_GRANTED){
                    showMessage(Settings.TYPE_MESSAGE_ERROR, getString(R.string.error_title), "Unable to show location permission required")
                } else {
                    initLocation()
                }
            }
        }
    }

    override fun onMapReady(map: GoogleMap?) {
        if(map != null){
            mMap = map
            val permission = ContextCompat.checkSelfPermission(this@MapActivity, android.Manifest.permission.ACCESS_FINE_LOCATION)
            if (permission == PackageManager.PERMISSION_GRANTED){
                mMap.isMyLocationEnabled = true
            }
            mapView.onResume()
            initLocation()
        }
    }

    private fun initMap() {
        mapView.onCreate(Bundle())
        mapView.getMapAsync(this)
    }

    private fun startMap() {
        val permission = ContextCompat.checkSelfPermission(this@MapActivity, android.Manifest.permission.ACCESS_FINE_LOCATION)
        if (permission == PackageManager.PERMISSION_GRANTED){
            if (::mMap.isInitialized){
                mMap.isMyLocationEnabled = true
            } else {
                initMap()
            }
        } else {
            requestPermission(
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                cLocationRequestCode
            )
        }
    }

    private fun requestPermission(permissionType: String,
                                  requestCode: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(arrayOf(permissionType), requestCode)
        }
    }

    private fun initLocation() {
        val permission = ContextCompat.checkSelfPermission(this@MapActivity, android.Manifest.permission.ACCESS_COARSE_LOCATION)
        if (permission == PackageManager.PERMISSION_GRANTED){
            val locationManager =
                this@MapActivity.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            val isGPSEnable = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
            val isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
            if (isGPSEnable || isNetworkEnabled){
                var locationProvider: String = LocationManager.GPS_PROVIDER
                if (!isGPSEnable){
                    locationProvider = LocationManager.NETWORK_PROVIDER
                }
                val location = locationManager.getLastKnownLocation(locationProvider)
                if (location == null){
                    locationManager.requestLocationUpdates(locationProvider, 0, 0f, this)
                } else {
                    addMarker(location)
                }
            } else {
                val dialog = AlertDialog.Builder(applicationContext)
                dialog.setMessage(resources.getString(R.string.gps_network_not_enabled))
                dialog.setPositiveButton(resources.getString(R.string.open_location_settings)){ _, _ ->
                    startActivity(Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS))
                }
                dialog.setNegativeButton(resources.getString(R.string.Cancel)) { paramDialogInterface, _ ->
                    paramDialogInterface.dismiss()
                }
                dialog.show()
            }
        } else {
            requestPermission(
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                cLocationUpdateCode)
        }
    }

    private fun addMarker(location: Location) {
        val locationManager =
            this@MapActivity.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        marker = mMap.addMarker(MarkerOptions().position(LatLng(location.latitude, location.longitude)))
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(CameraPosition.builder().target(LatLng(location.latitude, location.longitude)).zoom(14f).build()))
        locationManager.removeUpdates(this)
    }

    override fun onLocationChanged(location: Location) {
        addMarker(location)
    }

    override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onProviderEnabled(p0: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onProviderDisabled(p0: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
