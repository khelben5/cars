package com.eduardodev.cars.data.dataSource

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.eduardodev.cars.data.network.CarNetworkEntity
import com.eduardodev.cars.data.repository.DataResource
import com.eduardodev.cars.data.repository.SuccessDataResource
import org.jetbrains.anko.doAsync


class NetworkDataSource : DataSource {

    override fun getCars(): LiveData<DataResource> {
        // todo
        val result = MutableLiveData<DataResource>()
        doAsync {
            Thread.sleep(5000)
            result.postValue(SuccessDataResource(listOf(
                    CarNetworkEntity("23"),
                    CarNetworkEntity("24")
            )))
        }
        return result
    }
}