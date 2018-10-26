package com.samsularifin.footballclub.main

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ProgressBar
import android.widget.Spinner
import com.google.gson.Gson
import com.samsularifin.footballclub.R
import com.samsularifin.footballclub.api.ApiRepository
import com.samsularifin.footballclub.db.FavoriteMatch
import com.samsularifin.footballclub.db.database
import com.samsularifin.footballclub.matchdetail.MatchDetailActivity

import com.samsularifin.footballclub.model.Match
import com.samsularifin.footballclub.utils.Constant
import com.samsularifin.footballclub.utils.invisible
import com.samsularifin.footballclub.utils.visible
import kotlinx.android.synthetic.main.fragment_match_item_list.*
import kotlinx.android.synthetic.main.fragment_match_item_list.view.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.intentFor
import org.jetbrains.anko.support.v4.onRefresh

/**
 * A fragment representing a list of Items.
 * Activities containing this fragment MUST implement the
 * [FavoriteMatchFragment.OnListFragmentInteractionListener] interface.
 */
class FavoriteMatchFragment : Fragment() {


    private var matches: MutableList<Match>? = mutableListOf()

    private lateinit var adapter: MatchItemRecyclerViewAdapter

    private lateinit var matchList: RecyclerView

    private lateinit var progressBar: ProgressBar

    private lateinit var spinner: Spinner

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_match_item_list, container, false)

        progressBar = view.progress_bar
        progressBar.invisible()
        spinner = view.league_spinner
        spinner.invisible()

        matchList = view.match_list
        matchList.layoutManager = LinearLayoutManager(context)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        adapter = MatchItemRecyclerViewAdapter(matches) {
            startActivity(intentFor<MatchDetailActivity>(Constant.INTENT_MATCHID to "${it?.matchId}"))
        }
        matchList.adapter = adapter

        getContent()

        pull_to_refresh.onRefresh {
            matches?.clear()
            getContent()
        }

    }

    private fun getContent(){
        activity?.database?.use {
            val result = select(FavoriteMatch.TABLE_FAVORITE_MATCH)
            val favorites = result.parseList(classParser<FavoriteMatch>())
            var matchList: MutableList<Match>? = mutableListOf()
            for (item: FavoriteMatch in favorites){
                var temp = Match(item.matchId, item.homeTeamId,item.awayTeamId,item.homeTeamName, item.awayTeamName, item.homeScore, item.awayScore,item.matchDate)
                matchList?.add(temp)
            }
            showMatchList(matchList)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onDetach() {
        super.onDetach()
    }


    fun showMatchList(data: List<Match>?) {
        if (data != null && data.size > 0){
            matches?.clear()
            matches?.addAll(data)
        }


        adapter.notifyDataSetChanged()
        pull_to_refresh.isRefreshing = false
    }
}
