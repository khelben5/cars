package com.eduardodev.cars.presentation.model


sealed class Resource

class Success(val result: Any) : Resource()
class Failure(val exception: Exception) : Resource()
object Progress : Resource()
object ConnectionFailure : Resource()