package br.edu.ufabc.imccalculadora

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class MainActivity : AppCompatActivity() {
    private lateinit var editTextHeight: TextInputEditText
    private lateinit var editTextWeight: TextInputEditText
    private lateinit var textLayoutHeight: TextInputLayout
    private lateinit var textLayoutWeight: TextInputLayout
    private lateinit var buttonCalculate: Button
    private lateinit var textViewImcValue: TextView
    private lateinit var textViewImcStatus: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        initComponents()
        bindEvents()
    }

    private fun initComponents() {
        editTextHeight = findViewById(R.id.edittext_height_m)
        editTextWeight = findViewById(R.id.edittext_weight_kg)
        textLayoutHeight = findViewById(R.id.layout_edittext_height_m)
        textLayoutWeight = findViewById(R.id.layout_edittext_weight_kg)
        buttonCalculate = findViewById(R.id.button_calculate)
        textViewImcValue = findViewById(R.id.text_imc_value)
        textViewImcStatus = findViewById(R.id.text_imc_status)
    }

    private fun bindEvents() {
        buttonCalculate.setOnClickListener {
            textLayoutHeight.error = null
            textLayoutWeight.error = null
            val heightText = editTextHeight.text.toString()
            val height: Float
            if (heightText.isEmpty()) {
                height = Float.NaN
                textLayoutHeight.error = getString(R.string.textlayout_height_invalid)
            } else {
                height = heightText.toFloat()
                if (height <= 0.0f || height > 5.0f) {
                    textLayoutHeight.error = getString(R.string.textlayout_height_invalid)
                }
            }
            val weightText = editTextWeight.text.toString()
            val weight: Float
            if (weightText.isEmpty()) {
                weight = Float.NaN
                textLayoutWeight.error = getString(R.string.textlayout_weight_invalid)
            } else {
                weight = weightText.toFloat()
                if (weight <= 0.0f || weight > 1000.0f) {
                    textLayoutWeight.error = getString(R.string.textlayout_weight_invalid)
                }
            }
            if (textLayoutHeight.error.isNullOrEmpty() && textLayoutWeight.error.isNullOrEmpty()) {
                val imc = weight / (height * height)
                val imcStatus = when {
                    imc >= 0 && imc < 17 -> getString(R.string.imc_status_very_underweight)
                    imc >= 17 && imc < 18.5 -> getString(R.string.imc_status_underweight)
                    imc >= 18.5 && imc < 25 -> getString(R.string.imc_status_normal_weight)
                    imc >= 25 && imc < 30 -> getString(R.string.imc_status_overweight)
                    imc >= 30 && imc < 35 -> getString(R.string.imc_status_obesity_1)
                    imc >= 35 && imc < 40 -> getString(R.string.imc_status_obesity_2)
                    else -> getString(R.string.imc_status_obesity_3)
                }
                textViewImcValue.text = getString(R.string.imc_value_formatted, imc)
                textViewImcStatus.text = imcStatus
            }
        }
    }
}