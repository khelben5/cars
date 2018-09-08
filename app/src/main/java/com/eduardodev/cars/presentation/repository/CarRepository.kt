package com.eduardodev.cars.presentation.repository

import androidx.lifecycle.LiveData
import com.eduardodev.cars.presentation.model.Resource


interface CarRepository {
    fun getCars(): LiveData<Resource>
}