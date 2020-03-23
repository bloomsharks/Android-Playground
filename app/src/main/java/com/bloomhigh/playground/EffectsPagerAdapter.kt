package com.bloomhigh.playground

import android.view.View
import android.view.ViewGroup
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
        val v = View(container.context)
        container.addView(v)
        return v
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun getCount(): Int {
        return effects.size
    }

}