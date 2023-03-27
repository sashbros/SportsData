package com.example.sportsapp.ui.teamHistory

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sportsapp.R
import com.example.sportsapp.data.models.Match
import com.example.sportsapp.data.models.Team
import com.example.sportsapp.databinding.ActivityMainBinding
import com.example.sportsapp.viewModel.MatchViewModel
import io.github.cdimascio.dotenv.dotenv
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val env = dotenv {
        directory = "/assets"
        filename = "env"
    }
    private val api_key = env["API_KEY"] ?: throw Exception("API_KEY not found in env file")
    // private var team_id: Int = 135800// 133739 //133612// 133613// 133604// 133602 133714
    // private var team_name: String = "Arsenal"//"Paris SG"

    private val teamNames = arrayOf("Paris SG", "Real Madrid", "Barcelona", "Manchester United", "Al Nassr", "Atletico Madrid")
    private val playerNames = arrayOf("Lionel Messi", "Cristiano Ronaldo", "Luka Modrić", "Neymar", "Kylian Mbappe", "Robert Lewandowski")

    private lateinit var adapter: MatchAdapter
    private lateinit var teamsAdapter: TeamsAdapter

    private var spinnerItemSelected: String = "Team"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUI()
    }

    private fun initUI() {
        /** initializing autoComplete View */
        binding.editTextTeamName.setAdapter(ArrayAdapter<String>(this@MainActivity, android.R.layout.simple_list_item_1, teamNames))

        /** initializing spinner adapter */
        selectSomethingFromSpinner()

        /** initializing teams-view adapter */
        teamsAdapter = TeamsAdapter(emptyList(), object: TeamsAdapter.OnItemClickListener{
            override fun onItemClick(team: Team) {
                onTeamClickGetMatches(team)
            }
        })
        binding.recyclerViewTeams.adapter = teamsAdapter
        val spanCount = getSpanCount(150f)
        binding.recyclerViewTeams.layoutManager = GridLayoutManager(this@MainActivity, spanCount)

        /** initializing matches-view adapter */
        adapter = MatchAdapter(emptyList())
        binding.recyclerViewMatches.adapter = adapter
        binding.recyclerViewMatches.layoutManager = LinearLayoutManager(this@MainActivity)

        /** setting on-click to search button */
        binding.searchButton.setOnClickListener {
            binding.editTextTeamName.clearFocus()
            binding.recyclerViewTeams.visibility = View.VISIBLE
            binding.recyclerViewMatches.visibility = View.GONE
            binding.progressBar.isVisible = false
            if (binding.editTextTeamName.text.trim().isEmpty()) {
                Toast.makeText(this@MainActivity, "Please enter something", Toast.LENGTH_LONG).show()
                binding.editTextTeamName.setText("")
            } else {
                val team_player_name = binding.editTextTeamName.text.trim().toString()
                adapter.updateData(emptyList())
                if (spinnerItemSelected == "Team")
                    getTeamsFromTeamName(team_player_name)
                else
                    getTeamsFromPlayerName(team_player_name)
            }
        }
    }

    /** when clicked on any team */
    private fun onTeamClickGetMatches(team: Team) {
        val teamId: Int = team.idTeam.toInt()
        binding.recyclerViewTeams.visibility = View.GONE
        binding.recyclerViewMatches.visibility = View.VISIBLE
//        teamsAdapter.updateData(emptyList())
        adapter.updateData(emptyList())
        binding.noResultsText.visibility = View.GONE
        binding.progressBar.isVisible = true
        getTeamMatches(teamId)
    }

    /** fetches matches/events for a given team_id */
    private fun getTeamMatches(team_id: Int) {
        val viewModel = ViewModelProvider(this).get(MatchViewModel::class.java)
        CoroutineScope(Dispatchers.Main).launch {
            viewModel.getMatches(api_key, team_id).observe(this@MainActivity, Observer { matches ->
                if (matches.isNullOrEmpty()) {
                    adapter.updateData(emptyList())
                } else {
                    adapter.updateData(matches)
                    binding.progressBar.isVisible = adapter.itemCount <= 0
                }
            })
        }
    }

    /** fetches teams for a given team_name */
    private fun getTeamsFromTeamName(team_name: String) {
        val viewModel = ViewModelProvider(this).get(MatchViewModel::class.java)
        CoroutineScope(Dispatchers.Main).launch {
            viewModel.getTeams(api_key, team_name).observe(this@MainActivity, Observer {teams ->
                if (teams == null) {
                    teamsAdapter.updateData(emptyList())
                    binding.noResultsText.visibility = View.VISIBLE
                } else {
                    teamsAdapter.updateData(teams)
                    binding.noResultsText.visibility = View.GONE
                }
            })
        }
    }

    /** fetches teams for a given player_name */
    private fun getTeamsFromPlayerName(player_name: String) {
        val viewModel = ViewModelProvider(this).get(MatchViewModel::class.java)
        CoroutineScope(Dispatchers.Main).launch {
            viewModel.getTeamsFromPlayerName(api_key, player_name).observe(this@MainActivity, Observer {teams ->
                if (teams == null) {
                    teamsAdapter.updateData(emptyList())
                    binding.noResultsText.visibility = View.VISIBLE
                } else {
                    teamsAdapter.updateData(teams)
                    binding.noResultsText.visibility = View.GONE
                }
            })
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putString("searchField", binding.editTextTeamName.text.toString())

        outState.putInt("teamsVisibility", binding.recyclerViewTeams.visibility)
        outState.putInt("matchesVisibility", binding.recyclerViewMatches.visibility)

        val matches = adapter.matches
        outState.putParcelableArrayList("matches", ArrayList(matches))

        val teams = teamsAdapter.teams
        outState.putParcelableArrayList("teams", ArrayList(teams))

    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        binding.editTextTeamName.setText(savedInstanceState.getString("searchField"), false)

        binding.recyclerViewTeams.visibility = savedInstanceState.getInt("teamsVisibility")
        binding.recyclerViewMatches.visibility = savedInstanceState.getInt("matchesVisibility")

        val matches = savedInstanceState.getParcelableArrayList<Match>("matches")?.toList()
        if (matches != null) {
            adapter.updateData(matches)
        }

        val teams = savedInstanceState.getParcelableArrayList<Team>("teams")?.toList()
        if (teams != null) {
            teamsAdapter.updateData(teams)
        }
    }

    /** event when something is selected in spinner */
    private fun selectSomethingFromSpinner() {
        binding.spinnerPlayerTeam.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(adapterView: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long) {
                spinnerItemSelected = adapterView?.getItemAtPosition(position).toString()
                binding.editTextTeamName.setText("")
//                binding.editTextTeamName.hint = "Enter $spinnerItemSelected Name"
                binding.editTextTeamNameParent.hint = "Search by $spinnerItemSelected Name"
                if (spinnerItemSelected == "Team")
                    binding.editTextTeamName.setAdapter(ArrayAdapter<String>(this@MainActivity, android.R.layout.simple_list_item_1, teamNames))
                else
                    binding.editTextTeamName.setAdapter(ArrayAdapter<String>(this@MainActivity, android.R.layout.simple_list_item_1, playerNames))
            }
        }
    }

    /** gets span count for GridLayoutManager */
    private fun getSpanCount(dp: Float): Int {
        val itemWidth = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp,
            resources.displayMetrics
        ).toInt()
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val screenWidth = displayMetrics.widthPixels
        return (screenWidth / itemWidth).toInt()
    }

    override fun onBackPressed() {
        if (binding.recyclerViewMatches.visibility == View.VISIBLE) {
            binding.recyclerViewTeams.visibility = View.VISIBLE
            binding.recyclerViewMatches.visibility = View.GONE
            binding.progressBar.isVisible = false
        } else {
            super.onBackPressed()
        }
    }
}