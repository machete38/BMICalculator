package com.example.bmicalculator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.TransitionManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import com.google.android.material.slider.Slider

class MainActivity : AppCompatActivity() {

    private lateinit var weightEditText: EditText
    private lateinit var heightEditText: EditText
    private lateinit var calculateButton: Button
    private lateinit var resultTextView: TextView
    private lateinit var categoryTextView: TextView
    private lateinit var healthRiskTextView: TextView
    private lateinit var bmiSlider: Slider

    private val bmiCalculator = BMICalculator()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
        setupListeners()
    }

    private fun initViews() {
        weightEditText = findViewById(R.id.weightEditText)
        heightEditText = findViewById(R.id.heightEditText)
        calculateButton = findViewById(R.id.calculateButton)
        resultTextView = findViewById(R.id.resultTextView)
        categoryTextView = findViewById(R.id.categoryTextView)
        healthRiskTextView = findViewById(R.id.healthRiskTextView)
        bmiSlider = findViewById(R.id.bmiSlider)
    }

    private fun setupListeners() {
        calculateButton.setOnClickListener {
            calculateBMI()
        }
    }

    private fun calculateBMI() {
        animateLayout()
        val weight = weightEditText.text.toString().toDoubleOrNull()
        val height = heightEditText.text.toString().toDoubleOrNull()

        if (weight != null && height != null) {
            val bmi = bmiCalculator.calculateBMI(weight, height)
            val category = bmiCalculator.getBMICategory(bmi)
            val healthRisk = bmiCalculator.getHealthRiskDescription(category)

            displayResults(bmi, category, healthRisk)
        } else {
            resultTextView.text = "Пожалуйста, введите корректные значения веса и роста"
        }
    }

    private fun displayResults(bmi: Double, category: String, healthRisk: String) {
        resultTextView.text = String.format("Ваш BMI: %.2f", bmi)
        categoryTextView.text = "Категория: $category"
        healthRiskTextView.text = "Риск для здоровья: $healthRisk"

        // Ограничиваем значение BMI для слайдера
        val sliderValue = bmi.toFloat().coerceIn(10f, 40f)
        bmiSlider.value = sliderValue

        val color = when (category) {
            BMICalculator.UNDERWEIGHT -> R.color.underweight
            BMICalculator.NORMAL -> R.color.normal
            BMICalculator.OVERWEIGHT -> R.color.overweight
            BMICalculator.OBESE -> R.color.obese
            else -> R.color.default_color
        }
        bmiSlider.trackActiveTintList = ContextCompat.getColorStateList(this, color)!!
        bmiSlider.thumbTintList = ContextCompat.getColorStateList(this, color)!!
    }

    private fun animateLayout() {
        val constraintSet = ConstraintSet()
        constraintSet.clone(findViewById<ConstraintLayout>(R.id.root_layout))

        // Изменение позиции кнопки
        constraintSet.setHorizontalBias(R.id.calculateButton, 0.3f)

        // Анимация изменений
        TransitionManager.beginDelayedTransition(findViewById(R.id.root_layout))
        constraintSet.applyTo(findViewById(R.id.root_layout))
    }
}