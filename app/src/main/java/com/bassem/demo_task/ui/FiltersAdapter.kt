package com.bassem.demo_task.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bassem.demo_task.utils.Logger
import com.bassem.demo_task.utils.Logger.i
import com.bassem.demo_task.databinding.ItemFilterBinding

class FiltersAdapter(private val filters: List<String>, private val filterInterface: FilterInterface) :
    RecyclerView.Adapter<FiltersAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ItemFilterBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemFilterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = filters.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val filter = filters[position]
        Logger.i("filter is $filter")
        holder.binding.text.text = filter
        holder.binding.text.setOnClickListener {
            filterInterface.onClick(filter)
        }
    }

    interface FilterInterface {
        fun onClick(filter: String?)
    }


}
