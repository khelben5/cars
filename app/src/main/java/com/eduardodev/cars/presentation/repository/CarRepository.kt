package com.eduardodev.cars.presentation.repository

import android.arch.lifecycle.LiveData
import com.eduardodev.cars.presentation.model.Resource


interface CarRepository {
    fun getCars(): LiveData<Resource>
}