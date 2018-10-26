package com.samsularifin.footballclub.model

import com.google.gson.annotations.SerializedName

class PlayerListResponse {
    @SerializedName("player")
    val players: List<Player>? = null
}
