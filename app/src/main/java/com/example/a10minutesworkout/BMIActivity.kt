package com.example.a10minutesworkout

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.a10minutesworkout.databinding.ActivityBmiBinding
import java.math.BigDecimal
import java.math.RoundingMode

class BMIActivity : AppCompatActivity() {

    companion object {
        private const val METRIC_UNITS_VIEW = "METRIC_UNIT_VIEW"
        private const val US_UNITS_VIEW = "US_UNIT_VIEW"
    }

    private var currentVisibleView: String =
        METRIC_UNITS_VIEW

    private var binding: ActivityBmiBinding? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBmiBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbarBmiActivity)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = "CALCULATE BMI"
        }

        binding?.toolbarBmiActivity?.setNavigationOnClickListener{
            onBackPressedDispatcher.onBackPressed()
        }

        makeVisibleMetricUnitsView()

        binding?.rgUnits?.setOnCheckedChangeListener { _, checkedId: Int ->

            if (checkedId == R.id.rbMetricUnits) {
                makeVisibleMetricUnitsView()
            } else {
                makeVisibleUsUnitsView()
            }
        }

        binding?.btnCalculateUnits?.setOnClickListener {
            if (validateMetricUnits()) {
                val heightValue : Float =
                    binding?.etMetricUnitHeight?.text.toString().toFloat() / 100
                val weightValue : Float =
                    binding?.etMetricUnitWeight?.text.toString().toFloat()

                val bmi = weightValue / (heightValue * heightValue)
                displayBMIResults(bmi)
            }else{
                Toast.makeText(this@BMIActivity, "Please enter valid values!",
                    Toast.LENGTH_LONG).show()
            }
        }
    }
    private fun validateMetricUnits(): Boolean {

        var isValid = true

        if (binding?.etMetricUnitWeight?.text.toString().isEmpty()) {
            isValid = false
        }else if (binding?.etMetricUnitHeight?.text.toString().isEmpty()) {
            isValid = false
        }
        return isValid
    }
    private fun makeVisibleMetricUnitsView() {
        currentVisibleView = METRIC_UNITS_VIEW
        binding?.tiMetricUnitWeight?.visibility = View.VISIBLE
        binding?.tiMetricUnitHeight?.visibility = View.VISIBLE
        binding?.tilUsMetricUnitWeight?.visibility = View.GONE
        binding?.tilMetricUsUnitHeightFeet?.visibility = View.GONE
        binding?.tilMetricUsUnitHeightInch?.visibility = View.GONE

        binding?.etMetricUnitHeight?.text!!.clear()
        binding?.etMetricUnitWeight?.text!!.clear()

        binding?.llDisplayBMIResult?.visibility = View.INVISIBLE
    }
    private fun makeVisibleUsUnitsView() {
        currentVisibleView = US_UNITS_VIEW
        binding?.tiMetricUnitHeight?.visibility = View.INVISIBLE
        binding?.tiMetricUnitWeight?.visibility = View.INVISIBLE
        binding?.tilUsMetricUnitWeight?.visibility = View.VISIBLE
        binding?.tilMetricUsUnitHeightFeet?.visibility = View.VISIBLE
        binding?.tilMetricUsUnitHeightInch?.visibility = View.VISIBLE

        binding?.etUsMetricUnitWeight?.text!!.clear()
        binding?.etUsMetricUnitHeightFeet?.text!!.clear()
        binding?.etUsMetricUnitHeightInch?.text!!.clear()

        binding?.llDisplayBMIResult?.visibility = View.INVISIBLE
    }
    private fun displayBMIResults(bmi: Float) {

        val bmiLabel: String
        val bmiDescription: String

        if (bmi.compareTo(15f) <= 0) {
            bmiLabel = "You look amazing!"
            bmiDescription = "If you want, " +
                    "you can consider checking some food diets that have more carbs," +
                    " protein and healthy fat + some vitamins and minerals."

        }else if (bmi.compareTo(15f) > 0 && bmi.compareTo(16f) <= 0) {
            bmiLabel = "You look amazing!"
            bmiDescription = "If you want, " +
                    "you can consider checking some food diets that have more carbs," +
                    " protein and healthy fat + some vitamins and minerals."

        }else if (bmi.compareTo(16f) > 0 && bmi.compareTo(18.5f) <= 0) {
        bmiLabel = "You look amazing!"
        bmiDescription = "If you want, " +
                "you can consider checking some food diets that have more carbs," +
                " protein and healthy fat + some vitamins and minerals."

        }else if (bmi.compareTo(18.5f) > 0 && bmi.compareTo(25f) <= 0) {
            bmiLabel = "You look amazing!"
            bmiDescription = "If you want, " +
                    "you can consider checking some food diets that have carbs," +
                    " more protein and a bit healthy fat + some vitamins and minerals."

        }else if (bmi.compareTo(25f) > 0 && bmi.compareTo(30f) <= 0) {
            bmiLabel = "You look amazing!"
            bmiDescription = "If you want, " +
                    "you can consider checking some food diets that has carbs," +
                    " more protein and tiny bit of healthy fat + some vitamins and minerals."

        }else if (bmi.compareTo(30f) > 0 && bmi.compareTo(35f) <= 0) {
            bmiLabel = "You look amazing!"
            bmiDescription = "If you want, " +
                    "you can consider checking some food diets that have less carbs," +
                    " more protein + some vitamins and minerals."

        }else if (bmi.compareTo(35f) > 0 && bmi.compareTo(40f) <= 0) {
        bmiLabel = "You look amazing!"
        bmiDescription = "If you want, " +
                "you can consider checking some food diets that have more veggies," +
                " protein + some vitamins and minerals."

        }else{
            bmiLabel = "You look amazing!"
            bmiDescription = "We believe that everyone should choose the way that they look like," +
                    "and at the same time we care about our clients.\n" +
                    "We see that it will be great " +
                    "if you go to see a professional Nutritionist."
        }

        binding?.llDisplayBMIResult?.visibility = View.VISIBLE

        val bmiValue =BigDecimal(bmi.toDouble())
            .setScale(2,RoundingMode.HALF_EVEN).toString()

        binding?.llDisplayBMIResult?.visibility = View.VISIBLE
        binding?.tvBMIValue?.text = bmiValue
        binding?.tvBMIType?.text = bmiLabel
        binding?.tvBMIDescription?.text = bmiDescription
    }
}


















