package com.example.adammb.jadwalbalbalan.model.event

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class EventResponse(
        @SerializedName(value = "events", alternate = ["event"])
        val events: List<Event>
) : Parcelable