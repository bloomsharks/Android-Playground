package com.bloomhigh.playground

import android.graphics.Bitmap
import com.bumptech.glide.load.Key
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import org.wysaid.nativePort.CGENativeLibrary
import java.security.MessageDigest

class GPPTransformation(val filterName: String, val config: String) : BitmapTransformation() {

    companion object {
        const val ID = "GPPTransformation"
    }

    override fun updateDiskCacheKey(messageDigest: MessageDigest) {
        val key = (ID + filterName)
        messageDigest.update(key.toByteArray(Key.CHARSET))
    }

    override fun transform(
        pool: BitmapPool,
        toTransform: Bitmap,
        outWidth: Int,
        outHeight: Int
    ): Bitmap {
        println("GPPTransformation updateDiskCacheKey($config)")
        return CGENativeLibrary.filterImage_MultipleEffects(toTransform, config, 1.0f)
    }
}