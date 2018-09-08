package com.eduardodev.cars.presentation.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.eduardodev.cars.R
import com.eduardodev.cars.presentation.model.Car
import com.eduardodev.cars.presentation.model.Resource
import com.eduardodev.cars.presentation.model.Success
import com.eduardodev.cars.presentation.set.CarSetViewModel
import kotlinx.android.synthetic.main.fragment_car_list.*


class CarListFragment : Fragment() {

    companion object {
        fun newInstance() = CarListFragment()
    }

    private val model by lazy { ViewModelProviders.of(activity!!)[CarSetViewModel::class.java] }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_car_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        model.cars.observe(this, Observer { resource -> resource?.let { updateUI(it) } })
    }

    private fun updateUI(resource: Resource) {
        if (resource !is Success) return
        showCars(resource.result)
    }

    private fun showCars(result: Any) {
        if (result !is List<*>) return
        val cars = result.mapNotNull { it as? Car }
        carListRecyclerView.adapter = CarListAdapter(cars)
    }
}