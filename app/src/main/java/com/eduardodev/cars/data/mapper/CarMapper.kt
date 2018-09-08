package com.eduardodev.cars.data.mapper

import com.eduardodev.cars.data.network.CarNetworkEntity
import com.eduardodev.cars.presentation.model.Car


class CarMapper {

    fun transformToPresentation(cars: List<CarNetworkEntity>) = cars.map { Car(it.id) }
}