package com.samsularifin.footballclub.matchdetail

import com.google.gson.Gson
import com.samsularifin.footballclub.api.ApiRepository
import com.samsularifin.footballclub.api.TheSportDBApi
import com.samsularifin.footballclub.model.MatchListResponse
import com.samsularifin.footballclub.model.TeamListResponse
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class MatchDetailPresenter(private val view: MatchDetailView,
                           private val apiRepository: ApiRepository,
                           private val gson: Gson) {

    fun getMatchDetail(matchId: String) {
        view.showLoading()
        doAsync {

                val data = gson.fromJson(apiRepository.doRequest(TheSportDBApi.getEventDetail(matchId)), MatchListResponse::class.java)

                uiThread {
                    view.hideLoading()
                    if (data.matches != null && data.matches.size > 0){
                        view.attachData(data.matches[0])
                    }
                }
        }
    }

    fun getHomeTeamBadge(teamId: String?) {
        doAsync {

            val data = gson.fromJson(apiRepository.doRequest(TheSportDBApi.getTeamDetail(teamId)), TeamListResponse::class.java)

            uiThread {
                if (data.teams != null && data.teams.size > 0){
                    view.attachHomeLogo(data.teams[0].teamBadge)
                }
            }
        }
    }
    fun getAwayTeamBadge(teamId: String?) {
        doAsync {

            val data = gson.fromJson(apiRepository.doRequest(TheSportDBApi.getTeamDetail(teamId)), TeamListResponse::class.java)

            uiThread {
                if (data.teams != null && data.teams.size > 0){
                    view.attachAwayLogo(data.teams[0].teamBadge)
                }
            }
        }
    }
}