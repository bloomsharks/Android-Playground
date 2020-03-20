package com.bloomhigh.playground

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ClipDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MotionEvent
import android.widget.FrameLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import kotlinx.android.synthetic.main.view_image_conainer.view.*

class ImageConainter @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    var screenWidth: Int = 0
    var screenHeight: Int = 0

    var clipDrawable: ClipDrawable? = null

    init {
        screenWidth = context.resources.displayMetrics.widthPixels
        screenHeight = context.resources.displayMetrics.heightPixels
        LayoutInflater.from(context).inflate(R.layout.view_image_conainer, this)

        postDelayed({load()}, 100)
    }

    private fun load() {
        Glide.with(this)
            .load(R.drawable.lite)
            .skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .transform(
                GPPTransformation(
                    filterName = "tbilisi",
                    config = Effects.tbilisi
                )
            )
            .into(ivBefore)

        Glide.with(this)
            .asBitmap()
            .skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .load("https://i.imgur.com/Cgu6X3J.jpg")
//            .transform(
//                GPPTransformation(
//                    filterName = "london",
//                    config = Effects.london
//                )
//            )
            .into(object: CustomTarget<Bitmap>(ivBefore.width, ivBefore.height) {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
//                    val tmpBitmap = getScaledBitmap(resource)
                    val bitmapDrawable = BitmapDrawable(context.resources, resource)
                    clipDrawable = ClipDrawable(bitmapDrawable, Gravity.START, ClipDrawable.HORIZONTAL)
                    ivAfter.setImageDrawable(clipDrawable)
                    clipDrawable?.level = 5000
                }

                override fun onLoadCleared(placeholder: Drawable?) {

                }
            })
    }


    override fun onTouchEvent(event: MotionEvent): Boolean {
        return when(event.action) {
            MotionEvent.ACTION_DOWN -> {
                println("down ${event.getX(0)}")
                true
            }
            MotionEvent.ACTION_MOVE -> {
                val x = event.getX(0)
                val lvl = (10_000 / (screenWidth / x)).toInt()
                println("move $screenWidth $x $lvl")
                clipDrawable?.level = lvl
                true
            }
            else ->super.onTouchEvent(event)
        }

    }
}