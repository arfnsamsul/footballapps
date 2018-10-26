package com.samsularifin.footballclub.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Player(
        @SerializedName("idPlayer")
        var id: String? = null,

        @SerializedName("strPlayer")
        var name: String? = null,

        @SerializedName("strPosition")
        var position: String? = null,

        @SerializedName("strHeight")
        var height: String? = null,

        @SerializedName("strWeight")
        var weight: String? = null,

        @SerializedName("strDescriptionEN")
        var description: String? = null,

        @SerializedName("strCutout")
        var imgCutout: String? = null,

        @SerializedName("strFanart1")
        var imgFanart: String? = null
)
