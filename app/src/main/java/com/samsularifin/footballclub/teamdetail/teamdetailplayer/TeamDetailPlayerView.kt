package com.samsularifin.footballclub.teamdetail

import com.samsularifin.footballclub.model.Player

interface TeamDetailPlayerView {
    fun showLoading()
    fun hideLoading()
    fun showPlayerList(data: List<Player>?)
}
