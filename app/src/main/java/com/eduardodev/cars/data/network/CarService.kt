package com.eduardodev.cars.data.network

import retrofit2.Call
import retrofit2.http.GET


interface CarService {

    @GET("/cars.json")
    fun getCars(): Call<List<CarNetworkEntity>>
}