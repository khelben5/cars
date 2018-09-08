package com.eduardodev.cars.presentation.model


data class Car(
        val id: String,
        val plate: String,
        val position: Pair<Double, Double>,
        val model: String,
        val pictureUrl: String?,
        val fuelLevel: Int,
        val fuelType: String,
        val transmission: String
)