package com.samsularifin.footballclub.match.search
import android.util.Log
import com.google.gson.Gson
import com.samsularifin.footballclub.api.ApiRepository
import com.samsularifin.footballclub.api.TheSportDBApi
import com.samsularifin.footballclub.model.MatchListResponse
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg


class MatchSearchPresenter(private val view: MatchSearchActivity,
                           private val apiRepository: ApiRepository,
                           private val gson: Gson
                          ) {

    fun searchMatch(keyword: String) {
        view.showLoading()

        async(UI){
            val data = bg {
                var asd =apiRepository
                        .doRequest(TheSportDBApi.searchMatch(keyword))
                gson.fromJson(asd,
                        MatchListResponse::class.java
                )
            }
            //Log.d("asdasd", "keyword: $keyword, pjg: ${data.await().matches?.size}")
            view.showMatchList(data.await().matches)
            view.hideLoading()
        }

    }

}
