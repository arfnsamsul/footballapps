package com.samsularifin.footballclub.matchdetail

import com.samsularifin.footballclub.model.Match

interface MatchDetailView {
    fun showLoading()
    fun hideLoading()
    fun attachData(data: Match?)
    fun attachHomeLogo(homeLogoUrl: String?)
    fun attachAwayLogo(awayLogoUrl: String?)
    //fun showMatchList(data: List<Match>?)
}