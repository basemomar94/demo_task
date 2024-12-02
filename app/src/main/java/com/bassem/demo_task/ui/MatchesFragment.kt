package com.bassem.demo_task.ui

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bassem.demo_task.data.models.Match
import com.bassem.demo_task.data.models.Result
import com.bassem.demo_task.utils.AppConstants
import com.bassem.demo_task.utils.DateUtils
import com.bassem.demo_task.utils.Logger
import com.bassem.demo_task.utils.Logger.i
import com.bassem.demo_task.utils.showToast
import com.bassem.demo_task.viewmodels.MatchesViewModel
import com.bassem.demo_task.R
import com.bassem.demo_task.databinding.FragmentMatchesBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.Calendar

@AndroidEntryPoint
class MatchesFragment : BaseFragment<FragmentMatchesBinding>(), MatchesAdapter.FavoriteInterface,
    FiltersAdapter.FilterInterface, View.OnClickListener {
    private val matchesViewModel: MatchesViewModel by viewModels()
    private var matchesAdapter: MatchesAdapter? = null
    private var matchesList: List<Match> = listOf()
    private var fromDate: LocalDate? = null
    private var toDate: LocalDate? = null
    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMatchesBinding {
        return FragmentMatchesBinding.inflate(layoutInflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.statusChip.setOnClickListener(this)
        binding.fromChip.setOnClickListener(this)
        binding.toChip.setOnClickListener(this)

        binding.statusChip.setOnCloseIconClickListener {
            updateFilter(AppConstants.ALL)
        }
        binding.fromChip.setOnCloseIconClickListener {
            binding.fromChip.text = getString(R.string.from)
            matchesAdapter?.updateList(matchesList)
        }
        binding.toChip.setOnCloseIconClickListener {
            binding.toChip.text = getString(R.string.to)
            matchesAdapter?.updateList(matchesList)
        }

        lifecycleScope.launch {
            matchesViewModel.getMatches().collect { result ->
                when (result) {
                    is Result.Fail -> {
                        toggleLoading(false)
                        requireContext().showToast(result.reason)
                    }

                    is Result.Loading -> {
                        toggleLoading(true)

                    }

                    is Result.Success -> {
                        toggleLoading(false)
                        populateMatchesRv(result.result.matches)
                        Logger.i("result is ${result.result.matches}")

                    }
                }

            }
        }

    }

    private fun populateMatchesRv(matches: List<Match>) {
        matchesList = matches
        matchesAdapter = MatchesAdapter(groupMatchesByDate(matchesList), this)
        binding.matchesRv.apply {
            adapter = matchesAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)

        }
    }

    private fun toggleLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.matchesRv.gone()
            binding.progressCircular.visible()
        } else {
            binding.matchesRv.visible()
            binding.progressCircular.gone()
        }
    }

    override fun toggleFavorite(match: Match, position: Int) {
        Logger.i("adding favorite")
        matchesViewModel.makeFavorite(match)
        matchesAdapter?.notifyItemChanged(position)
    }

    override fun onClick(filter: String?) {
        if (filter != null) {
            updateFilter(filter)
        }
    }

    private fun updateFilter(filter: String) {
        binding.statusChip.text = filter
        val filteredList = matchesViewModel.filterByStatus(matchesList, filter)
        matchesAdapter?.updateList(filteredList)
    }

    private fun showDatePickerDialog(onDateSelected: (LocalDate) -> Unit) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog =
            DatePickerDialog(requireContext(), { _, selectedYear, selectedMonth, selectedDay ->
                val selectedDate = LocalDate.of(selectedYear, selectedMonth + 1, selectedDay)

                onDateSelected(selectedDate)
            }, year, month, day)

        datePickerDialog.show()
    }

    private fun filterByDate() {
        Logger.i("from $fromDate  $toDate")
        val filteredList =
            matchesViewModel.filteredByFromToDate(
                matches = matchesList,
                from = fromDate,
                to = toDate
            )
        matchesAdapter?.updateList(filteredList)
    }

    override fun onClick(view: View?) {
        when (view) {
            binding.statusChip -> {
                FiltersDialog(this).show(
                    requireActivity().supportFragmentManager,
                    FiltersDialog::class.java.simpleName
                )
            }

            binding.fromChip -> {
                showDatePickerDialog { selectedDate ->
                    fromDate = selectedDate
                    binding.fromChip.text = DateUtils.formatLocalDate(fromDate)
                    filterByDate()
                }
            }

            binding.toChip -> {
                showDatePickerDialog { selectedDate ->
                    toDate = selectedDate
                    binding.toChip.text = DateUtils.formatLocalDate(toDate)
                    filterByDate()
                }
            }
        }
    }


}