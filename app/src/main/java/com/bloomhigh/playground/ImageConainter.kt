package com.bloomhigh.playground

import android.content.Context
import android.graphics.drawable.ClipDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import kotlinx.android.synthetic.main.view_image_conainer.view.*

class ImageConainter @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    var screenWidth: Int = 0
    var screenHeight: Int = 0

    init {
        screenWidth = context.resources.displayMetrics.widthPixels
        screenHeight = context.resources.displayMetrics.heightPixels
        LayoutInflater.from(context).inflate(R.layout.view_image_conainer, this)

        postDelayed({ load() }, 100)
    }

    private fun loadClipDrawable(effect: Effect) {
        println("PRKK loadClipDrawable ${effect.name}")
        Glide.with(this)
            .asDrawable()
            .load(R.drawable.lite)
            .transform(GPPTransformation(effect))
            .dontAnimate()
            .into(object : CustomTarget<Drawable>(ivBefore.width, ivBefore.height) {
                override fun onResourceReady(
                    resource: Drawable,
                    transition: Transition<in Drawable>?
                ) {
                    ClipDrawable(resource, Gravity.START, ClipDrawable.HORIZONTAL).also {
                        it.level = 0//10_000
                        ivAfter.setImageDrawable(it)
                    }
                }

                override fun onLoadCleared(placeholder: Drawable?) {

                }
            })
    }

    private var callback: OnEffectSelectedListener? = null

    fun setOnEffectSelectedListener(listener: OnEffectSelectedListener) {
        callback = listener
    }

    private var hideTitleRunnable = {
        tvTitle.visibility = View.INVISIBLE
    }

    private var showtitleRunnable = {
        tvTitle.visibility = View.VISIBLE
    }

    private fun applyEffect(index: Int) {
//        loadClipDrawable(Effects[index])
        callback?.onEffectSelected(index)

        tvTitle.text = Effects[index].name

        tvTitle.removeCallbacks(showtitleRunnable)
        tvTitle.removeCallbacks(hideTitleRunnable)


        tvTitle.postDelayed(showtitleRunnable, 300)
        tvTitle.postDelayed(hideTitleRunnable, 1000)

        println("PKKKR loading effect ${Effects[index].name}")
        Glide.with(this)
            .asDrawable()
            .load(R.drawable.lite)
            .transform(GPPTransformation(Effects[index]))
            .dontAnimate()
            .into(object: CustomTarget<Drawable>(ivBefore.width, ivBefore.height) {
                override fun onLoadCleared(placeholder: Drawable?) {
                }

                override fun onResourceReady(
                    resource: Drawable,
                    transition: Transition<in Drawable>?
                ) {
                    ivBefore.setImageDrawable(resource)
                    ivPlaceHolder.setImageDrawable(resource)
                }

            })
    }

    private fun load() {
        viewPager.adapter = EffectsPagerAdapter(Effects)
        viewPager.setPageTransformer(false) { page, position ->
            if (position in 0.0F..1.0F) {
                val lvl = 10_000 * position
                (ivAfter.drawable as? ClipDrawable)?.level = lvl.toInt()

                if (position == 1.0F || position == 0.0F) {
                    println("PRKK settle at $position")
                    applyEffect(viewPager.currentItem)
                }

            }
        }
    }

    fun setSelectedEffect(effectIndex: Int) {
        applyEffect(effectIndex)
    }
}