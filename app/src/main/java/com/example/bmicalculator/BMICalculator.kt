package com.example.bmicalculator

import kotlin.math.pow

class BMICalculator {

    companion object {
        // BMI Categories
        const val UNDERWEIGHT = "Недостаточный вес"
        const val NORMAL = "Нормальный вес"
        const val OVERWEIGHT = "Избыточный вес"
        const val OBESE = "Ожирение"

        // BMI Category Thresholds
        const val UNDERWEIGHT_THRESHOLD = 18.5
        const val NORMAL_THRESHOLD = 24.9
        const val OVERWEIGHT_THRESHOLD = 29.9
    }

    fun calculateBMI(weightKg: Double, heightCm: Double): Double {
        val heightM = heightCm / 100
        return weightKg / (heightM.pow(2))
    }

    fun getBMICategory(bmi: Double): String {
        return when {
            bmi < UNDERWEIGHT_THRESHOLD -> UNDERWEIGHT
            bmi < NORMAL_THRESHOLD -> NORMAL
            bmi < OVERWEIGHT_THRESHOLD -> OVERWEIGHT
            else -> OBESE
        }
    }

    fun getHealthRiskDescription(category: String): String {
        return when (category) {
            UNDERWEIGHT -> "Повышенный риск развития заболеваний, связанных с недостатком веса."
            NORMAL -> "Низкий риск развития заболеваний, связанных с весом."
            OVERWEIGHT -> "Повышенный риск развития сердечно-сосудистых заболеваний, диабета 2 типа."
            OBESE -> "Высокий риск развития сердечно-сосудистых заболеваний, диабета 2 типа, некоторых видов рака."
            else -> "Неизвестная категория."
        }
    }
}
