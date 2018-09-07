package com.eduardodev.cars.presentation.list

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.eduardodev.cars.presentation.model.Car
import com.eduardodev.cars.presentation.model.Resource
import com.eduardodev.cars.presentation.model.Success
import org.jetbrains.anko.doAsync


class CarListViewModel : ViewModel() {

    fun getCars(): LiveData<Resource> {
        // todo
        val result = MutableLiveData<Resource>()

        doAsync {
            Thread.sleep(5000)
            result.postValue(Success(listOf(Car("1"), Car("2"))))
        }

        return result
    }
}