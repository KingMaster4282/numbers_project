package com.vkdev.numbersinfo.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.vkdev.numbersinfo.databinding.ItemHistoryBinding
import com.vkdev.numbersinfo.domain.model.NumberInfoModel

class HistoryAdapter(
    private val onItemClick: (Int) -> Unit
) : PagingDataAdapter<NumberInfoModel, HistoryAdapter.MyViewHolder>(DIFF_CALLBACK) {

    inner class MyViewHolder(val binding: ItemHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: NumberInfoModel) {
            binding.tvHeader.text = item.number.toString()
            binding.tvInfo.text = item.text
            binding.root.setOnClickListener {
                item.id?.let {
                    onItemClick(it)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemHistoryBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<NumberInfoModel>() {
            override fun areItemsTheSame(oldItem: NumberInfoModel, newItem: NumberInfoModel): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: NumberInfoModel, newItem: NumberInfoModel): Boolean =
                oldItem == newItem
        }
    }
}