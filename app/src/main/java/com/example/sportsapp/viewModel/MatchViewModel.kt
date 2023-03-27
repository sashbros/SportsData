package com.example.sportsapp.viewModel

import android.service.autofill.FieldClassification.Match
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sportsapp.data.MatchDAO
import com.example.sportsapp.data.MatchRepository

class MatchViewModel() : ViewModel() {

//    private val match = MutableLiveData<Match>()
    private val matchRepositoryInstance = MatchRepository.getInstance(MatchDAO())

    fun getMatches(api_key: String, team_id: Int) = matchRepositoryInstance.getMatches(api_key, team_id)

    fun getTeams(api_key: String, team_name: String) = matchRepositoryInstance.getTeams(api_key, team_name)

    fun getTeamsFromPlayerName(api_key: String, player_name: String) = matchRepositoryInstance.getTeamsFromPlayerName(api_key, player_name)
}