package com.example.sportsapp.data.models

import android.os.Parcel
import android.os.Parcelable

data class Match(
    val matchId: Int,
    val leagueId: Int,
    val leagueName: String,
    val leagueBadgeURL: String?,
    val homeTeamId: Int,
    val awayTeamId: Int,
    val homeTeamName: String,
    val awayTeamName: String,
    val homeTeamScore: Int,
    val awayTeamScore: Int,
    val homeTeamBadgeURL: String?,
    val awayTeamBadgeURL: String?,
    val dateOfMatch: String,
    val timeOfMatch: String,
    val venue: String?,
    val country: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    )

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(matchId)
        parcel.writeInt(leagueId)
        parcel.writeString(leagueName)
        parcel.writeString(leagueBadgeURL)
        parcel.writeInt(homeTeamId)
        parcel.writeInt(awayTeamId)
        parcel.writeString(homeTeamName)
        parcel.writeString(awayTeamName)
        parcel.writeInt(homeTeamScore)
        parcel.writeInt(awayTeamScore)
        parcel.writeString(homeTeamBadgeURL)
        parcel.writeString(awayTeamBadgeURL)
        parcel.writeString(dateOfMatch)
        parcel.writeString(timeOfMatch)
        parcel.writeString(venue)
        parcel.writeString(country)
    }

    companion object CREATOR : Parcelable.Creator<Match> {
        override fun createFromParcel(parcel: Parcel): Match {
            return Match(parcel)
        }

        override fun newArray(size: Int): Array<Match?> {
            return arrayOfNulls(size)
        }
    }
}