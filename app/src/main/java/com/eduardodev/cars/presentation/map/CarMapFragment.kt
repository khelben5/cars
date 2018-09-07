package com.eduardodev.cars.presentation.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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