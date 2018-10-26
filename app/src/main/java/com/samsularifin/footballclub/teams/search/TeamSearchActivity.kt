package com.samsularifin.footballclub.teams.search

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import com.google.gson.Gson
import com.samsularifin.footballclub.R
import com.samsularifin.footballclub.api.ApiRepository
import com.samsularifin.footballclub.model.Team
import com.samsularifin.footballclub.teamdetail.TeamDetailActivity
import com.samsularifin.footballclub.teams.TeamsAdapter
import com.samsularifin.footballclub.utils.hideKeyboard
import com.samsularifin.footballclub.utils.invisible
import com.samsularifin.footballclub.utils.visible
import kotlinx.android.synthetic.main.activity_team_search.*
import org.jetbrains.anko.sdk15.coroutines.onClick
import org.jetbrains.anko.sdk15.coroutines.onEditorAction
import org.jetbrains.anko.startActivity


class TeamSearchActivity : AppCompatActivity() {

    private lateinit var listAdapter: TeamsAdapter
    private var teams: MutableList<Team> = mutableListOf()
    private lateinit var presenter: TeamSearchPresenter
    private lateinit var keyword: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_search)

        supportActionBar?.title = "Team Search"

        var resultList = team_list_result
        var btnSearch = btn_search
        keyword = input_keyword

        listAdapter = TeamsAdapter(teams) {
            startActivity<TeamDetailActivity>("id" to "${it.teamId}")
        }

        resultList.adapter = listAdapter
        resultList.layoutManager = LinearLayoutManager(this)

        val request = ApiRepository()
        val gson = Gson()

        presenter = TeamSearchPresenter(this, request, gson)

        keyword.onEditorAction { v, actionId, event ->
            if(actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_NEXT){
                prepareSearch()
                hideKeyboard()
            }
        }

        btnSearch.onClick {
            prepareSearch()
        }

        hideLoading()
    }

    private fun prepareSearch(){
        if (keyword.text.toString().length > 0){
            teams.clear()
            listAdapter.notifyDataSetChanged()
            presenter.searchTeam(keyword.text.toString())
        }
    }

    fun showLoading(){
        progress_bar.visible()
    }

    fun hideLoading(){
        progress_bar.invisible()
    }

    fun showTeamList(data: List<Team>) {
        //swipeRefresh.isRefreshing = false
        teams.clear()
        teams.addAll(data)
        listAdapter.notifyDataSetChanged()
    }
}
