package com.example.sportsapp.data.models

import android.os.Parcel
import android.os.Parcelable

data class Team(
    val idAPIfootball: String,
    val idLeague: String,
    val idLeague2: String,
    val idLeague3: String,
    val idLeague4: Any,
    val idLeague5: Any,
    val idLeague6: Any,
    val idLeague7: Any,
    val idSoccerXML: String,
    val idTeam: String,
    val intFormedYear: String,
    val intLoved: String,
    val intStadiumCapacity: String,
    val strAlternate: String,
    val strCountry: String,
    val strDescriptionCN: Any,
    val strDescriptionDE: String,
    val strDescriptionEN: String,
    val strDescriptionES: String,
    val strDescriptionFR: String,
    val strDescriptionHU: Any,
    val strDescriptionIL: Any,
    val strDescriptionIT: String,
    val strDescriptionJP: String,
    val strDescriptionNL: Any,
    val strDescriptionNO: String,
    val strDescriptionPL: Any,
    val strDescriptionPT: String,
    val strDescriptionRU: String,
    val strDescriptionSE: Any,
    val strDivision: Any,
    val strFacebook: String,
    val strGender: String,
    val strInstagram: String,
    val strKeywords: String,
    val strKitColour1: String,
    val strKitColour2: String,
    val strKitColour3: String,
    val strLeague: String,
    val strLeague2: String,
    val strLeague3: String,
    val strLeague4: String,
    val strLeague5: String,
    val strLeague6: String,
    val strLeague7: String,
    val strLocked: String,
    val strManager: String,
    val strRSS: String,
    val strSport: String,
    val strStadium: String,
    val strStadiumDescription: String,
    val strStadiumLocation: String,
    val strStadiumThumb: String,
    val strTeam: String,
    val strTeamBadge: String,
    val strTeamBanner: String,
    val strTeamFanart1: String,
    val strTeamFanart2: String,
    val strTeamFanart3: String,
    val strTeamFanart4: String,
    val strTeamJersey: String,
    val strTeamLogo: String,
    val strTeamShort: String,
    val strTwitter: String,
    val strWebsite: String,
    val strYoutube: String
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readValue(Int::class.java.classLoader) ?: 0,
        parcel.readValue(Int::class.java.classLoader) ?: 0,
        parcel.readValue(Int::class.java.classLoader) ?: 0,
        parcel.readValue(Int::class.java.classLoader) ?: 0,
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readValue(Int::class.java.classLoader) ?: 0,
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readValue(Int::class.java.classLoader) ?: 0,
        parcel.readValue(Int::class.java.classLoader) ?: 0,
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readValue(Int::class.java.classLoader) ?: 0,
        parcel.readString() ?: "",
        parcel.readValue(Int::class.java.classLoader) ?: 0,
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readValue(Int::class.java.classLoader) ?: 0,
        parcel.readValue(Int::class.java.classLoader) ?: 0,
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    )

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(idAPIfootball)
        parcel.writeString(idLeague)
        parcel.writeString(idLeague2)
        parcel.writeString(idLeague3)
        parcel.writeParcelable(idLeague4 as Parcelable?, flags)
        parcel.writeParcelable(idLeague5 as Parcelable?, flags)
        parcel.writeParcelable(idLeague6 as Parcelable?, flags)
        parcel.writeParcelable(idLeague7 as Parcelable?, flags)
        parcel.writeString(idSoccerXML)
        parcel.writeString(idTeam)
        parcel.writeString(intFormedYear)
        parcel.writeString(intLoved)
        parcel.writeString(intStadiumCapacity)
        parcel.writeString(strAlternate)
        parcel.writeString(strCountry)
        parcel.writeParcelable(strDescriptionCN as Parcelable?, flags)
        parcel.writeString(strDescriptionDE)
        parcel.writeString(strDescriptionEN)
        parcel.writeString(strDescriptionES)
        parcel.writeString(strDescriptionFR)
        parcel.writeParcelable(strDescriptionHU as Parcelable?, flags)
        parcel.writeParcelable(strDescriptionIL as Parcelable?, flags)
        parcel.writeString(strDescriptionIT)
        parcel.writeString(strDescriptionJP)
        parcel.writeParcelable(strDescriptionNL as Parcelable?, flags)
        parcel.writeString(strDescriptionNO)
        parcel.writeParcelable(strDescriptionPL as Parcelable?, flags)
        parcel.writeString(strDescriptionPT)
        parcel.writeString(strDescriptionRU)
        parcel.writeParcelable(strDescriptionSE as Parcelable?, flags)
        parcel.writeParcelable(strDivision as Parcelable?, flags)
        parcel.writeString(strFacebook)
        parcel.writeString(strGender)
        parcel.writeString(strInstagram)
        parcel.writeString(strKeywords)
        parcel.writeString(strKitColour1)
        parcel.writeString(strKitColour2)
        parcel.writeString(strKitColour3)
        parcel.writeString(strLeague)
        parcel.writeString(strLeague2)
        parcel.writeString(strLeague3)
        parcel.writeString(strLeague4)
        parcel.writeString(strLeague5)
        parcel.writeString(strLeague6)
        parcel.writeString(strLeague7)
        parcel.writeString(strLocked)
        parcel.writeString(strManager)
        parcel.writeString(strRSS)
        parcel.writeString(strSport)
        parcel.writeString(strStadium)
        parcel.writeString(strStadiumDescription)
        parcel.writeString(strStadiumLocation)
        parcel.writeString(strStadiumThumb)
        parcel.writeString(strTeam)
        parcel.writeString(strTeamBadge)
        parcel.writeString(strTeamBanner)
        parcel.writeString(strTeamFanart1)
        parcel.writeString(strTeamFanart2)
        parcel.writeString(strTeamFanart3)
        parcel.writeString(strTeamFanart4)
        parcel.writeString(strTeamJersey)
        parcel.writeString(strTeamLogo)
        parcel.writeString(strTeamShort)
        parcel.writeString(strTwitter)
        parcel.writeString(strWebsite)
        parcel.writeString(strYoutube)
    }

    companion object CREATOR : Parcelable.Creator<Team> {
        override fun createFromParcel(parcel: Parcel): Team {
            return Team(parcel)
        }

        override fun newArray(size: Int): Array<Team?> {
            return arrayOfNulls(size)
        }
    }

}