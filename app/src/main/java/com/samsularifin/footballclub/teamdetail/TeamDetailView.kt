package com.samsularifin.footballclub.teamdetail

import com.samsularifin.footballclub.model.Team

interface TeamDetailView {
    fun showLoading()
    fun hideLoading()
    fun showTeamDetail(data: List<Team>)
}
