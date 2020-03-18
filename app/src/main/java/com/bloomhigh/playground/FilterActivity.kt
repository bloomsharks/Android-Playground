package com.bloomhigh.playground

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_filter.*
import org.wysaid.view.ImageGLSurfaceView

class FilterActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )


        setContentView(R.layout.activity_filter)

        val mImageView = findViewById<ImageGLSurfaceView>(R.id.mainImageView)

        val srcImage = BitmapFactory.decodeResource(resources, R.drawable.original)

        val tbilisi = "@curve R(2, 2)(16, 30)(72, 112)(135, 185)(252, 255)G(2, 1)(30, 42)(55, 84)(157, 207)(238, 249)B(1, 0)(26, 17)(67, 106)(114, 165)(231, 250)"
        val california = "@curve R(0, 0)(96, 61)(154, 177)(255, 255) @pixblend overlay 0.357 0.863 0.882 1 40"
        val newYork = "@curve R(40, 40)(86, 148)(255, 255)G(0, 28)(67, 140)(142, 214)(255, 255)B(0, 100)(103, 176)(195, 174)(255, 255) @adjust hsv 0.32 0 -0.5 -0.2 0 -0.4"

        mImageView.setSurfaceCreatedCallback {
            mImageView.setImageBitmap(srcImage)
            mImageView.setFilterWithConfig(tbilisi)
        }

        mImageView.displayMode = ImageGLSurfaceView.DisplayMode.DISPLAY_ASPECT_FIT

        val seekBar = findViewById<SeekBar>(R.id.globalRestoreSeekBar)
        seekBar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(
                seekBar: SeekBar,
                progress: Int,
                fromUser: Boolean
            ) {
                val intensity = progress / 100.0f
                mImageView.setFilterIntensity(intensity)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })

        btnTbilisi.setOnClickListener {
            mImageView.setFilterWithConfig(tbilisi)
        }
        btnCalifornia.setOnClickListener {
            mImageView.setFilterWithConfig(california)
        }
        btnNewYork.setOnClickListener {
            mImageView.setFilterWithConfig(newYork)
        }
    }
}
