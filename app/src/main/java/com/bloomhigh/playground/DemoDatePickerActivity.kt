package com.bloomhigh.playground

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bloomhigh.datepicker.BHDatePicker
import kotlinx.android.synthetic.main.activity_demo_datepicker.*

class DemoDatePickerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo_datepicker)

        bhDatePicker.setMaxDate("2016-03-13")
        bhDatePicker.setMinDate("2007-03-13")
        bhDatePicker.setCurrentDate("2016-03-13")

        bhDatePicker.callback = object : BHDatePicker.DateSelectionCallback {
            override fun onDateSelected(date: String) {
                tvResult.text = date
                bhDatePicker.setCurrentDate(date)
            }

        }
    }
}
