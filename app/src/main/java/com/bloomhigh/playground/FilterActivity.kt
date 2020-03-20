package com.bloomhigh.playground

import android.os.Bundle
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.updateLayoutParams
import kotlinx.android.synthetic.main.activity_filter.*
import kotlin.math.max
import kotlin.math.min

class FilterActivity : AppCompatActivity(R.layout.activity_filter) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val tbilisi =
            "@curve R(2, 2)(16, 30)(72, 112)(135, 185)(252, 255)G(2, 1)(30, 42)(55, 84)(157, 207)(238, 249)B(1, 0)(26, 17)(67, 106)(114, 165)(231, 250)"
        val california =
            "@curve R(0, 0)(96, 61)(154, 177)(255, 255) @pixblend overlay 0.357 0.863 0.882 1 40"
        val newYork =
            "@curve R(40, 40)(86, 148)(255, 255)G(0, 28)(67, 140)(142, 214)(255, 255)B(0, 100)(103, 176)(195, 174)(255, 255) @adjust hsv 0.32 0 -0.5 -0.2 0 -0.4"

        viewPager.adapter = EffectsPagerAdapter(
            supportFragmentManager, listOf(
                tbilisi,
                california,
                newYork
            )
        )

//        viewPager.setPageTransformer(
//            true, object : ABaseTransformer() {
//
//                override fun onTransform(page: View, position: Float) {
//                    if (position <= 0) page.translationX = 0f
//                    else if (position <= 1) {
//                        page.translationX = (-page.width * (1 - position))
//                    }
//                }
//
//            }
//        )

        viewPager.setPageTransformer(true) { page, position ->
            val w = if (position < 0) 0F else (-page.width * position)
            val ww = min(1080, max(0, 1080 - (1080 + w.toInt())))

            page.translationX = w

            page.findViewById<ImageView>(R.id.mainImageView)
                .updateLayoutParams<ViewGroup.LayoutParams> {
                    println("WWW w $w $ww")
                    width = (1080 + w.toInt())
                }
        }
    }
}
