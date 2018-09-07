package com.eduardodev.cars.presentation.set

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eduardodev.cars.presentation.model.Car
import com.eduardodev.cars.presentation.model.Resource
import com.eduardodev.cars.presentation.model.Success
import org.jetbrains.anko.doAsync


class CarSetViewModel : ViewModel() {

    private val cars by lazy {
        val result = MutableLiveData<Resource>()

        // todo
        doAsync {
            Thread.sleep(5000)
            result.postValue(Success(listOf(Car("1"), Car("2"))))
        }

        result
    }

    fun getCars(): LiveData<Resource> = cars
}