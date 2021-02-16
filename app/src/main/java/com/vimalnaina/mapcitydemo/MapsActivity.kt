package com.vimalnaina.mapcitydemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.google.android.gms.maps.*

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_maps.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, ItemClickListener {

    lateinit var map: GoogleMap

    private lateinit var mapAdapter: MapAdapter
    private var pos: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        setRecyclerView()

    }

    private fun setRecyclerView() {
        rvCity.apply {
            mapAdapter = MapAdapter(listLocation, this@MapsActivity)
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false)
            adapter = mapAdapter
        }

        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(rvCity)

        rvCity.addOnScrollListener(object: RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE){
                    val centerView = snapHelper.findSnapView(rvCity.layoutManager)
                    pos = rvCity.layoutManager!!.getPosition(centerView!!)
                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(listLocation[pos].second,10f))
                    Log.d("Position",pos.toString());
                }
            }
        })
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        // Marker rajkot
        map.addMarker(MarkerOptions().position(listLocation[0].second).title(listLocation[0].first))
        // Marker gandhinagar
        map.addMarker(MarkerOptions().position(listLocation[1].second).title(listLocation[1].first))
        // Marker mumbai
        map.addMarker(MarkerOptions().position(listLocation[2].second).title(listLocation[2].first))
        // Marker delhi
        map.addMarker(MarkerOptions().position(listLocation[3].second).title(listLocation[3].first))
        // Marker kolkata
        map.addMarker(MarkerOptions().position(listLocation[4].second).title(listLocation[4].first))
        // Marker chennai
        map.addMarker(MarkerOptions().position(listLocation[5].second).title(listLocation[5].first))
        // Marker bengaluru
        map.addMarker(MarkerOptions().position(listLocation[6].second).title(listLocation[6].first))

        map.animateCamera(CameraUpdateFactory.newLatLngZoom(listLocation[0].second,4f))

    }

    private val listLocation: List<Pair<String, LatLng>> = listOf(
            Pair("Rajkot", LatLng(22.2736308,70.7512554)),
            Pair("Gandhinagar", LatLng(23.220852,72.5755072)),
            Pair("Mumbai", LatLng(19.0825223,72.7410978)),
            Pair("Delhi", LatLng(28.6927189,76.811151)),
            Pair("Kolkata", LatLng(22.572645,88.363892)),
            Pair("Chennai", LatLng(13.0480438,79.928808)),
            Pair("Bengaluru", LatLng(12.954517,77.3507357))
    )

    override fun onItemClickListener(position: Int) {
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(listLocation[position].second,10f))
        mapAdapter.notifyItemChanged(position)
    }

}