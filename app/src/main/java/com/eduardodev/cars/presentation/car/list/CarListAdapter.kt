package com.eduardodev.cars.presentation.car.list

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.eduardodev.cars.R
import com.eduardodev.cars.presentation.model.Car


class CarListAdapter(private val cars: List<Car>) : RecyclerView.Adapter<CarListItemHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarListItemHolder {
        val inflater = LayoutInflater.from(parent.context)
        return CarListItemHolder(inflater.inflate(R.layout.list_item_car, parent, false))
    }

    override fun getItemCount() = cars.size

    override fun onBindViewHolder(holder: CarListItemHolder, position: Int) {
        holder.car = cars[position]
    }
}