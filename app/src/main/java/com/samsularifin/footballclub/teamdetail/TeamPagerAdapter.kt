package com.samsularifin.footballclub.main


import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.samsularifin.footballclub.model.Team
import com.samsularifin.footballclub.utils.Constant



class TeamPagerAdapter (fragmentManager: FragmentManager?) :FragmentPagerAdapter(fragmentManager) {
    private var tabTitles = arrayOf(
            Constant.LABEL_OVERVIEW,
            Constant.LABEL_PLAYERS
    )

    override fun getItem(position: Int): Fragment {
        if (position == 0){
            return TeamDetailOverviewFragment()
        }else{
            return TeamDetailPlayerFragment()
        }
    }

    override fun getCount(): Int {
        return tabTitles.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return tabTitles[position]
    }
}