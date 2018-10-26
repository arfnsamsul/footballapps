package com.samsularifin.footballclub.main

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import com.samsularifin.footballclub.playerdetail.PlayerDetailActivity
import com.samsularifin.footballclub.model.MessageEvent
import com.samsularifin.footballclub.R
import com.samsularifin.footballclub.api.ApiRepository
import com.samsularifin.footballclub.model.Player
import com.samsularifin.footballclub.teamdetail.TeamDetailPlayerPresenter
import com.samsularifin.footballclub.teamdetail.TeamDetailPlayerView
import com.samsularifin.footballclub.utils.Constant
import kotlinx.android.synthetic.main.fragment_team_detail_player_list.view.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.jetbrains.anko.support.v4.startActivity


class TeamDetailPlayerFragment : Fragment(), TeamDetailPlayerView {

    private lateinit var mView:View
    private var playersData: MutableList<Player>? = mutableListOf()
    private lateinit var listAdapter: TeamDetailPlayerRecyclerViewAdapter
    private lateinit var presenter: TeamDetailPlayerPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(R.layout.fragment_team_detail_player_list, container, false)

        val request = ApiRepository()
        val gson = Gson()
        presenter = TeamDetailPlayerPresenter(this,request, gson)

        listAdapter = TeamDetailPlayerRecyclerViewAdapter(playersData){
            //listener
            var parsedData = gson.toJson(it)
            //startActivity(intentFor<PlayerDetailActivity>(Constant.INTENT_PLAYER_DATA to parsedData))
            //startActivity(intentFor<PlayerDetailActivity>(Constant.INTENT_PLAYER_DATA to "ulalalala"))
            startActivity<PlayerDetailActivity>(Constant.INTENT_PLAYER_DATA to parsedData)
        }

        mView.player_list.layoutManager = LinearLayoutManager(context)
        mView.player_list.adapter = listAdapter
        return mView
    }

    // This method will be called when a MessageEvent is posted (in the UI thread for Toast)
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: MessageEvent) {
        presenter.getTeamPlayers(event.teamId)
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    override fun showPlayerList(data: List<Player>?) {
        if (data != null ){
            playersData?.clear()
            playersData?.addAll(data)
            listAdapter.notifyDataSetChanged()
        }
    }
}
