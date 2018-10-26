package com.samsularifin.footballclub.utils

import com.samsularifin.footballclub.model.League

object Constant {
    val LABEL_NEXT_MATCH = "NEXT MATCH"
    val LABEL_LAST_MATCH = "LAST MATCH"
    val LABEL_FAVOURITES = "FAVOURITES"

    val INTENT_MATCHID = "matchId"
    val INTENT_PLAYER_DATA = "playerData"

    val LABEL_OVERVIEW = "OVERVIEW"
    val LABEL_PLAYERS = "PLAYERS"

    val LABEL_MATCHES = "MATCHES"
    val LABEL_TEAMS = "TEAMS"

    val LEAGUES = listOf(
        League("4328", "English Premier League"),
        League("4329", "English League Championship"),
        League("4331", "German Bundesliga"),
        League("4332", "Italian Serie A"),
        League("4334", "French Ligue 1"),
        League("4335", "Spanish La Liga")
    )
}