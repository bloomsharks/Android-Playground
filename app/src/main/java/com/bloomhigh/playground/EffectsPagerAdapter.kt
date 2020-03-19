package com.bloomhigh.playground

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class EffectsPagerAdapter(
    fragmentManager: FragmentManager,
    private val effects: List<String>
) : FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getItem(position: Int): Fragment {
        return EffectFragment.newInstance(getPageTitle(position).toString(), effects[position])
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position) {
            0 -> "Tbilisi"
            1 -> "California"
            else -> "New York"
        }
    }

    override fun getCount(): Int {
        return 3
    }

}