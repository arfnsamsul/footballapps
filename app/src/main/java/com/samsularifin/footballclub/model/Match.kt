package com.samsularifin.footballclub.model

import com.google.gson.annotations.SerializedName

data class Match(
    @SerializedName("idEvent")
    var matchId: String? = null,

    @SerializedName("idHomeTeam")
    var homeTeamId: String? = null,

    @SerializedName("idAwayTeam")
    var awayTeamId: String? = null,

    @SerializedName("strHomeTeam")
    var homeTeam: String? = null,

    @SerializedName("strAwayTeam")
    var awayTeam: String? = null,

    @SerializedName("intHomeScore")
    var homeScore: String? = null,

    @SerializedName("intAwayScore")
    var awayScore: String? = null,

    @SerializedName("dateEvent")
    var matchDate: String? = null,

    @SerializedName("strHomeGoalDetails")
    var homeGoalDetails: String? = null,

    @SerializedName("strAwayGoalDetails")
    var awayGoalDetails: String? = null,

    @SerializedName("intHomeShots")
    var homeShots: String? = null,

    @SerializedName("intAwayShots")
    var awayShots: String? = null,

    @SerializedName("strHomeLineupGoalkeeper")
    var homeGoalkeeper: String? = null,

    @SerializedName("strAwayLineupGoalkeeper")
    var awayGoalkeeper: String? = null,

    @SerializedName("strHomeLineupDefense")
    var homeDefense: String? = null,

    @SerializedName("strAwayLineupDefense")
    var awayDefense: String? = null,

    @SerializedName("strHomeLineupMidfield")
    var homeMidfield: String? = null,

    @SerializedName("strAwayLineupMidfield")
    var awayMidfield: String? = null,

    @SerializedName("strHomeLineupForward")
    var homeForward: String? = null,

    @SerializedName("strAwayLineupForward")
    var awayForward: String? = null,

    @SerializedName("strHomeLineupSubstitutes")
    var homeSubstitutes: String? = null,

    @SerializedName("strAwayLineupSubstitutes")
    var awaySubstitutes: String? = null
)
