package com.bloomhigh.playground

import android.content.Context
import android.graphics.drawable.ClipDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
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

    private var currentPlaceHolderIndex = -1

    private fun loadPlaceholder(index: Int) {
        if (index > 0) {
            val modIndex = index - 1
            if (modIndex == currentPlaceHolderIndex) {
                return
            }
            println("PKKKR loading placeholder ${Effects[modIndex].name}")
            Glide.with(this)
                .load(R.drawable.lite)
                .transform(GPPTransformation(Effects[modIndex]))
                .dontAnimate()
                .into(ivPlaceHolder)
            currentPlaceHolderIndex = modIndex
        } else {
            if (index == currentPlaceHolderIndex) {
                return
            }
            println("PKKKR loading placeholder ${Effects[index].name}")
            Glide.with(this)
                .load(R.drawable.lite)
                .transform(GPPTransformation(Effects[index]))
                .dontAnimate()
                .into(ivPlaceHolder)
            currentPlaceHolderIndex = index
        }
    }

    private var currentEffectIndex = -1

    private fun applyEffect(index: Int) {
        if (index == currentEffectIndex) {
            return
        }

//        loadClipDrawable(Effects[index])

        loadPlaceholder(index)

        println("PKKKR loading effect ${Effects[index].name}")
        Glide.with(this)
            .load(R.drawable.lite)
            .transform(GPPTransformation(Effects[index]))
            .dontAnimate()
            .into(ivBefore)
        currentEffectIndex = index
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
                    page.findViewById<TextView>(R.id.tvTitle).apply {
                        visibility = View.VISIBLE
                        postDelayed({
                            visibility = View.GONE
                        }, 500)
                    }
                }

            }
        }
    }

//    override fun onTouchEvent(event: MotionEvent): Boolean {
//        return when(event.action) {
//            MotionEvent.ACTION_DOWN -> {
//                println("down ${event.getX(0)}")
//                true
//            }
//            MotionEvent.ACTION_MOVE -> {
//                val x = event.getX(0)
//                val lvl = (10_000 / (screenWidth / x)).toInt()
//                println("move $screenWidth $x $lvl")
//                clipDrawable?.level = lvl
//                true
//            }
//            else ->super.onTouchEvent(event)
//        }
//    }
}