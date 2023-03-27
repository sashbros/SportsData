package com.example.sportsapp.ui.teamHistory

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.sportsapp.R
import com.example.sportsapp.data.models.Match
import com.example.sportsapp.data.models.Team
import com.example.sportsapp.databinding.TeamsLayoutBinding

class TeamsAdapter(var teams: List<Team>, private val listener: OnItemClickListener) : RecyclerView.Adapter<TeamsAdapter.TeamsViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(team: Team)
    }

    inner class TeamsViewHolder(private val binding: TeamsLayoutBinding) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        init {
            binding.root.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            listener.onItemClick(teams[adapterPosition])
        }

        fun bind (team: Team) {
            binding.teamName.text = team.strTeam
            setImageFromImageURL(binding.root, team.strTeamBadge, binding.thumbTeam)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamsViewHolder {
        val binding = TeamsLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TeamsViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return teams.size
    }

    override fun onBindViewHolder(holder: TeamsViewHolder, position: Int) {
        holder.bind(teams[position])
    }

    fun updateData(newTeams: List<Team>) {
        teams = newTeams
        notifyDataSetChanged()
    }

    private fun setImageFromImageURL(view: View, imageURL: String?, imagePosition: ImageView) {
        if (imageURL != null && imageURL.isNotEmpty()) {
            GlideApp.with(view)
                .load(imageURL)
                .placeholder(R.drawable.progress_bar)
                .into(imagePosition)
        } else {
            imagePosition.setImageResource(R.mipmap.ic_launcher)
        }

    }
}