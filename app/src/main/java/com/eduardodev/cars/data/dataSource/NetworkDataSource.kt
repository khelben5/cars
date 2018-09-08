package com.eduardodev.cars.data.dataSource

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.eduardodev.cars.data.extension.readContent
import com.eduardodev.cars.data.network.CarNetworkEntity
import com.eduardodev.cars.data.network.CarService
import com.eduardodev.cars.data.repository.ConnectionFailureDataResource
import com.eduardodev.cars.data.repository.DataResource
import com.eduardodev.cars.data.repository.FailureDataResource
import com.eduardodev.cars.data.repository.SuccessDataResource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class NetworkDataSource : DataSource {

    private val carService by lazy {
        Retrofit.Builder()
                .baseUrl("http://www.codetalk.de")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(CarService::class.java)
    }

    override fun getCars(): LiveData<DataResource> {
        val result = MutableLiveData<DataResource>()

        carService.getCars().enqueue(object : Callback<List<CarNetworkEntity>> {
            override fun onFailure(call: Call<List<CarNetworkEntity>>, error: Throwable) {
                // This is a connection failure.
                result.value = ConnectionFailureDataResource
            }

            override fun onResponse(
                    call: Call<List<CarNetworkEntity>>,
                    response: Response<List<CarNetworkEntity>>
            ) {
                if (!response.isSuccessful) {
                    val errorDescription = response.errorBody()?.byteStream()?.readContent()
                    result.value = FailureDataResource(Exception(errorDescription))
                    return
                }

                result.value = response.body()?.let {
                    try {
                        SuccessDataResource(it)
                    } catch (exception: Exception) { // Something wrong in the transformation.
                        FailureDataResource(exception)
                    }
                } ?: FailureDataResource(Exception("response body is missing"))
            }
        })

        return result
    }
}