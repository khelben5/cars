package com.eduardodev.cars.data.dataSource

import androidx.lifecycle.LiveData
import com.eduardodev.cars.data.repository.DataResource


interface DataSource {
    fun getCars(): LiveData<DataResource>
}