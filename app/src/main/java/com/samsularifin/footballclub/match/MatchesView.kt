package com.samsularifin.footballclub.main

import com.samsularifin.footballclub.model.Match

interface MatchesView {
    fun showLoading()
    fun hideLoading()
    fun showMatchList(data: List<Match>?)
}