package com.samsularifin.footballclub.teams

import com.samsularifin.footballclub.model.Team

interface TeamsView {
    fun showLoading()
    fun hideLoading()
    fun showTeamList(data: List<Team>)
}
