package com.bloomhigh.playground

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_filter.*

class FilterActivity : AppCompatActivity(R.layout.activity_filter) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        rv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val filtersRecyclerviewAdapter = FiltersRecyclerviewAdapter()
        rv.adapter = filtersRecyclerviewAdapter.apply {
            setOnEffectSelectedListener(object : OnEffectSelectedListener {
                override fun onEffectSelected(effectIndex: Int) {
                    imageContainer.setSelectedEffect(effectIndex)
                }
            })
        }

        imageContainer.setOnEffectSelectedListener(object : OnEffectSelectedListener {
            override fun onEffectSelected(effectIndex: Int) {
                filtersRecyclerviewAdapter.selectEffect(effectIndex)
            }
        })
    }
}
