package com.bassem.demo_task.ui

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.bassem.demo_task.utils.AppConstants
import com.bassem.demo_task.utils.Logger
import com.bassem.demo_task.utils.Logger.i
import com.bassem.demo_task.databinding.DialogFilterBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FiltersDialog(private val filterInterface: FiltersAdapter.FilterInterface) :
    BottomSheetDialogFragment(), FiltersAdapter.FilterInterface {
    private var binding: DialogFilterBinding? = null
    private var selectedFilter: String? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogFilterBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        populateRv()
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        filterInterface.onClick(selectedFilter)

    }


    private fun populateRv() {
        val filters = listOf(
            AppConstants.ALL,
            AppConstants.FINISHED,
            AppConstants.MATCH_IN_PROGRESS,
            AppConstants.SCHEDULED,
            AppConstants.HALF_TIME,
            AppConstants.FULL_TIME,
            AppConstants.EXTRA_TIME,
            AppConstants.PENALTIES
        )
        Logger.i("filters count ${filters.size}")
        val filtersAdapter = FiltersAdapter(filters, this)
        binding?.filtersRv?.apply {
            adapter = filtersAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)

        }
    }

    override fun onClick(filter: String?) {
        selectedFilter = filter
        dismiss()

    }


}