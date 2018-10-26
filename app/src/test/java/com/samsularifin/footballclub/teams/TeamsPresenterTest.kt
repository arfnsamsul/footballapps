package com.samsularifin.footballclub.teams

import com.google.gson.Gson
import com.samsularifin.footballclub.api.ApiRepository
import com.samsularifin.footballclub.TestContextProvider
import com.samsularifin.footballclub.api.TheSportDBApi
import com.samsularifin.footballclub.model.Team
import com.samsularifin.footballclub.model.TeamListResponse
import org.junit.Test

import org.junit.Before
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class TeamsPresenterTest {

    @Mock
    private
    lateinit var view: TeamsView

    @Mock
    private
    lateinit var gson: Gson

    @Mock
    private
    lateinit var apiRepository: ApiRepository

    private lateinit var presenter: TeamsPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = TeamsPresenter(view, apiRepository, gson, TestContextProvider())
    }


    @Test
    fun testGetTeamList() {
        val teams: MutableList<Team> = mutableListOf()
        val response = TeamListResponse(teams)
        val league = "English Premiere League"

        `when`(gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getTeams(league)),
                TeamListResponse::class.java
        )).thenReturn(response)

        presenter.getTeamList(league)
        verify(view).showLoading()
        verify(view).showTeamList(teams)
        verify(view).hideLoading()
    }

}