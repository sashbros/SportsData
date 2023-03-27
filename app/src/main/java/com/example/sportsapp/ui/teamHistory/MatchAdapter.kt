package com.example.sportsapp.ui.teamHistory

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.sportsapp.R
import com.example.sportsapp.data.models.Match
import com.example.sportsapp.databinding.MatchLayoutBinding

class MatchAdapter (var matches: List<Match>) : RecyclerView.Adapter<MatchAdapter.MatchViewHolder>() {

    inner class MatchViewHolder(private val binding: MatchLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind (match: Match) {
            binding.nameHome.text = match.homeTeamName
            binding.nameAway.text = match.awayTeamName
            binding.scoreHome.text = match.homeTeamScore.toString()
            binding.scoreAway.text = match.awayTeamScore.toString()
            binding.venueCountry.text = buildString {
                append(match.venue ?: "")
                match.country?.let {
                    append(", $it")
                }
            }
            binding.dateTime.text = buildString {
                append(match.dateOfMatch)
                append(" @ ")
                append(match.timeOfMatch)
            }
            binding.leagueName.text = match.leagueName

            setImageFromImageURL(binding.root, match.homeTeamBadgeURL, binding.thumbHome)
            setImageFromImageURL(binding.root, match.awayTeamBadgeURL, binding.thumbAway)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchViewHolder {
        val binding = MatchLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MatchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MatchViewHolder, position: Int) {
        holder.bind(matches[position])
    }

    override fun getItemCount(): Int {
        return matches.size
    }

    fun updateData(newMatches: List<Match>) {
        matches = newMatches
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