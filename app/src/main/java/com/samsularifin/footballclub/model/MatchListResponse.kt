package com.samsularifin.footballclub.model

import com.google.gson.annotations.SerializedName

class MatchListResponse {
    @SerializedName(value= "events", alternate=["event","results"])
    val matches: List<Match>? = null
}
