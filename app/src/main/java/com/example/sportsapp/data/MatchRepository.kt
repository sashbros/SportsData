package com.example.sportsapp.data

class MatchRepository private constructor(private val matchDAO: MatchDAO) {

    fun getMatches(api_key: String, team_id: Int) = matchDAO.getMatches(api_key, team_id)

    fun getTeams(api_key: String, team_name: String) = matchDAO.getTeams(api_key, team_name)

    fun getTeamsFromPlayerName(api_key: String, player_name: String) = matchDAO.getTeamsFromPlayerName(api_key, player_name)

    companion object {
        @Volatile private  var instance: MatchRepository? = null

        fun getInstance(matchDAO: MatchDAO) =
            instance ?: synchronized(this) {
                instance ?: MatchRepository(matchDAO).also { instance = it }
            }
    }
}