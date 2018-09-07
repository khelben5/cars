package com.eduardodev.cars.presentation.list

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.eduardodev.cars.presentation.model.Car
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

    private fun updateUI(car: Car) {
        listItemCarId.text = car.id
    }
}