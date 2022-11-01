package com.example.jalavidapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.jalavidapp.databinding.ActivityMapsBinding
import com.example.jalavidapp.model.DataItemFaskes
import com.example.jalavidapp.model.RumahSakitItem

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar?.setCustomView(R.layout.toolbar_title_layout)
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        val title = findViewById<TextView>(R.id.action_bar_title)
        title.text = getString(R.string.map_title)
        title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20F)
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.uiSettings.isIndoorLevelPickerEnabled = true
        mMap.uiSettings.isCompassEnabled = true
        mMap.uiSettings.isMapToolbarEnabled = true
        val dataFaskes = intent.getParcelableExtra<DataItemFaskes>(DATAFASKES)
        val dataRS = intent.getParcelableExtra<RumahSakitItem>(DATARS)
        if(dataRS == null) {
            val faskes = dataFaskes?.longitude?.let {
                dataFaskes.latitude?.let { it1 ->
                    LatLng(
                        it1.toDouble(),
                        it.toDouble()
                    )
                }
            }
            if (dataFaskes != null) {
                faskes?.let {
                    MarkerOptions().position(it).title(dataFaskes.nama).snippet(dataFaskes.alamat)
                }
                    ?.let { mMap.addMarker(it)
                    }
            }
            faskes?.let { CameraUpdateFactory.newLatLng(it) }?.let { mMap.moveCamera(it) }
            faskes?.let { CameraUpdateFactory.newLatLngZoom(it, 15f) }
                ?.let { mMap.animateCamera(it) }
        } else{
            val rs = dataRS.lokasi?.lat?.let { dataRS.lokasi.lon?.let { it1 -> LatLng(it, it1) } }
            rs?.let {
                MarkerOptions().position(it).title(dataRS.nama).snippet(dataRS.alamat)
            }
                ?.let { mMap.addMarker(it) }
            rs?.let { CameraUpdateFactory.newLatLng(it) }?.let { mMap.moveCamera(it) }
            rs?.let { CameraUpdateFactory.newLatLngZoom(it, 15f) }?.let { mMap.animateCamera(it) }
        }
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
    companion object{
        const val DATARS = "DATARS"
        const val DATAFASKES = "DATAFASKES"
    }
}