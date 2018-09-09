package com.eduardodev.cars.presentation.car.list

import android.annotation.SuppressLint
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.View
import com.eduardodev.cars.R
import com.eduardodev.cars.presentation.model.Car
import com.squareup.picasso.Picasso
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.list_item_car.*

class CarListItemHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView),
        LayoutContainer {

    var car: Car? = null
        set(value) {
            field = value
            value?.let { updateUI(value) }
        }

    @SuppressLint("StringFormatInvalid")
    private fun updateUI(car: Car) {
        Picasso.get().load(car.pictureUrl).into(listItemCarPicture)
        listItemCarMainDescription.text = containerView.resources.getString(
                R.string.car_list_item_main_description_template,
                car.model,
                car.plate
        )
        listItemCarSecondaryDescription.text = containerView.resources.getString(
                R.string.car_list_item_secondary_description_template,
                car.fuelType,
                car.transmission
        )
        listItemCarFuelLevel.text = containerView.resources.getString(
                R.string.car_list_item_fuel_level_template,
                car.fuelLevel
        )
        listItemCarFuelLevel.setTextColor(ContextCompat.getColor(
                containerView.context,
                when (car.fuelLevel) {
                    in 75..100 -> android.R.color.holo_green_dark
                    in 25..74 -> android.R.color.holo_orange_dark
                    in 0..24 -> android.R.color.holo_red_dark
                    else -> android.R.color.black // Not expected.)
                }
        ))
    }
}