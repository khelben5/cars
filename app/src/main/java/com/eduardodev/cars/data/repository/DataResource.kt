package com.eduardodev.cars.data.repository


sealed class DataResource

class SuccessDataResource(val result: Any) : DataResource()
class FailureDataResource(val exception: Exception) : DataResource()
object ProgressDataResource : DataResource()
object ConnectionFailureDataResource : DataResource()