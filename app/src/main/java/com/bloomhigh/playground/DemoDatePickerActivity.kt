package com.bloomhigh.playground

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bloomhigh.datepicker.BHDatePicker
import kotlinx.android.synthetic.main.activity_demo_datepicker.*

class DemoDatePickerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo_datepicker)

        bhDatePicker.setMaxDate("2016-03-11")
        bhDatePicker.setMinDate("2000-03-11")
        bhDatePicker.setCurrentDate("2021-03-11")

        bhDatePicker.callback = object : BHDatePicker.DateSelectionCallback {
            override fun onDateSelected(date: String) {
                tvResult.text = date
            }

        }
    }
}
