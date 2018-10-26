package com.samsularifin.footballclub.main

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.samsularifin.footballclub.R
import kotlinx.android.synthetic.main.fragment_team_detail_overview.view.*
import org.greenrobot.eventbus.Subscribe
import com.samsularifin.footballclub.model.MessageEvent
import org.greenrobot.eventbus.ThreadMode
import org.greenrobot.eventbus.EventBus





class TeamDetailOverviewFragment : Fragment() {



    private lateinit var teamDescription: TextView
    private lateinit var mView:View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(R.layout.fragment_team_detail_overview, container, false)
        teamDescription = mView.team_description
        return mView
    }

    // This method will be called when a MessageEvent is posted (in the UI thread for Toast)
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: MessageEvent) {
        mView.team_description.text = event.teamDesc
    }

    /*// This method will be called when a SomeOtherEvent is posted
    @Subscribe
    fun handleSomethingElse(event: SomeOtherEvent) {
        doSomethingWith(event)
    }*/

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }
}
