package com.curvelo.simuladorrastreiopet.ui

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.curvelo.simuladorrastreiopet.R
import com.curvelo.simuladorrastreiopet.viewmodel.PetViewModel

import kotlin.random.Random
class MainActivity : AppCompatActivity() {

    private lateinit var latitudeTextView: TextView
    private lateinit var longitudeTextView: TextView
    private lateinit var resultTextView: TextView
    private lateinit var imageView: ImageView
    private lateinit var checkButton: Button

    private lateinit var petViewModel: PetViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        latitudeTextView = findViewById(R.id.latitudeTextView)
        longitudeTextView = findViewById(R.id.longitudeTextView)
        resultTextView = findViewById(R.id.resultTextView)
        imageView = findViewById(R.id.imageView)
        checkButton = findViewById(R.id.checkButton)

        petViewModel = ViewModelProvider(this).get(PetViewModel::class.java)

        checkButton.setOnClickListener { checkPetLocation() }

        petViewModel.petLocation.observe(this) { pet ->
            pet?.let {
                latitudeTextView.text = "Latitude: ${it.latitude}"
                longitudeTextView.text = "Longitude: ${it.longitude}"
            }
        }

        petViewModel.isPetInsideArea.observe(this) { isInside ->
            if (isInside) {
                resultTextView.text = "Traquilo... Ele está em casa"
                resultTextView.setTextColor(Color.GREEN)
                imageView.setImageResource(R.drawable.pet_parado)
            } else {
                resultTextView.text = "Ops.... Fugiu Correr!!!"
                resultTextView.setTextColor(Color.RED)
                imageView.setImageResource(R.drawable.correr)
            }
        }

        petViewModel.generateRandomPetPosition()
    }

    private fun checkPetLocation() {
        val currentLatitude = Random.nextDouble(-90.0, 90.0)
        val currentLongitude = Random.nextDouble(-180.0, 180.0)
        petViewModel.checkPetLocation(currentLatitude, currentLongitude)
    }
}