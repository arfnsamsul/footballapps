package com.samsularifin.footballclub.teams.search
import com.google.gson.Gson
import com.samsularifin.footballclub.api.ApiRepository
import com.samsularifin.footballclub.api.TheSportDBApi
import com.samsularifin.footballclub.model.TeamListResponse
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg


class TeamSearchPresenter(private val view: TeamSearchActivity,
                          private val apiRepository: ApiRepository,
                          private val gson: Gson
                          ) {

    fun searchTeam(keyword: String) {
        view.showLoading()

        async(UI){
            val data = bg {
                gson.fromJson(apiRepository
                        .doRequest(TheSportDBApi.searchTeam(keyword)),
                        TeamListResponse::class.java
                )
            }
            view.showTeamList(data.await().teams)
            view.hideLoading()
        }

    }

}
