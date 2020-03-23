package com.bloomhigh.playground

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter

class EffectsPagerAdapter(
    private val effects: List<Effect>
) : PagerAdapter() {

    override fun getPageTitle(position: Int): CharSequence? {
        return effects[position].name
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater = LayoutInflater.from(container.context)
        val layout = inflater.inflate(R.layout.view_filter_title, container, false)
        val v = layout.findViewById<TextView>(R.id.tvTitle)
        v.text = getPageTitle(position)
        container.addView(layout)
        return layout
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as FrameLayout)
    }

    override fun getCount(): Int {
        return effects.size
    }

}