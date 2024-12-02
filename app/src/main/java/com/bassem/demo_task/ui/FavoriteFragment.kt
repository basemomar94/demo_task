package com.bassem.demo_task.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bassem.demo_task.data.models.Match
import com.bassem.demo_task.viewmodels.MatchesViewModel
import com.bassem.demo_task.databinding.FragmentMatchesBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoriteFragment : BaseFragment<FragmentMatchesBinding>() {
    private val viewModel: MatchesViewModel by viewModels()

    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMatchesBinding {
        return FragmentMatchesBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.chipGroup.gone()

        collectFavorite()
    }

    private fun collectFavorite() = lifecycleScope.launch {
        viewModel.getFavoriteMatches().collect { favorite ->
            populateMatchesRv(favorite.first())
        }
    }

    private fun populateMatchesRv(matchesList: List<Match>) {
        val matchesAdapter = MatchesAdapter(groupMatchesByDate(matchesList), null)
        binding.matchesRv.apply {
            adapter = matchesAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)

        }
    }

}
