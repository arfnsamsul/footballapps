package com.samsularifin.footballclub.main

import com.google.gson.Gson
import com.samsularifin.footballclub.main.MatchesView
import com.samsularifin.footballclub.model.MatchListResponse
import com.samsularifin.footballclub.api.ApiRepository
import com.samsularifin.footballclub.api.TheSportDBApi
import com.samsularifin.footballclub.utils.CoroutineContextProvider
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg


class MatchesPresenter(private val view: MatchesView,
                       private val apiRepository: ApiRepository,
                       private val gson: Gson,
                       private val context: CoroutineContextProvider = CoroutineContextProvider()) {

    fun getNextMatchList(leagueId: String?) {
        view.showLoading()

        async(context.main){
            val data = bg {
                gson.fromJson(apiRepository
                        .doRequest(TheSportDBApi.getEventsNextLeague(leagueId ?: "4328")),
                        MatchListResponse::class.java
                )
            }
            view.showMatchList(data.await().matches)
            view.hideLoading()
        }
    }

    fun getPastMatchList(leagueId: String?) {
        view.showLoading()

        async(context.main){
            val data = bg {
                gson.fromJson(apiRepository
                        .doRequest(TheSportDBApi.getEventsPastLeague(leagueId ?: "4328")),
                        MatchListResponse::class.java
                )
            }

            view.showMatchList(data.await().matches)
            view.hideLoading()
        }
    }
}