package com.samsularifin.footballclub.match.search

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import com.google.gson.Gson
import com.samsularifin.footballclub.R
import com.samsularifin.footballclub.api.ApiRepository
import com.samsularifin.footballclub.main.MatchItemRecyclerViewAdapter
import com.samsularifin.footballclub.matchdetail.MatchDetailActivity
import com.samsularifin.footballclub.model.Match
import com.samsularifin.footballclub.utils.Constant
import com.samsularifin.footballclub.utils.hideKeyboard
import com.samsularifin.footballclub.utils.invisible
import com.samsularifin.footballclub.utils.visible
import kotlinx.android.synthetic.main.activity_match_search.*
import org.jetbrains.anko.sdk15.coroutines.onClick
import org.jetbrains.anko.sdk15.coroutines.onEditorAction
import org.jetbrains.anko.startActivity


class MatchSearchActivity : AppCompatActivity() {

    private lateinit var listAdapter: MatchItemRecyclerViewAdapter
    private var matches: MutableList<Match> = mutableListOf()
    private lateinit var presenter: MatchSearchPresenter
    private lateinit var keyword: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match_search)

        supportActionBar?.title = "Match Search"

        var resultList = match_list_result
        var btnSearch = btn_search
        keyword = input_keyword

        listAdapter = MatchItemRecyclerViewAdapter(matches){
            startActivity<MatchDetailActivity>(Constant.INTENT_MATCHID to "${it?.matchId}")
        }

        //matches.add()

        //showMatchList(listOf(Match("asd","home","away","home","away","1","0", "2002-05-04")))

        resultList.adapter = listAdapter
        resultList.layoutManager = LinearLayoutManager(this)

        val request = ApiRepository()
        val gson = Gson()

        presenter = MatchSearchPresenter(this, request, gson)

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
            matches.clear()
            listAdapter.notifyDataSetChanged()
            presenter.searchMatch(keyword.text.toString())
        }
    }

    fun showLoading(){
        progress_bar.visible()
    }

    fun hideLoading(){
        progress_bar.invisible()
    }

    fun showMatchList(data: List<Match>?) {
        //swipeRefresh.isRefreshing = false
        matches.clear()
        if (data != null){
            matches.addAll(data)
        }
        listAdapter.notifyDataSetChanged()
    }
}
