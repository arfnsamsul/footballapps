package com.samsularifin.footballclub.teamdetail

import android.util.Log
import com.google.gson.Gson
import com.samsularifin.footballclub.api.ApiRepository
import com.samsularifin.footballclub.api.TheSportDBApi
import com.samsularifin.footballclub.model.PlayerListResponse
import com.samsularifin.footballclub.model.TeamListResponse
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class TeamDetailPlayerPresenter(private val view: TeamDetailPlayerView,
                                private val apiRepository: ApiRepository,
                                private val gson: Gson) {

    fun getTeamPlayers(teamId: String) {
        view.showLoading()

        async(UI){
            val data = bg {
                gson.fromJson(apiRepository
                        .doRequest(TheSportDBApi.getTeamPlayers(teamId)),
                        PlayerListResponse::class.java
                )
            }

            view.showPlayerList(data.await().players)
            view.hideLoading()
        }
    }
}
