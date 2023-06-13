package org.dufa.dufa9596.paging

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import org.dufa.dufa9596.databinding.ItemLoadingProgressBinding

class LoaderAdapter : LoadStateAdapter<LoaderAdapter.LoadViewHolder>() {

    class LoadViewHolder(private val binding: ItemLoadingProgressBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindState(loadState: LoadState) {
            binding.progress.isVisible = loadState is LoadState.Loading
        }

    }

    override fun onBindViewHolder(holder: LoadViewHolder, loadState: LoadState) {
        holder.bindState(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadViewHolder {
        return LoadViewHolder(
            ItemLoadingProgressBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

}