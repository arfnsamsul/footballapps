package com.samsularifin.footballclub.match

import android.content.Context
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.*

import com.samsularifin.footballclub.R
import com.samsularifin.footballclub.R.id.search
import com.samsularifin.footballclub.R.menu.search_menu
import com.samsularifin.footballclub.main.MatchPagerAdapter
import com.samsularifin.footballclub.match.search.MatchSearchActivity
import kotlinx.android.synthetic.main.fragment_matches.view.*
import org.jetbrains.anko.support.v4.startActivity


class MatchesFragment : Fragment() {


    private lateinit var viewPager: ViewPager
    private lateinit var pagerAdapter: MatchPagerAdapter
    private lateinit var tabLayoutMatch: TabLayout
    private lateinit var myContext: Context

    private var menuItem: Menu? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_matches, container, false)

        viewPager = view.view_pager_main
        tabLayoutMatch = view.tab_layout_main

        pagerAdapter = MatchPagerAdapter(childFragmentManager)
        viewPager.adapter = pagerAdapter

        tabLayoutMatch.setupWithViewPager(viewPager)

        setHasOptionsMenu(true)

        return view
    }

    override fun onAttach(context: Context) {
        myContext = context
        super.onAttach(context)
    }

    override fun onDetach() {
        super.onDetach()
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(search_menu, menu)
        menuItem = menu
        return
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {

            search -> {
                startActivity<MatchSearchActivity>()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
