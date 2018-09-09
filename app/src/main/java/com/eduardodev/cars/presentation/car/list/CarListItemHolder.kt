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
        updatePictureUI(car)
        updateDescriptionUI(car)
        updateFuelTypeUI(car)
        updateTransmissionUI(car)
        updateFuelLevelUI(car)
    }

    private fun updatePictureUI(car: Car) {
        Picasso.get()
                .load(car.pictureUrl)
                .error(R.drawable.car)
                .fit()
                .centerInside()
                .into(listItemCarPicture)
    }

    private fun updateDescriptionUI(car: Car) {
        listItemCarMainDescription.text = containerView.resources.getString(
                R.string.car_list_item_main_description_template,
                car.model,
                car.plate
        )
    }

    private fun updateFuelTypeUI(car: Car) {
        listItemCarFuel.text = containerView.resources.getString(
                R.string.car_list_item_fuel_template,
                car.fuelType
        )
    }

    private fun updateTransmissionUI(car: Car) {
        listItemCarTransmission.text = containerView.resources.getString(
                R.string.car_list_item_transmission_template,
                car.transmission
        )
    }

    private fun updateFuelLevelUI(car: Car) {
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
                    else -> android.R.color.black // Not expected.
                }
        ))
    }
}