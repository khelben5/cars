package com.eduardodev.cars.data.mapper

import com.eduardodev.cars.data.network.CarNetworkEntity
import com.eduardodev.cars.presentation.model.Car


class CarMapper {

    fun transformToPresentation(cars: List<CarNetworkEntity>) = cars.map {
        Car(
                it.id,
                it.licensePlate,
                Pair(it.latitude, it.longitude),
                it.modelName,
                // not working "https://prod.drive-now-content.com/fileadmin/user_upload_global/assets/cars/${it.modelIdentifier}/${it.color}/2x/car.png",
                it.carImageUrl,
                (it.fuelLevel * 100).toInt(),
                it.fuelType,
                it.transmission
        )
    }
}