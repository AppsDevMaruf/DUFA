package com.marufalam.dufa.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.marufalam.dufa.data.models.SearchBy
import com.marufalam.dufa.databinding.FragmentLogoutBinding
import com.marufalam.dufa.databinding.ItemSearchBinding
import com.marufalam.dufa.`interface`.SearchByListener

class SearchItemAdapter(var seaSearchByListener: SearchByListener) :
    ListAdapter<SearchBy, SearchItemAdapter.SearchItemViewHolder>(comparator) {


    inner class SearchItemViewHolder(var binding: ItemSearchBinding) :
        RecyclerView.ViewHolder(binding.root)

    companion object {

        var comparator = object : DiffUtil.ItemCallback<SearchBy>() {
            override fun areItemsTheSame(oldItem: SearchBy, newItem: SearchBy): Boolean {

                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(oldItem: SearchBy, newItem: SearchBy): Boolean {
                return oldItem == newItem
            }

        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchItemViewHolder {
        return SearchItemViewHolder(
            ItemSearchBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: SearchItemViewHolder, position: Int) {

        getItem(position).let {
            holder.binding.searchByItemTv.text = it.name

            holder.itemView.setOnClickListener { _ ->
                seaSearchByListener.searchBYSelectedItem(it)
            }


        }

    }


}