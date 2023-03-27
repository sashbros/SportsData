package com.example.sportsapp.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.sportsapp.data.models.Match
import com.example.sportsapp.data.models.Result
import com.example.sportsapp.data.models.Team
import com.example.sportsapp.utilities.RetrofitInstance
import kotlinx.coroutines.*
import retrofit2.HttpException
import java.io.IOException

class MatchDAO {
    private val matchList = mutableListOf<Match>()
    private val matches = MutableLiveData<List<Match>>()

    private val teamList = mutableListOf<Team>()
    private val teams = MutableLiveData<List<Team>>()

    init {
        matches.value = matchList
        teams.value = teamList
    }

    fun getMatches(api_key: String, team_id: Int): LiveData<List<Match>> {
        matches.value = emptyList()
        CoroutineScope(Dispatchers.IO).launch {
            val results: List<Result> = fetchMatches(api_key, team_id)
            extractMatchesFromResults(api_key, results)
            withContext(Dispatchers.Main) {
                matches.value = matchList
            }

        }
//        teams.value = emptyList()
        return matches
    }

    fun getTeams(api_key: String, team_name: String): LiveData<List<Team>> {
        teams.value = emptyList()
        CoroutineScope(Dispatchers.IO).launch {
            val tempTeams: List<Team> = fetchTeams(api_key, team_name)
            withContext(Dispatchers.Main) {
                teams.value = tempTeams
            }
        }
        matches.value = emptyList()
        return teams
    }

    fun getTeamsFromPlayerName(api_key: String, player_name: String): LiveData<List<Team>> {
        teams.value = emptyList()
        CoroutineScope(Dispatchers.IO).launch {
            val tempTeams: List<Team>? = fetchTeamsFromPlayerName(api_key, player_name)
            withContext(Dispatchers.Main) {
                teams.value = tempTeams
            }
        }
        matches.value = emptyList()
        return teams
    }

    private suspend fun fetchMatches(api_key: String, team_id: Int): List<Result> {
        var results: List<Result> = mutableListOf<Result>()
        try {
            val response = RetrofitInstance.api.getLastMatches(api_key, team_id)
            if (response.isSuccessful && response.body() != null) {
                val matches = response.body()!!
                results = matches.results
            } else {
                Log.e("SPDATA", "Response not successful")
            }
        } catch (e: IOException) {
            Log.e("SPDATA", "No Internet Connection")
        } catch (e: HttpException) {
            Log.e("SPDATA", "HTTP Exception. Unexpected Response.")
        } catch (e: Exception) {
            Log.e("ApiError", "API call failed with error message ${e.message}")
        }
        return results
    }

    private fun extractMatchesFromResults(api_key: String, results: List<Result>) {
        runBlocking {
            matchList.clear()
            if (!results.isNullOrEmpty()) {
                results.forEach { result ->
                    val homeTeamDetailsDeferred = async(Dispatchers.IO) {
                        getTeamDetailsURLfromId(
                            api_key,
                            result.idHomeTeam?.toInt() ?: 0
                        )
                    }
                    val awayTeamDetailsDeferred = async(Dispatchers.IO) {
                        getTeamDetailsURLfromId(
                            api_key,
                            result.idAwayTeam?.toInt() ?: 0
                        )
                    }

                    val homeTeamDetails = homeTeamDetailsDeferred.await()
                    val awayTeamDetails = awayTeamDetailsDeferred.await()

                    val match = Match(
                        result.idEvent.toInt(),
                        result.idLeague.toInt(),
                        result.strLeague,
                        "",
                        result.idHomeTeam?.toInt() ?: 0,
                        result.idAwayTeam?.toInt() ?: 0,
                        result?.strHomeTeam ?: "",
                        result?.strAwayTeam ?: "",
                        result.intHomeScore?.toInt() ?: 0,
                        result.intAwayScore?.toInt() ?: 0,
                        homeTeamDetails?.firstOrNull()?.strTeamBadge ?: "",
                        awayTeamDetails?.firstOrNull()?.strTeamBadge ?: "",
                        result.dateEvent,
                        result.strTime,
                        result.strVenue,
                        result.strCountry
                    )
                    matchList.add(match)
                }
            }
        }
    }

    private suspend fun getTeamDetailsURLfromId(api_key: String, team_id: Int): List<Team> {
        var teamDetails: List<Team> = mutableListOf<Team>()
        try {
            val response = RetrofitInstance.api.getTeamDetails(api_key, team_id)
            if (response.isSuccessful && response.body() != null) {
                val matches = response.body()!!
                teamDetails = matches.teams
            } else {
                Log.e("SPDATA", "opResponse not successful")
            }
        } catch (e: IOException) {
            Log.e("SPDATA", "No Internet Connection")
        } catch (e: HttpException) {
            Log.e("SPDATA", "HTTP Exception. Unexpected Response.")
        } catch (e: Exception) {
            Log.e("ApiError", "API call failed with error message ${e.message}")
        }
        return teamDetails
    }

    private suspend fun fetchTeams(api_key: String, team_name: String): List<Team> {
        var teamDetails: List<Team> = mutableListOf<Team>()
        try {
            val response = RetrofitInstance.api.getTeamsFromTeamName(api_key, team_name)
            if (response.isSuccessful && response.body() != null) {
                val body = response.body()!!
                teamDetails = body.teams
            } else {
                Log.e("SPDATA", "opResponse not successful")
            }
        } catch (e: IOException) {
            Log.e("SPDATA", "No Internet Connection")
        } catch (e: HttpException) {
            Log.e("SPDATA", "HTTP Exception. Unexpected Response.")
        } catch (e: Exception) {
            Log.e("ApiError", "API call failed with error message ${e.message}")
        }
        return teamDetails
    }

    private suspend fun fetchTeamsFromPlayerName(api_key: String, player_name: String): List<Team>? {
        var teamDetails: MutableList<Team> = mutableListOf<Team>()
        try {
            val response = RetrofitInstance.api.getTeamsFromPlayerName(api_key, player_name)
            if (response.isSuccessful && response.body() != null) {
                val body = response.body()!!
                if (!body.player.isNullOrEmpty()) {
                    body.player.forEach { player ->
                        val teamId1 = player.idTeam.toInt()
                        teamDetails.addAll(getTeamDetailsURLfromId(api_key, teamId1))
                        if (!player.idTeam2.isNullOrEmpty() && player.idTeam2 != "0") {
                            val teamId2 = player.idTeam2.toInt()
                            teamDetails.addAll(getTeamDetailsURLfromId(api_key, teamId2))
                        }
                    }
                } else {
                    return null
                }
            } else {
                Log.e("SPDATA", "opResponse not successful")
            }
        } catch (e: IOException) {
            Log.e("SPDATA", "No Internet Connection")
        } catch (e: HttpException) {
            Log.e("SPDATA", "HTTP Exception. Unexpected Response.")
        } catch (e: Exception) {
            Log.e("ApiError", "API call failed with error message ${e.message}")
        }
        return teamDetails
    }
}