package com.example.sportsapp.data

import com.example.sportsapp.data.models.MatchResponse
import com.example.sportsapp.data.models.PlayerDetailsResponse
import com.example.sportsapp.data.models.TeamDetailsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SportsDataApi {

    @GET("{api_key}/eventslast.php") // ?id=133602
    suspend fun getLastMatches(@Path("api_key") api_key: String, @Query("id") team_id: Int): Response<MatchResponse>

    @GET("{api_key}/lookupteam.php") // ?id=133604
    suspend fun getTeamDetails(@Path("api_key") api_key: String, @Query("id") team_id: Int): Response<TeamDetailsResponse>

    @GET("{api_key}/searchteams.php") // ?t=paris%20sg
    suspend fun getTeamsFromTeamName(@Path("api_key") api_key: String, @Query("t") team_name: String): Response<TeamDetailsResponse>

    @GET("{api_key}/searchplayers.php") // ?p=lionel%20messi
    suspend fun getTeamsFromPlayerName(@Path("api_key") api_key: String, @Query("p") player_name: String): Response<PlayerDetailsResponse>
}