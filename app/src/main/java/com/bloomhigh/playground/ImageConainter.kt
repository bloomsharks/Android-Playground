package com.bloomhigh.playground

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.drawable.ClipDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import kotlinx.android.synthetic.main.view_image_conainer.view.*

class ImageConainter @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    var screenHeight: Int = 0

    init {
        screenHeight = context.resources.displayMetrics.heightPixels
        LayoutInflater.from(context).inflate(R.layout.view_image_conainer, this)
    }

    private fun loadClipDrawable(effect: Effect) {
        println("PRKK loadClipDrawable ${effect.name}")
        Glide.with(this)
            .asDrawable()
            .load(R.drawable.photo)
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
        if (index > 0)
            loadClipDrawable(Effects[index - 1])
        callback?.onEffectSelected(index)

        tvTitle.text = Effects[index].name

        tvTitle.removeCallbacks(showtitleRunnable)
        tvTitle.removeCallbacks(hideTitleRunnable)


        tvTitle.postDelayed(showtitleRunnable, 300)
        tvTitle.postDelayed(hideTitleRunnable, 1000)

        println("PKKKR loading effect ${Effects[index].name}")
        Glide.with(this)
            .asDrawable()
            .load(R.drawable.photo)
            .transform(GPPTransformation(Effects[index]))
            .dontAnimate()
            .into(object : CustomTarget<Drawable>(ivBefore.width, ivBefore.height) {
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

    fun setSelectedEffect(effectIndex: Int) {
        applyEffect(effectIndex)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val d = (ivAfter.drawable as? ClipDrawable)
        if (d != null) {
            return when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    true
                }
                MotionEvent.ACTION_MOVE -> {
                    val x = event.getX(0)
                    val lvl = (10_000 / (ivBefore.width / x)).toInt()
                    d.level = lvl
                    true
                }
                MotionEvent.ACTION_UP -> {
                    val limit = 3500
                    println("UPPP at ${d.level} limit $limit")
                    if (d.level <= 5_000) {
                        animateSnap(d.level, 0, d)
                    } else {
                        animateSnap(d.level, 10_000, d)
                    }
                    true
                }
                else -> super.onTouchEvent(event)
            }
        } else {
            return super.onTouchEvent(event)
        }
    }

    private fun animateSnap(from: Int, to: Int,d: ClipDrawable) {
        ValueAnimator.ofInt(from, to).apply {
            setDuration(300)
            addUpdateListener {
                d.level = it.animatedValue as Int
            }
            start()
        }
    }
}