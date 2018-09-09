package com.eduardodev.cars.presentation.car.map

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.eduardodev.cars.R
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment


class CarMapFragment : Fragment() {

    companion object {
        fun newInstance() = CarMapFragment()
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_car_map, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (childFragmentManager.findFragmentById(R.id.carMapFragment) as SupportMapFragment)
                .getMapAsync(::setupMap)
    }

    private fun setupMap(map: GoogleMap) {

    }
}