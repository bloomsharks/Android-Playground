package com.bloomhigh.playground

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide

class EffectFragment : Fragment(R.layout.fragment_effect) {

    companion object {
        fun newInstance(filterName: String, config: String): EffectFragment {
            val effectFragment = EffectFragment()
            effectFragment.arguments = Bundle().apply {
                putString("config", config)
                putString("filterName", filterName)
            }
            return effectFragment
        }
    }

    lateinit var mImageView: ImageView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mImageView = view.findViewById(R.id.mainImageView)


        Glide.with(view)
            .load(R.drawable.lite)
            .transform(
                GPPTransformation(
                    filterName = arguments!!.getString("filterName")!!,
                    config = arguments!!.getString("config")!!
                )
            )
            .into(mImageView)

//        mImageView.setZOrderMediaOverlay(true)
//        mImageView.holder.setFormat(PixelFormat.TRANSLUCENT)
//
//        mImageView.displayMode = ImageGLSurfaceView.DisplayMode.DISPLAY_ASPECT_FIT
//        mImageView.requestRender()
//        mImageView.release()
    }

    override fun onPause() {
        super.onPause()
//        mImageView.release()
    }


}