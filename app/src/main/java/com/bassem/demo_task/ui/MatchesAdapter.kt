package com.bassem.demo_task.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bassem.demo_task.data.models.Match
import com.bassem.demo_task.data.models.MatchItem
import com.bassem.demo_task.utils.AppConstants
import com.bassem.demo_task.utils.DateUtils
import com.bassem.demo_task.databinding.ItemDateHeaderBinding
import com.bassem.demo_task.databinding.ItemMatchBinding

class MatchesAdapter(
    private var matchesList: List<MatchItem>,
    private val favoriteInterface: FavoriteInterface?
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class MatchViewHolder(val binding: ItemMatchBinding) :
        RecyclerView.ViewHolder(binding.root)

    inner class DateViewHolder(val binding: ItemDateHeaderBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_DATE_HEADER -> {
                val binding = ItemDateHeaderBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                DateViewHolder(binding)
            }

            VIEW_TYPE_MATCH_ITEM -> {
                val binding =
                    ItemMatchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                MatchViewHolder(binding)
            }

            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun getItemCount() = matchesList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = matchesList[position]) {
            is MatchItem.DateHeader -> (holder as DateViewHolder).binding.dateHeader.text =
                item.date

            is MatchItem.MatchData -> {
                val match = item.match
                (holder as MatchViewHolder).binding.apply {
                    awayClubTxt.text = match.awayTeam?.name
                    homeClubTxt.text = match.homeTeam?.name
                    matchStatus.text = match.status
                    update.text = DateUtils.formatIsoDateTime(match.lastUpdated)

                    favorite.setOnClickListener {
                        favoriteInterface?.toggleFavorite(match, position)
                    }

                    with(match.status) {
                        when (this) {
                            AppConstants.FINISHED, AppConstants.MATCH_IN_PROGRESS -> {
                                homeScoreTxt.visible()
                                awayScoreTxt.visible()
                                matchDate.gone()

                                homeScoreTxt.text = match.score?.fullTime?.homeTeam.toString()
                                awayScoreTxt.text = match.score?.fullTime?.awayTeam.toString()
                            }

                            AppConstants.SCHEDULED -> {
                                homeScoreTxt.gone()
                                awayScoreTxt.gone()

                                matchDate.text = DateUtils.formatIsoDateTime(match.utcDate)
                            }

                            else -> {}
                        }
                    }
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (matchesList[position]) {
            is MatchItem.DateHeader -> VIEW_TYPE_DATE_HEADER
            is MatchItem.MatchData -> VIEW_TYPE_MATCH_ITEM
        }
    }

    fun updateList(newList: List<Match>) {
        matchesList = groupMatchesByDate(newList)
        notifyDataSetChanged()
    }

    interface FavoriteInterface {
        fun toggleFavorite(match: Match, position: Int)
    }

    companion object {
        private const val VIEW_TYPE_DATE_HEADER = 0
        private const val VIEW_TYPE_MATCH_ITEM = 1
    }
}

