package com.eduardodev.cars.presentation.car.map

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.eduardodev.cars.R


class CarMapFragment : Fragment() {

    companion object {
        fun newInstance() = CarMapFragment()
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_car_map, container, false)
}