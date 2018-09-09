package com.eduardodev.cars.presentation.car.map

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.eduardodev.cars.R
import com.eduardodev.cars.presentation.car.set.CarSetViewModel
import com.eduardodev.cars.presentation.model.Car
import com.eduardodev.cars.presentation.model.Resource
import com.eduardodev.cars.presentation.model.Success
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions


class CarMapFragment : Fragment() {

    companion object {
        fun newInstance() = CarMapFragment()
    }

    private val model by lazy { ViewModelProviders.of(activity!!)[CarSetViewModel::class.java] }
    private var map: GoogleMap? = null
    private var cars: List<Car>? = null

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_car_map, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        model.cars.observe(this, Observer { resource ->
            resource?.let {
                onResourceLoaded(resource)
            }
        })
        (childFragmentManager.findFragmentById(R.id.carMapFragment) as SupportMapFragment)
                .getMapAsync(::onMapLoaded)
    }

    private fun onMapLoaded(map: GoogleMap) {
        this.map = map
        this.cars?.let { updateMarkers(map, it) }
    }

    private fun onResourceLoaded(resource: Resource) {
        if (resource !is Success) return
        val cars = (resource.result as List<*>).map { it as Car }
        this.cars = cars
        this.map?.let { updateMarkers(it, cars) }
    }

    private fun updateMarkers(map: GoogleMap, cars: List<Car>) {
        val allPositions = ArrayList<Pair<Double, Double>>(cars.size)
        cars.map {
            allPositions.add(it.position.first to it.position.second)
            map.addMarker(
                    MarkerOptions()
                            .position(LatLng(it.position.first, it.position.second))
                            .title(getString(
                                    R.string.car_list_item_main_description_template,
                                    it.model,
                                    it.plate
                            ))
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.car))
            )
        }
        map.moveCamera(CameraUpdateFactory.newLatLngBounds(
                LatLngBounds.Builder().apply {
                    allPositions.forEach { include(LatLng(it.first, it.second)) }
                }.build(),
                resources.getDimensionPixelSize(R.dimen.map_padding)
        ))
    }
}