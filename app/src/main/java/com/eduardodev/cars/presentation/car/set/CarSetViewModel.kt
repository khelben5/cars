package com.eduardodev.cars.presentation.car.set

import androidx.lifecycle.ViewModel
import com.eduardodev.cars.data.repository.CarDataRepository
import com.eduardodev.cars.presentation.repository.CarRepository


class CarSetViewModel(private val repository: CarRepository = CarDataRepository()) : ViewModel() {
    val cars by lazy { repository.getCars() }
}