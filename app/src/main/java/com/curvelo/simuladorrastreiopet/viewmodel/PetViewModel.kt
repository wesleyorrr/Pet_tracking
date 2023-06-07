package com.curvelo.simuladorrastreiopet.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.curvelo.simuladorrastreiopet.model.Pet
import java.lang.Math.sqrt
import kotlin.math.pow
import kotlin.random.Random

class PetViewModel : ViewModel() {
    private val _petLocation = MutableLiveData<Pet>()
    val petLocation: LiveData<Pet>
        get() = _petLocation

    private val _isPetInsideArea = MutableLiveData<Boolean>()
    val isPetInsideArea: LiveData<Boolean>
        get() = _isPetInsideArea

    fun generateRandomPetPosition() {
        val latitude = Random.nextDouble(-90.0, 90.0)
        val longitude = Random.nextDouble(-180.0, 180.0)
        _petLocation.value = Pet(latitude, longitude)
    }

    fun checkPetLocation(currentLatitude: Double, currentLongitude: Double) {
        val petLocation = petLocation.value
        if (petLocation != null) {
            val distance = calculateDistance(currentLatitude, currentLongitude, petLocation)
            _isPetInsideArea.value = distance <= thresholdDistance
        }
    }

    private fun calculateDistance(latitude: Double, longitude: Double, pet: Pet): Double {
        val latitudeDiff = pet.latitude - latitude
        val longitudeDiff = pet.longitude - longitude
        return sqrt(latitudeDiff.pow(2) + longitudeDiff.pow(2))
    }

    companion object {
        private const val thresholdDistance = 40.0
    }
}