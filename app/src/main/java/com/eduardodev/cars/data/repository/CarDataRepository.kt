package com.eduardodev.cars.data.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Transformations
import com.eduardodev.cars.data.dataSource.DataSource
import com.eduardodev.cars.data.dataSource.NetworkDataSource
import com.eduardodev.cars.data.mapper.CarMapper
import com.eduardodev.cars.data.network.CarNetworkEntity
import com.eduardodev.cars.presentation.model.*
import com.eduardodev.cars.presentation.repository.CarRepository


class CarDataRepository(private val dataSource: DataSource = NetworkDataSource()) : CarRepository {

    private val carMapper = CarMapper()

    override fun getCars(): LiveData<Resource> =
            Transformations.map(dataSource.getCars()) { dataResource ->
                when (dataResource) {
                    is SuccessDataResource -> Success(carMapper.transformToPresentation(
                            (dataResource.result as List<*>).map { it as CarNetworkEntity }
                    ))
                    is FailureDataResource -> Failure(dataResource.exception)
                    is ConnectionFailureDataResource -> ConnectionFailure
                    is ProgressDataResource -> Progress
                }
            }
}