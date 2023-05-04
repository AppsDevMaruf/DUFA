package com.marufalam.dufa.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.marufalam.dufa.R
import com.marufalam.dufa.data.models.SearchBy
import com.marufalam.dufa.databinding.ItemSearchBinding
import com.marufalam.dufa.interfaces.SearchByListener

class SearchItemAdapter(var seaSearchByListener: SearchByListener, var type: String) :
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
            Log.i("type", "onBindViewHolder: $type ")

            when (type) {
                "Blood Group" -> {
                    holder.binding.searchByItemTv.text = it.name
                    holder.binding.itemIcon.setImageResource(R.drawable.blood)


                }
                "District" -> {
                    holder.binding.searchByItemTv.text = it.name
                    holder.binding.itemIcon.setImageResource(R.drawable.pin)


                }
                "Occupation" -> {
                    holder.binding.searchByItemTv.text = it.name
                    holder.binding.itemIcon.setImageResource(R.drawable.occupation)


                }
                "Department" -> {
                    holder.binding.searchByItemTv.text = it.name
                    holder.binding.itemIcon.setImageResource(R.drawable.department)


                }

            }


            holder.itemView.setOnClickListener { _ ->
                seaSearchByListener.searchBYSelectedItem(it)
            }


        }

    }


}