package com.eduardodev.cars.data.network


data class CarNetworkEntity(
        val id: String,
        val licensePlate: String,
        val latitude: Double,
        val longitude: Double,
        val modelIdentifier: String,
        val color: String,
        val modelName: String,
        val carImageUrl: String?,
        val fuelType: String,
        val fuelLevel: Double,
        val transmission: String
)