package com.eduardodev.cars.data.dataSource

import android.arch.lifecycle.LiveData
import com.eduardodev.cars.data.repository.DataResource


interface DataSource {
    fun getCars(): LiveData<DataResource>
}