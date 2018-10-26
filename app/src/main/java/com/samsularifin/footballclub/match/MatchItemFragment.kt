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
 * [MatchItemFragment.OnListFragmentInteractionListener] interface.
 */
class MatchItemFragment : Fragment(), MatchesView {


    private var matches: MutableList<Match>? = mutableListOf()

    private lateinit var adapter: MatchItemRecyclerViewAdapter

    private lateinit var matchList: RecyclerView


    private var columnCount = 0 // 0 = last, 1 = next

    private lateinit var presenter: MatchesPresenter

    private lateinit var progressBar: ProgressBar

    private lateinit var spinner: Spinner

    private var selectedLeagueId: String = "4328"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_match_item_list, container, false)

        progressBar = view.progress_bar
        spinner = view.league_spinner

        val spinnerItems = resources.getStringArray(R.array.leaguesName)

        val leaguesId = resources.getStringArray(R.array.leaguesId)

        val spinnerAdapter = ArrayAdapter(ctx, android.R.layout.simple_spinner_dropdown_item, spinnerItems)
        spinner.adapter = spinnerAdapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                selectedLeagueId = leaguesId[spinner.selectedItemPosition]
                getContent(selectedLeagueId)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        matchList = view.match_list
        matchList.layoutManager = LinearLayoutManager(context)



        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //adapter = MatchItemRecyclerViewAdapter(matches, listener)
        adapter = MatchItemRecyclerViewAdapter(matches) {
            //ctx.startActivity<TeamDetailActivity>("id" to "${it.teamId}")
            //ctx.startActivity<TeamDetailActivity>()
            startActivity(intentFor<MatchDetailActivity>(Constant.INTENT_MATCHID to "${it?.matchId}"))
        }
        matchList.adapter = adapter

        getContent(selectedLeagueId)

        pull_to_refresh.onRefresh {
            matches?.clear()
            getContent(selectedLeagueId)
        }

    }

    private fun getContent(leagueId: String?){
        val request = ApiRepository()
        val gson = Gson()
        presenter = MatchesPresenter(this, request, gson)

        if(columnCount == 0){
            presenter.getPastMatchList(leagueId)
        }else if(columnCount == 1){
            presenter.getNextMatchList(leagueId)
        }else{
            showLoading()
            activity?.database?.use {
                val result = select(FavoriteMatch.TABLE_FAVORITE_MATCH)
                val favorites = result.parseList(classParser<FavoriteMatch>())
                var matchList: MutableList<Match>? = mutableListOf()
                for (item: FavoriteMatch in favorites){
                    var temp = Match(item.matchId, item.homeTeamId,item.awayTeamId,item.homeTeamName, item.awayTeamName, item.homeScore, item.awayScore,item.matchDate)
                    matchList?.add(temp)
                }
                showMatchList(matchList)
                hideLoading()
            }
        }

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        /*if (context is OnListFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnListFragmentInteractionListener")
        }*/
    }

    override fun onDetach() {
        super.onDetach()
    }

    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }


    override fun showMatchList(data: List<Match>?) {
        //swipeRefresh.isRefreshing = false

        if (data != null && data.size > 0){
            matches?.clear()
            matches?.addAll(data)
        }


        adapter.notifyDataSetChanged()
        pull_to_refresh.isRefreshing = false
    }

    companion object {

        const val ARG_COLUMN_COUNT = "column-count"

        @JvmStatic
        fun newInstance(columnCount: Int) =
                MatchItemFragment().apply {
                    arguments = Bundle().apply {
                        putInt(ARG_COLUMN_COUNT, columnCount)
                    }
                }
    }
}
