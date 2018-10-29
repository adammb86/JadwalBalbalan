package com.example.adammb.jadwalbalbalan.model.player

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Player(
        @SerializedName("idPlayer")
        var playerId: String? = null,

        @SerializedName("strPlayer")
        var playerName: String? = null,

        @SerializedName("strCutout")
        var playerAvatar: String? = null,

        @SerializedName("strPosition")
        var playerPosition: String? = null,

        @SerializedName("strHeight")
        var playerHeight: String? = null,

        @SerializedName("strWeight")
        var playerWeight: String? = null,

        @SerializedName("strDescriptionEN")
        var playerDescription: String? = null
) : Parcelable