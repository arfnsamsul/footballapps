package com.samsularifin.footballclub.main


import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.samsularifin.footballclub.utils.Constant

class MatchPagerAdapter (fragmentManager: FragmentManager?) :FragmentPagerAdapter(fragmentManager) {
    private var tabTitles = arrayOf(
            Constant.LABEL_LAST_MATCH,
            Constant.LABEL_NEXT_MATCH
    )

    override fun getItem(position: Int): Fragment {
        return MatchItemFragment.newInstance(position)
    }

    override fun getCount(): Int {
        return tabTitles.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return tabTitles[position]
    }
}