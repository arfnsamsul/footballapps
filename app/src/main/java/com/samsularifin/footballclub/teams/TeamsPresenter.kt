package com.samsularifin.footballclub.teams
import com.google.gson.Gson
import com.samsularifin.footballclub.api.ApiRepository
import com.samsularifin.footballclub.utils.CoroutineContextProvider
import com.samsularifin.footballclub.api.TheSportDBApi
import com.samsularifin.footballclub.model.TeamListResponse
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg


class TeamsPresenter(private val view: TeamsView,
                     private val apiRepository: ApiRepository,
                     private val gson: Gson,
                     private val context: CoroutineContextProvider = CoroutineContextProvider()) {

    fun getTeamList(league: String?) {
        view.showLoading()

        async(UI){
            val data = bg {
                gson.fromJson(apiRepository
                        .doRequest(TheSportDBApi.getTeams(league)),
                        TeamListResponse::class.java
                )
            }
            view.showTeamList(data.await().teams)
            view.hideLoading()
        }

    }

}
